package com.example.Attendance.controller;
 
import com.example.Attendance.entity.Employee;
import com.example.Attendance.entity.Attendance;
import com.example.Attendance.service.EmployeeService;
import com.example.Attendance.service.AttendanceService;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
import java.util.stream.Collectors;
 
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/employees")
public class EmployeeController {
 
    private final EmployeeService employeeService;
    private final AttendanceService attendanceService;
 
    public EmployeeController(EmployeeService employeeService, AttendanceService attendanceService) {
        this.employeeService = employeeService;
        this.attendanceService = attendanceService;
    }
 
    // Add a new employee
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        // Save the employee (Database will generate the ID automatically)
        Employee savedEmployee = employeeService.addEmployee(employee);
 
        // Return the saved employee with a 201 status (Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }
 
    // Get all employees (Ensure no duplicates)
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
 
        // Remove duplicates if any - Ensure uniqueness based on employee ID
        List<Employee> uniqueEmployees = employees.stream()
                .distinct()
                .collect(Collectors.toList());
 
        // Return list of all unique employees
        return ResponseEntity.ok(uniqueEmployees);
    }
 
    // Get employee and their attendance details
    @GetMapping("/details/{id}")
    public ResponseEntity<Employee> getEmployeeDetailsWithAttendance(@PathVariable Long id) {
        // Get the employee by ID
        Employee employee = employeeService.getEmployeeById(id);
 
        // Check if employee is null (employee not found)
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
 
        // Get the attendance history for the employee
        List<Attendance> attendanceList = attendanceService.getAttendanceHistory(id);
 
        // If there are any duplicate attendances (for example, if the same attendance entry is being saved multiple times),
        // remove duplicates before returning.
        List<Attendance> uniqueAttendanceList = attendanceList.stream()
                .distinct()  // Remove any duplicate attendance entries
                .collect(Collectors.toList());
 
        // Set the unique attendance list to the employee
        employee.setAttendances(uniqueAttendanceList);
 
        // Return the employee with unique attendance data
        return ResponseEntity.ok(employee);
    }
}
 
 