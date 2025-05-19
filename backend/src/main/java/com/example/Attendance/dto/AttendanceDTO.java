package com.example.Attendance.dto;
 
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
 
public class AttendanceDTO {
    private Long employeeId;
    private String name;
    private LocalDate date;
    private String checkIn;
    private String checkOut;
 
    // Constructor to initialize the DTO with Employee's ID, Name, Date, and Check-in/Check-out times
    public AttendanceDTO(Long employeeId, String name, LocalDate date, LocalTime checkIn, LocalTime checkOut) {
        this.employeeId = employeeId;
        this.name = name;
        this.date = date;
        this.checkIn = formatTime(checkIn);
        this.checkOut = formatTime(checkOut);
    }
 
    // Method to format time to a string without decimals in seconds
    private String formatTime(LocalTime time) {
        if (time != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            return time.format(formatter);
        }
        return null;
    }
 
    // Getters and Setters
    public Long getEmployeeId() {
        return employeeId;
    }
 
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public LocalDate getDate() {
        return date;
    }
 
    public void setDate(LocalDate date) {
        this.date = date;
    }
 
    public String getCheckIn() {
        return checkIn;
    }
 
    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }
 
    public String getCheckOut() {
        return checkOut;
    }
 
    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }
}