package com.msbeigi.sprintboot.service;

import com.msbeigi.sprintboot.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();
}
