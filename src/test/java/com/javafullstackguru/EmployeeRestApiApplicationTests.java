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
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeRestControllerTest {

    @InjectMocks
    private EmployeeRestController employeeRestController;

    @Mock
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee();
        employee.setId(1);
        employee.setName("John Doe");
        employee.setEmail("johndoe@example.com");
        // Set other fields as necessary
    }

    @Test
    void addEmployee_ShouldReturnEmployee() {
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee);

        ResponseEntity<Employee> response = employeeRestController.addEmployee(employee);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(employee);
    }

    @Test
    void getAllEmployees_ShouldReturnListOfEmployees() {
        var employees = Arrays.asList(employee);
        when(employeeService.getAllEmployees()).thenReturn(employees);

        var response = employeeRestController.getAllEmployees();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(employees);
    }

    @Test
    void getEmployeeById_ShouldReturnEmployee() {
        when(employeeService.getEmployeeById(1)).thenReturn(employee);

        var response = employeeRestController.getEmployeeById(1);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(employee);
    }

    @Test
    void updateEmployee_ShouldReturnUpdatedEmployee() {
        when(employeeService.updateEmployee(anyInt(), any(Employee.class))).thenReturn(employee);

        var response = employeeRestController.updateEmployee(1, employee);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(employee);
    }

    @Test
    void deleteEmployee_ShouldReturnNoContent() {
        doNothing().when(employeeService).deleteEmployee(1);

        var response = employeeRestController.deleteEmployee(1);

        assertThat(response.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    void deleteEmployee_ShouldReturnNotFound() {
        doThrow(new UserNotFoundException("User not found")).when(employeeService).deleteEmployee(1);

        var response = employeeRestController.deleteEmployee(1);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }
}
