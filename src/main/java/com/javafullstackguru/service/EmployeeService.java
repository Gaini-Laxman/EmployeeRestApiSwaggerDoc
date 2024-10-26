package com.javafullstackguru.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javafullstackguru.entity.Employee;

@Service
public interface EmployeeService {
	
	Employee creatEmployee(Employee employee);
	Employee getEmployeeById(Integer id);
	List<Employee> getAllEmployees();
	Employee updateEmployee(Integer id, Employee employee);
	void deleteEmployee(Integer id);

}
