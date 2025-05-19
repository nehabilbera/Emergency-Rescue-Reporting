package com.example.Attendance.repository;

import com.example.Attendance.entity.Attendance;
import com.example.Attendance.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployee(Employee employee);
    List<Attendance> findByEmployeeAndDate(Employee employee, LocalDate date);
    List<Attendance> findByDate(LocalDate date);
}
