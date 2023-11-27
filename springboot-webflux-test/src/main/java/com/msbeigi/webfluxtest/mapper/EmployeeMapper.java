package com.msbeigi.webfluxtest.mapper;

import com.msbeigi.webfluxtest.dto.EmployeeDto;
import com.msbeigi.webfluxtest.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EmployeeMapper implements Function<Employee, EmployeeDto> {
    /*public static EmployeeDto mapToEmployeeDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail()
        );
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto) {
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail());
    }*/

    @Override
    public EmployeeDto apply(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail());
    }

}
