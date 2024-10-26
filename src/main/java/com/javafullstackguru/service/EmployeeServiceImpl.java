package com.javafullstackguru.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javafullstackguru.entity.Employee;
import com.javafullstackguru.exception.UserAlreadyExistByException;
import com.javafullstackguru.exception.UserNotFoundException;
import com.javafullstackguru.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee creatEmployee(Employee employee) {
        if (employeeRepository.existsById(employee.getId())) {
            throw new UserAlreadyExistByException("Employee already exists with ID: " + employee.getId());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Employee not found with ID: " + id));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Integer id, Employee employee) {
        if (!employeeRepository.existsById(id)) {
            throw new UserNotFoundException("Employee not found with ID: " + id);
        }
        employee.setId(id); // Ensure the ID is set for the update
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Integer id) {
        Employee employeeToDelete = employeeRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Employee not found with ID: " + id));
        employeeRepository.delete(employeeToDelete);
    }
}
