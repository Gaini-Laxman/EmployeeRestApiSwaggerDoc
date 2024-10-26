package com.javafullstackguru.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javafullstackguru.entity.Employee;
import com.javafullstackguru.exception.UserNotFoundException;
import com.javafullstackguru.service.EmployeeServiceImpl;

@RestController
public class EmployeeRestController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @PostMapping("/employee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.creatEmployee(employee));
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getAllEmployeeById(Integer id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        employee.setId(id); // Set the ID for the update
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build(); // Return 204 No Content
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found
        }
    }
}
