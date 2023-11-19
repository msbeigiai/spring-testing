package com.msbeigi.sprintboot.service;

import com.msbeigi.sprintboot.entity.Employee;
import com.msbeigi.sprintboot.exception.ResourceNotFoundException;
import com.msbeigi.sprintboot.repository.EmployeeRepository;
import com.msbeigi.sprintboot.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee = new Employee();

    @BeforeEach
    void setUp() {
        // employeeRepository = Mockito.mock(EmployeeRepository.class);
        // employeeService = new EmployeeServiceImpl(employeeRepository);
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();
    }

    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        // given - precondition or setup
        given(employeeRepository.findByEmailIgnoreCase(employee.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        // when - action and the behaviour that we are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isEqualTo(employee.getId());
    }

    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenShouldReturnErrorSinceEmployeeAlreadyInDb() {
        // given - precondition or setup
        given(employeeRepository.findByEmailIgnoreCase(employee.getEmail()))
                .willReturn(Optional.of(employee));

        // when - action and the behaviour that we are going to test
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        // then
        verify(employeeRepository, never()).save(employee);

    }
}