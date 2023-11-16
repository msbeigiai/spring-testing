package com.msbeigi.sprintboot.repository;

import com.msbeigi.sprintboot.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
