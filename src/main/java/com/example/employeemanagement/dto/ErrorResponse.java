package com.example.employeemanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 📌 ERROR RESPONSE DTO (Standard Error Format)
 * ============================================================================
 * Iska Kaam Kya Hai?
 * Jab bhi application mein koi gadbadi ya error hoti hai:
 *  - Employee nahi mila (404 Not Found)
 *  - Duplicate email daal diya (409 Conflict)
 *  - Form galat bhara (400 Bad Request / Validation Error)
 *
 * Spring Boot ka default ganda error dikhane ke bajaye, hum client (Postman/Frontend)
 * ko ek saaf aur structured JSON format bhejte hain.
 * ============================================================================
 */
// 💡 @JsonInclude(NON_NULL): Agar validationErrors ya koi field null ho,
// toh Jackson use JSON mein dikhayega hi nahi (chupa dega). JSON clean rahega!
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    // 1. Error kis exact date aur time par aayi?
    private LocalDateTime timestamp;

    // 2. HTTP Status Code kya hai? (jaise 400, 404, 409, 500)
    private int status;

    // 3. HTTP Status ka official naam kya hai? (jaise "Not Found", "Bad Request")
    private String error;

    // 4. Insaan ke samajhne layak detailed message (jaise "Employee not found with id: 5")
    private String message;

    // 5. Agar form validation fail hui, toh kaun-kaun se dabbe galat bhare gaye?
    // Key = field name ("email"), Value = error rule ("Email must be valid")
    private Map<String, String> validationErrors;

    // ========================================================================
    // CONSTRUCTOR OVERLOADING (3 Alag-Alag Zarooraton Ke Liye)
    // ========================================================================

    /**
     * 1. Default (No-Args) Constructor:
     * Spring Boot ke Jackson JSON library ko background mein kaam karne ke liye
     * ek khali constructor compulsory chahiye hota hai.
     */
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * 2. Constructor for Simple Errors (3 Parameters):
     * Kahan use hota hai: Jab koi seedha error aaye (jaise 404 Not Found ya 409 Duplicate Email).
     * Yahan form validation ki list nahi hoti, isliye validationErrors pass nahi kiya.
     *
     * Example Output JSON:
     * {
     *   "timestamp": "2026-09-30T01:40:00",
     *   "status": 404,
     *   "error": "Not Found",
     *   "message": "Employee not found with id: 5"
     * }
     */
    public ErrorResponse(int status, String error, String message) {
        this.timestamp = LocalDateTime.now(); // System clock se current time automatically set
        this.status = status;
        this.error = error;
        this.message = message;
    }

    /**
     * 3. Constructor for Form Validation Errors (4 Parameters):
     * Kahan use hota hai: Jab @Valid fail ho (400 Bad Request).
     * Yahan message ke sath-sath kaunse fields galat bhare hain (Map) bhi bhejte hain.
     *
     * Example Output JSON:
     * {
     *   "timestamp": "2026-09-30T01:40:00",
     *   "status": 400,
     *   "error": "Bad Request",
     *   "message": "Validation failed for one or more fields",
     *   "validationErrors": {
     *     "email": "Email must be valid",
     *     "salary": "Salary must be greater than 0"
     *   }
     * }
     */
    public ErrorResponse(int status, String error, String message, Map<String, String> validationErrors) {
        this.timestamp = LocalDateTime.now(); // System clock se current time automatically set
        this.status = status;
        this.error = error;
        this.message = message;
        this.validationErrors = validationErrors;
    }

    // ========================================================================
    // GETTERS & SETTERS (Jackson ko JSON banane ke liye chahiye hote hain)
    // ========================================================================

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
