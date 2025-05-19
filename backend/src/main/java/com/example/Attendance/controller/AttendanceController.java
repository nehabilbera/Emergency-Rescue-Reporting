package com.example.Attendance.controller;

import com.example.Attendance.entity.Attendance;
import com.example.Attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import com.example.Attendance.dto.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/")
public class AttendanceController {
	
    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("records/{employeeId}")
    public String toggleCheckInOut(@PathVariable Long employeeId) {
        return attendanceService.toggleCheckInOut(employeeId);
    }

    @GetMapping("/history/{employeeId}")
    public List<Attendance> getAttendanceHistory(@PathVariable Long employeeId) {
        return attendanceService.getAttendanceHistory(employeeId);
    }

    @GetMapping("/present")
    public List<AttendanceDTO> getPresentEmployees(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return attendanceService.getEmployeesPresentByDate(date);
    }
}
