package com.msbeigi.sprintboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msbeigi.sprintboot.entity.Employee;
import com.msbeigi.sprintboot.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerIT {

    private static final String BASE_URI = "/api/employees";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
    }

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee()
            throws Exception {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();

        // when - action and the behaviour that we are going to test
        ResultActions response = mockMvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then - verify the output
        response
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnListOfEmployees() throws Exception {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();
        Employee employee2 = Employee.builder()
                .firstName("Ali")
                .lastName("Sadeghi")
                .email("ali@gmail.com")
                .build();
        List<Employee> employees = List.of(employee, employee2);

        employeeRepository.saveAll(employees);

        // when - action and the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get(BASE_URI));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(employees.size())));
    }

    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenShouldReturnEmployeeObject() throws Exception {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();

        employeeRepository.save(employee);

        // when - action and the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get(BASE_URI + "/{id}", employee.getId()));

        // then - verify the output
        response
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenShouldReturnEmpty() throws Exception {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();
        Long employeeId = 0L;
        employeeRepository.save(employee);

        // when - action and the behaviour that we are going to test
        ResultActions response = mockMvc.perform(get(BASE_URI + "/{id}", employeeId));

        // then - verify the output
        response
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void givenUpdatedEmployeeObject_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception {
        // given - precondition or setup
        Employee savedEmployee = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                .firstName("Ali")
                .lastName("Sadeghi")
                .email("ali@gmail.com")
                .build();


        // when - action and the behaviour that we are going to test
        ResultActions response = mockMvc
                .perform(put(BASE_URI + "/{id}", savedEmployee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmployee))
                );

        // then - verify the output
        response
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
    }

    @Test
    public void givenInvalidateUpdatedEmployeeObject_whenUpdateEmployee_thenNotFound() throws Exception {
        // given - precondition or setup
        Employee savedEmployee = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);
        Long employeeId = 0L;

        Employee updatedEmployee = Employee.builder()
                .firstName("Ali")
                .lastName("Sadeghi")
                .email("ali@gmail.com")
                .build();

        // when - action and the behaviour that we are going to test
        ResultActions response = mockMvc
                .perform(put(BASE_URI + "/{id}", employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmployee))
                );

        // then - verify the output
        response
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenShouldDeleteAnEmployee() throws Exception {
        // given - precondition or setup
        Employee savedEmployee = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        // when - action and the behaviour that we are going to test
        ResultActions response = mockMvc
                .perform(delete(BASE_URI + "/{id}", savedEmployee.getId()));

        // then - verify the output
        response
                .andExpect(status().isOk())
                .andDo(print());
    }
}
