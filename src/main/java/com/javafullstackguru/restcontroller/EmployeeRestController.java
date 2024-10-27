package com.javafullstackguru.restcontroller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.javafullstackguru.entity.Employee;
import com.javafullstackguru.exception.UserNotFoundException;
import com.javafullstackguru.service.EmployeeServiceImpl;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @PostMapping("/employee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        var createdEmployee = employeeService.createEmployee(employee); // Fixed typo
        return ResponseEntity.status(201).body(createdEmployee); // 201 Created
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/employee/{id}") // Changed the path to be consistent
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
        var employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        employee.setId(id); // Set the ID for the update
        var updatedEmployee = employeeService.updateEmployee(id, employee);
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
