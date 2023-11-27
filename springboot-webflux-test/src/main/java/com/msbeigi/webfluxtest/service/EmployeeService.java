package com.msbeigi.webfluxtest.service;

import com.msbeigi.webfluxtest.dto.EmployeeDto;
import reactor.core.publisher.Mono;

public interface EmployeeService {
    Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto);
}
