package com.example.employeemanagement.repository;

import com.example.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Main Database Layer (DAO - Data Access Object) ka component hoon
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Duplicate email check when creating an employee
    // Jab hume findById ke alawa kuch alag search karna ho (jaise Email se search karna), tab hum Derived Methods likhte hain.
    // Naya employee create karte waqt check karne ke liye ki yeh email pehle se kisi aur ka toh nahi hai. Agar hai toh true dega, hum turant error phenk denge.
    boolean existsByEmail(String email); // SELECT COUNT(*) > 0 FROM employees WHERE email = ?

    // Duplicate email check when updating an employee (ignores own ID)
    // Khud se khud ka update karte time uss ID ko chhod ke kisi aur ID mein bas na ho, that's it!
    boolean existsByEmailAndIdNot(String email, Long id);

    // Find employee by email
    Optional<Employee> findByEmail(String email);
}