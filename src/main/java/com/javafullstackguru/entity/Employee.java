package com.javafullstackguru.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String name;
    private String email;
    private String dept;
    private Double salary;

    // New constructor
    public Employee(Integer id, String name, String email, String dept, Double salary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dept = dept;
        this.salary = salary;
    }

    // Default constructor (optional if you use Lombok @Data)
    public Employee() {}
}
