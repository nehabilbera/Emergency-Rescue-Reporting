package com.example.Attendance.dto;

import java.util.List;
import com.example.Attendance.entity.Employee;

public class EmployeeAttendanceDTO {
    private Employee employee;
    private List<AttendanceDTO> attendanceList;  // Use a custom DTO for Attendance

    public EmployeeAttendanceDTO(Employee employee, List<AttendanceDTO> attendanceList) {
        this.employee = employee;
        this.attendanceList = attendanceList;
    }

    // Getters and setters
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<AttendanceDTO> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<AttendanceDTO> attendanceList) {
        this.attendanceList = attendanceList;
    }
}
