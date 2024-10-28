package com.javafullstackguru;

import com.javafullstackguru.entity.Employee;
import com.javafullstackguru.exception.UserNotFoundException;
import com.javafullstackguru.restcontroller.EmployeeRestController;
import com.javafullstackguru.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class EmployeeRestControllerTest {

    @InjectMocks
    private EmployeeRestController employeeRestController;

    @Mock
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private static final String EMPLOYEE_ID = "klit10001";
    private static final String EMPLOYEE_NOT_FOUND_MESSAGE = "Employee Not Available With ID: " + EMPLOYEE_ID;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee(EMPLOYEE_ID, "John Doe", "johndoe@example.com", "IT", 50000.0);
    }

    @Test
    void createEmployee_WhenValid_ShouldReturnCreatedEmployee() {
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee);

        ResponseEntity<Employee> response = employeeRestController.createEmployee(employee);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(employee);
    }

    @Test
    void getAllEmployees_ShouldReturnListOfEmployees() {
        List<Employee> employees = Arrays.asList(employee);
        when(employeeService.getAllEmployees(anyInt(), anyInt())).thenReturn(employees);

        ResponseEntity<List<Employee>> response = employeeRestController.getAllEmployees(0, 10);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(employees);
    }

    @Test
    void getEmployeeById_WhenExists_ShouldReturnEmployee() {
        when(employeeService.getEmployeeById(anyString())).thenReturn(employee);

        ResponseEntity<Employee> response = employeeRestController.getEmployeeById(EMPLOYEE_ID);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(employee);
    }

    @Test
    void getEmployeeById_WhenNotFound_ShouldReturnNotFound() {
        when(employeeService.getEmployeeById(anyString())).thenThrow(new UserNotFoundException(EMPLOYEE_NOT_FOUND_MESSAGE));

        ResponseEntity<Employee> response = employeeRestController.getEmployeeById(EMPLOYEE_ID);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    void updateEmployee_WhenValid_ShouldReturnUpdatedEmployee() {
        when(employeeService.updateEmployee(anyString(), any(Employee.class))).thenReturn(employee);

        ResponseEntity<Employee> response = employeeRestController.updateEmployee(EMPLOYEE_ID, employee);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(employee);
    }

    @Test
    void deleteEmployee_WhenExists_ShouldReturnNoContent() {
        doNothing().when(employeeService).deleteEmployee(EMPLOYEE_ID);

        ResponseEntity<Void> response = employeeRestController.deleteEmployee(EMPLOYEE_ID);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    void deleteEmployee_WhenNotFound_ShouldReturnNotFound() {
        doThrow(new UserNotFoundException(EMPLOYEE_NOT_FOUND_MESSAGE)).when(employeeService).deleteEmployee(EMPLOYEE_ID);

        ResponseEntity<Void> response = employeeRestController.deleteEmployee(EMPLOYEE_ID);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }
}
