package com.example.Attendance.service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Attendance.entity.Employee;
import com.example.Attendance.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
 
    @Autowired
    private EmployeeRepository employeeRepository;
    
    // Add a new employee
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
 
    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    //get employee by id
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }
    public Long generateUniqueEmployeeId() {
        Long newId;
        do {
            // Generate a random ID within the range [2111111L, 2999999L]
            newId = ThreadLocalRandom.current().nextLong(2111111L, 3000000L);
        } while (employeeRepository.existsById(newId)); // Check if the ID already exists in the database
        
        return newId; // Return the unique ID
    }
    
}