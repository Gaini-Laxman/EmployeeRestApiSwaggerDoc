package com.javafullstackguru;

import com.javafullstackguru.entity.Employee;
import com.javafullstackguru.exception.UserAlreadyExistByException;
import com.javafullstackguru.exception.UserNotFoundException;
import com.javafullstackguru.repository.EmployeeRepository;
import com.javafullstackguru.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    private Employee employee;
    private static final String EMPLOYEE_ID = "klit10001";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee(EMPLOYEE_ID, "John Doe", "johndoe@example.com", "IT", 50000.0);
    }

    @Test
    void createEmployee_WhenNotExists_ShouldReturnEmployee() {
        when(employeeRepository.existsById(employee.getId())).thenReturn(false);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee createdEmployee = employeeService.createEmployee(employee);

        assertThat(createdEmployee).isEqualTo(employee);
        verify(employeeRepository).save(employee);
    }

    @Test
    void createEmployee_WhenExists_ShouldThrowUserAlreadyExistByException() {
        when(employeeRepository.existsById(employee.getId())).thenReturn(true);

        assertThatThrownBy(() -> employeeService.createEmployee(employee))
            .isInstanceOf(UserAlreadyExistByException.class)
            .hasMessage("Employee already exists with ID: " + employee.getId());
    }

    @Test
    void getEmployeeById_WhenExists_ShouldReturnEmployee() {
        when(employeeRepository.findById(anyString())).thenReturn(Optional.of(employee));

        Employee fetchedEmployee = employeeService.getEmployeeById(EMPLOYEE_ID);

        assertThat(fetchedEmployee).isEqualTo(employee);
    }

    @Test
    void getEmployeeById_WhenNotExists_ShouldThrowUserNotFoundException() {
        when(employeeRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.getEmployeeById(EMPLOYEE_ID))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("Employee Not Available With ID: " + EMPLOYEE_ID);
    }

    @Test
    void getAllEmployees_ShouldReturnEmployeeList() {
        when(employeeRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(employee)));

        List<Employee> employees = employeeService.getAllEmployees(0, 10);

        assertThat(employees).isNotEmpty();
        assertThat(employees.size()).isEqualTo(1);
        assertThat(employees.get(0)).isEqualTo(employee);
    }

    @Test
    void updateEmployee_WhenExists_ShouldReturnUpdatedEmployee() {
        when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee updatedEmployee = employeeService.updateEmployee(EMPLOYEE_ID, employee);

        assertThat(updatedEmployee).isEqualTo(employee);
        verify(employeeRepository).save(employee);
    }

    @Test
    void updateEmployee_WhenNotExists_ShouldThrowUserNotFoundException() {
        when(employeeRepository.existsById(anyString())).thenReturn(false);

        assertThatThrownBy(() -> employeeService.updateEmployee(EMPLOYEE_ID, employee))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("Cannot update, employee not found with ID: " + EMPLOYEE_ID);
    }

    @Test
    void deleteEmployee_WhenExists_ShouldRemoveEmployee() {
        when(employeeRepository.findById(anyString())).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(any(Employee.class));

        employeeService.deleteEmployee(EMPLOYEE_ID);

        verify(employeeRepository).delete(employee);
    }

    @Test
    void deleteEmployee_WhenNotExists_ShouldThrowUserNotFoundException() {
        when(employeeRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.deleteEmployee(EMPLOYEE_ID))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("Cannot delete, employee not found with ID: " + EMPLOYEE_ID);
    }
}
