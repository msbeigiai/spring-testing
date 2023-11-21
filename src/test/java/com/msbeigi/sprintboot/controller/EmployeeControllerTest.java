package com.msbeigi.sprintboot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msbeigi.sprintboot.entity.Employee;
import com.msbeigi.sprintboot.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URI = "/api/employees";

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee()
            throws Exception {
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();

        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when - action and the behaviour that we are going to test
        ResultActions response = mockMvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then - verify the output
        response
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }
}


















