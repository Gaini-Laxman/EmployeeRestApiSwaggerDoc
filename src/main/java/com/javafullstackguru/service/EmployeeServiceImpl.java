package com.javafullstackguru.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javafullstackguru.entity.Employee;
import com.javafullstackguru.exception.UserAlreadyExistByException;
import com.javafullstackguru.exception.UserNotFoundException;
import com.javafullstackguru.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final String CREATE_EMPLOYEE = "creatEmployee";  
    private static final String GET_EMPLOYEE_BY_ID = "getEmployeeById";  
    private static final String UPDATE_EMPLOYEE = "updateEmployee";  
    private static final String DELETE_EMPLOYEE = "deleteEmployee";  

    @Override
    public Employee createEmployee(Employee employee) { // Fixed typo
        if (employeeRepository.existsById(employee.getId())) {
            logger.warn("Attempt to create an existing employee with ID: {}", employee.getId());
            throw new UserAlreadyExistByException(CREATE_EMPLOYEE + " " + employee.getId() + " already exists.");
        }
        logger.info("Creating employee: {}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        logger.info("Fetching employee by ID: {}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(GET_EMPLOYEE_BY_ID + " " + id));
    }

    @Override
    public List<Employee> getAllEmployees() {
        logger.info("Fetching all employees");
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Integer id, Employee employee) {
        if (!employeeRepository.existsById(id)) {
            logger.warn("Attempt to update non-existing employee with ID: {}", id);
            throw new UserNotFoundException(UPDATE_EMPLOYEE + " " + id);
        }
        employee.setId(id); // Ensure the ID is set for the update
        logger.info("Updating employee: {}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Integer id) {
        var employeeToDelete = employeeRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(DELETE_EMPLOYEE + " " + id));
        logger.info("Deleting employee: {}", employeeToDelete);
        employeeRepository.delete(employeeToDelete);
    }
}
