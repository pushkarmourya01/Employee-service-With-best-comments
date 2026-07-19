package com.example.employeemanagement.mapper;

import com.example.employeemanagement.dto.CreateEmployeeRequest;
import com.example.employeemanagement.dto.EmployeeResponse;
import com.example.employeemanagement.dto.UpdateEmployeeRequest;
import com.example.employeemanagement.entity.Employee;
import org.springframework.stereotype.Component;

//Ek dedicated class jo sirf object-to-object conversion karti hai. Na logic, na DB, na validation. Uski file mein sirf copy-paste + field mapping hota hai
/**
 * 📌 STEP 4: MAPPER (The Bridge / Translator)
 * ============================================================================
 * Design Pattern: Data Mapper Pattern
 *
 * ❓ DTO ke baad Mapper par aane ka convention kyun hai?
 * Service ka asli kaam hai "Business Logic" (duplicate check, rules).
 * Data ko ek object se doosre object mein copy karna (20 lines of
 * getters/setters)
 * Service mein daalne se code cluttered ho jata hai.
 * Isliye Mapper class akele yeh sara translation handle karti hai.
 *
 * 💡 @Component: Taaki Spring iska Bean banaye aur hum ise Service mein inject
 * kar sakein.
 * ============================================================================
 */
@Component
public class EmployeeMapper {

    // 1. DTO -> Entity (Naya employee DB mein save karne ke liye)
    /**
     * Client ka DTO (CreateEmployeeRequest) lo aur naya Employee entity bana ke do.
     * ⚠️ Yahan 'id' aur 'createdAt' set NAHI karte:
     * - id -> MySQL khud generate karega (@GeneratedValue IDENTITY)
     * - createdAt -> @PrePersist callback khud set karega
     * Isse client server-side fields (id/createdAt) ko cheat karke bhej nahi sakta.
     */
    public Employee toEntity(CreateEmployeeRequest request) {
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setPosition(request.getPosition());
        employee.setSalary(request.getSalary());
        return employee;
    }

    // 2. DTO -> Existing Entity (Purane employee ka data update karne ke liye)
    /**
     * Naya object banane ke bajaye DB se already loaded entity ke fields overwrite
     * karte hain.
     * ✅ Isse 'id' aur 'createdAt' bach jaate hain (wahan @PrePersist chalta hi
     * nahi,
     * aur 'createdAt' par updatable = false hai).
     * ❌ 'id' yahan set nahi karte, warna dusre employee ka record overwrite ho
     * jayega.
     * Service ne duplicate-email check pehle kar diya hota hai, mapper yeh kaam
     * nahi karta.
     */
    public void updateEntity(Employee existing, UpdateEmployeeRequest request) {
        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setEmail(request.getEmail());
        existing.setPhone(request.getPhone());
        existing.setPosition(request.getPosition());
        existing.setSalary(request.getSalary());
    }

    // 3. Entity -> Response DTO (DB se nikla employee client ko dikhane ke liye)
    /**
     * DB se nikla Employee entity ko client-facing EmployeeResponse mein badalta
     * hai.
     * Yehi method repository ke baad har jagah use hota hai (findById, findAll,
     * save).
     * ✅ Naya DTO banta hai isliye Entity ka reference bahar leak nahi hota —
     * client ke paas sirf yehi fields (id + data + createdAt) jaate hain.
     */
    public EmployeeResponse toResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setEmail(employee.getEmail());
        response.setPhone(employee.getPhone());
        response.setPosition(employee.getPosition());
        response.setSalary(employee.getSalary());
        response.setCreatedAt(employee.getCreatedAt());
        return response;
    }
}
