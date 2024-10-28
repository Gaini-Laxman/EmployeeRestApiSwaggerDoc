package com.javafullstackguru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.javafullstackguru.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

}
