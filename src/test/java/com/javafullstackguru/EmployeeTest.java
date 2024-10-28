package com.javafullstackguru;

import com.javafullstackguru.entity.Employee;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmployeeTest {

    private static final String ID = "KLIT1000";
    private static final String NAME = "John Doe";
    private static final String EMAIL = "johndoe@example.com";
    private static final String DEPT = "IT";
    private static final Double SALARY = 50000.0;

    @Test
    void employeeConstructor_ShouldCreateEmployee_WhenAllFieldsAreValid() {
        Employee employee = new Employee(ID, NAME, EMAIL, DEPT, SALARY);
        
        assertThat(employee.getId()).isEqualTo(ID);
        assertThat(employee.getName()).isEqualTo(NAME);
        assertThat(employee.getEmail()).isEqualTo(EMAIL);
        assertThat(employee.getDept()).isEqualTo(DEPT);
        assertThat(employee.getSalary()).isEqualTo(SALARY);
    }

    @Test
    void employeeConstructor_ShouldThrowException_WhenSalaryIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee(ID, NAME, EMAIL, DEPT, null);
        });
    }

    @Test
    void employeeConstructor_ShouldThrowException_WhenNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee(ID, null, EMAIL, DEPT, SALARY);
        });
    }

    @Test
    void employeeConstructor_ShouldThrowException_WhenEmailIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee(ID, NAME, null, DEPT, SALARY);
        });
    }

    @Test
    void employeeConstructor_ShouldThrowException_WhenDeptIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee(ID, NAME, EMAIL, null, SALARY);
        });
    }

    @Test
    void employeeConstructor_ShouldCreateEmployee_WhenAllFieldsAreProvided() {
        Employee employee = new Employee(ID, NAME, EMAIL, DEPT, SALARY);
        
        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(ID);
        assertThat(employee.getName()).isEqualTo(NAME);
        assertThat(employee.getEmail()).isEqualTo(EMAIL);
        assertThat(employee.getDept()).isEqualTo(DEPT);
        assertThat(employee.getSalary()).isEqualTo(SALARY);
    }

    // Additional tests can be added here as needed
}
