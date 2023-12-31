package com.msbeigi.sprintboot.repository;

import com.msbeigi.sprintboot.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmailIgnoreCase(String email);

    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Optional<Employee> findFirstNameAndLastName(String firstName, String lastName);

    @Query("select e from Employee e where e.firstName =:firstName and e.lastName =:lastName")
    Optional<Employee> findFirstNameAndLastNameNamedParams(@Param("firstName") String firstName,
                                                           @Param("lastName") String lastName);
    @Query(value = "select * from employees e where e.first_name=?1 and e.last_name=?2", nativeQuery = true)
    Optional<Employee> findByFirstNameAndLastNameWithNativeQuery(String firstName, String lastName);

    @Query(value = "select * from employees e where e.first_name=:firstName and e.last_name=:lastName", nativeQuery = true)
    Optional<Employee> findByFirstNameAndLastNameWithNativeQueryAndNameParams(String firstName, String lastName);
}
