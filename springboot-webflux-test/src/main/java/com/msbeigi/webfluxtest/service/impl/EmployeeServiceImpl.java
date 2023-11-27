package com.msbeigi.webfluxtest.service.impl;

import com.msbeigi.webfluxtest.dto.EmployeeDto;
import com.msbeigi.webfluxtest.entity.Employee;
import com.msbeigi.webfluxtest.mapper.EmployeeDtoMapper;
import com.msbeigi.webfluxtest.mapper.EmployeeMapper;
import com.msbeigi.webfluxtest.repository.EmployeeRepository;
import com.msbeigi.webfluxtest.service.EmployeeService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeDtoMapper employeeDtoMapper;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               EmployeeDtoMapper employeeDtoMapper,
                               EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeDtoMapper = employeeDtoMapper;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeDtoMapper.apply(employeeDto);
        Mono<Employee> savedEmployee = employeeRepository.save(employee);
        return savedEmployee.map(employeeMapper);
    }
}
