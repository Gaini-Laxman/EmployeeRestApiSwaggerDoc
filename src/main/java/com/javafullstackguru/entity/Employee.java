package com.javafullstackguru.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@NoArgsConstructor
@Entity
public class Employee {

    @Id
    @GeneratedValue(generator = "custom-id-generator")
    @GenericGenerator(name = "custom-id-generator", strategy = "com.javafullstackguru.generator.CustomIdGenerator")
    private String id;

    private String name;
    private String email;
    private String dept;
    private Double salary;

    public Employee(String id, String name, String email, String dept, Double salary) {
        if (name == null || email == null || dept == null || salary == null) {
            throw new IllegalArgumentException("Fields cannot be null");
        }
        this.id = id;
        this.name = name;
        this.email = email;
        this.dept = dept;
        this.salary = salary;
    }
}
