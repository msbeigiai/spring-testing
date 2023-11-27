package com.msbeigi.webfluxtest.service;

import com.msbeigi.webfluxtest.dto.EmployeeDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface EmployeeService {
    Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto);

    Mono<EmployeeDto> getEmployee(String employeeId);
}
