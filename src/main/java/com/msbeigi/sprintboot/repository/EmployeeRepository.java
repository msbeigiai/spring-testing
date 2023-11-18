package com.msbeigi.sprintboot.repository;

import com.msbeigi.sprintboot.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmailIgnoreCase(String email);

    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Optional<Employee> findFirstNameAndLastName(String firstName, String lastName);
}
