package com.msbeigi.webfluxtest.repository;

import com.msbeigi.webfluxtest.entity.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, String> {

}
