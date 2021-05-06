package com.academy.it.boot.demo.controllers;

import com.academy.it.boot.demo.model.Employee;
import com.academy.it.boot.demo.services.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/employees", produces = "application/json;charset=UTF-8")
@ResponseBody
public class EmployeeJsonController {
    private static final String ID = "id";

    private final EmployeeService service;

    // GET /zzz/employees?salary>=100
    @GetMapping
    public List<Employee> getAll(
            @RequestParam(name = "salary>", required = false, defaultValue = "0") int salaryGreatOrEquals) {
        return service.getAllEmployees().stream()
                .filter(employee -> employee.getSalary() >= salaryGreatOrEquals)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getByPath(@PathVariable int id) {
        return ResponseEntity.of(service.getEmployee(id));
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return service.saveEmployee(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @RequestBody Employee employee,
            @PathVariable int id) {
        return employee != null && id != employee.getId()
                ? createExceptionMessage("Id in path and in entity must be the same")
                : ResponseEntity.ok(service.saveEmployee(employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable int id) {
        return ResponseEntity.of(service.deleteEmployee(id));
    }

    private ResponseEntity<ExceptionMessage> createExceptionMessage(String msg) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMessage.builder()
                        .message(msg)
                        .build());
    }

    @Builder
    @Getter
    public static class ExceptionMessage {
        private final String message;
    }
}