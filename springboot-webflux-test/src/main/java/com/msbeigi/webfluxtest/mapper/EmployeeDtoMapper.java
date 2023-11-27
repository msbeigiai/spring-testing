package com.msbeigi.webfluxtest.mapper;

import com.msbeigi.webfluxtest.dto.EmployeeDto;
import com.msbeigi.webfluxtest.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EmployeeDtoMapper implements Function<EmployeeDto, Employee> {
    @Override
    public Employee apply(EmployeeDto employeeDto) {
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail()
        );
    }
}
