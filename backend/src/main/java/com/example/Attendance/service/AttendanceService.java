package com.example.Attendance.service;
 
import com.example.Attendance.dto.AttendanceDTO;

import com.example.Attendance.entity.Attendance;

import com.example.Attendance.entity.Employee;

import com.example.Attendance.repository.AttendanceRepository;

import com.example.Attendance.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
 
import java.time.*;

import java.util.*;

import java.util.stream.Collectors;
 
@Service

public class AttendanceService {
 
    @Autowired

    private AttendanceRepository attendanceRepository;
 
    @Autowired

    private EmployeeRepository employeeRepository;
 
    public String toggleCheckInOut(Long employeeId) {

        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);

        if (employeeOpt.isEmpty()) return "Employee not found.";
 
        Employee employee = employeeOpt.get();

        LocalDate today = LocalDate.now();
 
        List<Attendance> todayRecords = attendanceRepository.findByEmployeeAndDate(employee, today);

        Attendance lastRecord = todayRecords.isEmpty() ? null : todayRecords.get(todayRecords.size() - 1);
 
        if (lastRecord == null || (lastRecord.getCheckIn() != null && lastRecord.getCheckOut() != null)) {

            Attendance newAttendance = new Attendance();

            newAttendance.setEmployee(employee);

            newAttendance.setDate(today);

            newAttendance.setCheckIn(LocalTime.now());

            attendanceRepository.save(newAttendance);

            return "Checked in successfully.";

        } else if (lastRecord.getCheckIn() != null && lastRecord.getCheckOut() == null) {

            LocalTime checkOutTime = LocalTime.now();

            lastRecord.setCheckOut(checkOutTime);

            attendanceRepository.save(lastRecord);
 
            Duration duration = Duration.between(lastRecord.getCheckIn(), checkOutTime);

            long hours = duration.toHours();
 
            return hours < 4

                    ? "Checked out successfully. Note: Early checkout (less than 4 hours)."

                    : "Checked out successfully.";

        }
 
        return "Unexpected error occurred.";

    }
 
    public List<Attendance> getAttendanceHistory(Long employeeId) {

        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);

        return employeeOpt.map(attendanceRepository::findByEmployee).orElse(new ArrayList<>());

    }
 
    public List<AttendanceDTO> getEmployeesPresentByDate(LocalDate date) {

        return attendanceRepository.findByDate(date).stream()

                .filter(att -> att.getCheckIn() != null)

                .map(att -> new AttendanceDTO(

                        att.getEmployee().getEmployeeId(),  // Use getEmployeeId() here

                        att.getEmployee().getName(),

                        att.getDate(),

                        att.getCheckIn(),

                        att.getCheckOut()

                ))

                .collect(Collectors.toList());

    }
 
}

 