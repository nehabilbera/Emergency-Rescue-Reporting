package com.example.Attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Attendance.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	boolean existsById(Long id);
    
}
