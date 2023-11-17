package com.msbeigi.sprintboot.repository;

import com.msbeigi.sprintboot.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    // JUnit test for save employee op
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();

        // when - action and the behaviour that we are gonna test
        Employee savedEmployee = employeeRepository.save(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    public void givenEmployeeList_whenFindAll_thenReturnEmployeeList() {
        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();

        Employee employee2 = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@gmail.com")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // when - action and the behaviour that we are going to test
        List<Employee> employeeList = employeeRepository.findAll();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);

    }

}