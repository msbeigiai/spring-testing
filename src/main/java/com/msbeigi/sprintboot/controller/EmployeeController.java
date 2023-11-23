package com.msbeigi.sprintboot.controller;

import com.msbeigi.sprintboot.entity.Employee;
import com.msbeigi.sprintboot.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long employeeId) {
        return employeeService.getEmployeeById(employeeId)
                .map(maybeEmployee -> ResponseEntity.ok().body(maybeEmployee))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long employeeId,
                                                   @RequestBody Employee employee) {
        return employeeService.getEmployeeById(employeeId)
                .map(maybeEmployee -> {
                    maybeEmployee.setFirstName(employee.getFirstName());
                    maybeEmployee.setLastName(employee.getLastName());
                    maybeEmployee.setEmail(employee.getEmail());

                    return ResponseEntity.ok().body(employeeService.updateEmployee(maybeEmployee));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}


