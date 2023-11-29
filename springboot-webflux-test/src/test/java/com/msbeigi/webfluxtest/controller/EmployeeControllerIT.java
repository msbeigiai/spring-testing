package com.msbeigi.webfluxtest.controller;

import com.msbeigi.webfluxtest.dto.EmployeeDto;
import com.msbeigi.webfluxtest.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // will load application context
public class EmployeeControllerIT {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void givenEmployee_whenSaveEmployee_thenShouldReturnNewEmployeeSaved() {
        // given - precondition or setup
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("Sadegh")
                .lastName("Mohammadi")
                .email("sadegh.mohammadi@gmail.com")
                .build();


        // then - verify the output
        webTestClient
                .post()
                .uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnAnEmployeeObject() {
        // given - precondition or setup
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("Sadegh")
                .lastName("Mohammadi")
                .email("sadegh.mohammadi@gmail.com")
                .build();

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

        // when - action and the behaviour that we are going to test

        Assertions.assertThat(savedEmployee).isNotNull();

        webTestClient
                .get()
                .uri("/api/employees/{id}", Collections.singletonMap("id", savedEmployee.getId()))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(savedEmployee.getId())
                .jsonPath("$.firstName").isEqualTo(savedEmployee.getFirstName())
                .jsonPath("$.lastName").isEqualTo(savedEmployee.getLastName())
                .jsonPath("$.email").isEqualTo(savedEmployee.getEmail());

        // then - verify the output
    }

}
