package com.javafullstackguru.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.javafullstackguru.entity.Employee;

@Service
public interface EmployeeService {
	
    Employee createEmployee(Employee employee);
    Employee getEmployeeById(String id); // Change Integer to String
    List<Employee> getAllEmployees(int page, int size);
    Employee updateEmployee(String id, Employee employee); // Change Integer to String
    void deleteEmployee(String id); // Change Integer to String
	
}
