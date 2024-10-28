package com.javafullstackguru.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.javafullstackguru.entity.Employee;
import com.javafullstackguru.exception.UserAlreadyExistByException;
import com.javafullstackguru.exception.UserNotFoundException;
import com.javafullstackguru.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private static final String EMPLOYEE_NOT_FOUND_MSG = "Employee Not Available With ID: ";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsById(employee.getId())) {
            logger.warn("Attempt to create an existing employee with ID: {}", employee.getId());
            throw new UserAlreadyExistByException("Employee already exists with ID: " + employee.getId());
        }
        logger.info("Creating employee: {}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(String id) {
        logger.info("Fetching employee by ID: {}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(EMPLOYEE_NOT_FOUND_MSG + id));
    }

    @Override
    public List<Employee> getAllEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        logger.info("Fetching all employees - Page: {}, Size: {}", page, size);
        return employeeRepository.findAll(pageable).getContent();
    }

    @Override
    public Employee updateEmployee(String id, Employee employee) {
        if (!employeeRepository.existsById(id)) {
            logger.warn("Attempt to update non-existing employee with ID: {}", id);
            throw new UserNotFoundException("Cannot update, employee not found with ID: " + id);
        }
        employee.setId(id);
        logger.info("Updating employee: {}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(String id) {
        var employeeToDelete = employeeRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Cannot delete, employee not found with ID: " + id));
        logger.info("Deleting employee: {}", employeeToDelete);
        employeeRepository.delete(employeeToDelete);
    }
}
