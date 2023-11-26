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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

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

    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnListOfEmployees() {
        // given - precondition or setup
        Employee employee2 = Employee.builder()
                .id(2L)
                .firstName("Ali")
                .lastName("Sadeghi")
                .email("ali@gmail.com")
                .build();
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee2));

        // when - action and the behaviour that we are going to test
        List<Employee> employees = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(2);
    }

    @Test
    public void givenEmptyEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
        // given - precondition or setup
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when - action and the behaviour that we are going to test
        List<Employee> employees = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employees).isEmpty();
        assertThat(employees.size()).isEqualTo(0);
    }

    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        // given - precondition or setup
        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));

        // when - action and the behaviour that we are going to test
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isEqualTo(employee.getId());
    }

    @Test
    public void givenEmployeeObject_whenUpdateEmployeeWithNewEmployee_thenReturnUpdatedEmployee() {
        // given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);

        employee.setEmail("moh@gmailcom");
        employee.setFirstName("Moh");

        // when - action and the behaviour that we are going to test
        employeeService.updateEmployee(employee);

        // then - verify the output
        assertThat(employee).isNotNull();
        assertThat(employee.getEmail()).isEqualTo("moh@gmailcom");
        assertThat(employee.getFirstName()).isEqualTo("Moh");
    }

    @Test
    public void givenEmployeeObjectId_whenDeleteEmployeeById_thenReturnsNothing() {
        // given - precondition or setup
        willDoNothing().given(employeeRepository).deleteById(employee.getId());

        // when - action and the behaviour that we are going to test
        employeeService.deleteEmployeeById(employee.getId());

        // then - verify the output
        verify(employeeRepository, times(1)).deleteById(employee.getId());
    }
}