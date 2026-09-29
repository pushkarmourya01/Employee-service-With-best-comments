package com.example.employeemanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
/**
 * 📌 STEP 1: ENTITY (Database Blueprint)
 * ============================================================================
 * 1. Kaam: Yeh class MySQL ke 'employees' table ka direct mirror/structure hai.
 * 2. Sabse pehle yehi banti hai taaki table ke columns, datatypes aur primary key decide ho sake.
 *
 * 🧭 APPLICATION CODING FLOW (Next Steps):
 * ----------------------------------------------------------------------------
 * 1. Entity (Employee.java)                   <-- [Aap abhi yahan hain]
 *      ⬇
 * 2. Repository (EmployeeRepository.java)     <-- Database se SQL/CRUD operations ke liye
 *      ⬇
 * 3. DTOs (CreateEmployeeRequest, Response)   <-- Client se kya aayega aur kya dikhana hai (@Valid ke sath)
 *      ⬇
 * 4. Mapper & Service (EmployeeService.java)  <-- Business Logic + Duplicate check + Exception handling
 *      ⬇
 * 5. Controller (EmployeeController.java)     <-- REST API Endpoints (@PostMapping, @GetMapping)
 *
 * ⚠️ GOLDEN RULE: Entity ko kabhi direct Controller/API mein accept ya return nahi karte!
 * Hamesha DTO use karte hain taaki id/salary cheat na ho aur sensitive data leak na ho.
 * ============================================================================
 */
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "position", length = 50)
    private String position;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "created_at", updatable = false) // can't update it's value even after update the value
    private LocalDateTime createdAt;

    @PrePersist // Before inserting the value into the database, this method will be called
    // @PreUpdate will be called before updating the value into the database
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}