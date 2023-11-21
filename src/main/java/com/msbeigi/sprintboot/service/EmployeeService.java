package com.msbeigi.sprintboot.service;

import com.msbeigi.sprintboot.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    Employee updateEmployee(Employee employee);

    void deleteEmployeeById(Long id);

}
