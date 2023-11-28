package com.msbeigi.webfluxtest.controller;

import com.msbeigi.webfluxtest.dto.EmployeeDto;
import com.msbeigi.webfluxtest.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee() {
        // given - precondition or setup
        EmployeeDto employeeDto = EmployeeDto
                .builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen.sadegh62@gmail.com")
                .build();

        given(employeeService.saveEmployee(any(EmployeeDto.class)))
                .willReturn(Mono.just(employeeDto));

        // when - action and the behaviour that we are going to test
        WebTestClient.ResponseSpec responseSpec = webTestClient
                .post()
                .uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange();

        // then - verify the output
        responseSpec
                .expectStatus()
                .isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        // given - precondition or setup
        EmployeeDto employeeDto = EmployeeDto
                .builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen.sadegh62@gmail.com")
                .build();

        String employeeId = "123";

        given(employeeService.getEmployee(employeeId)).willReturn(Mono.just(employeeDto));

        // when - action and the behaviour that we are going to test
        WebTestClient.ResponseSpec responseSpec = webTestClient
                .get()
                .uri("/api/employees/{id}", Collections.singletonMap("id", employeeId))
                .exchange();

        // then - verify the output
        responseSpec
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnListOfEmployees() {
        // given - precondition or setup
        EmployeeDto employeeDtoFirst = EmployeeDto
                .builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen.sadegh62@gmail.com")
                .build();

        EmployeeDto employeeDtoSecond = EmployeeDto
                .builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen.sadegh62@gmail.com")
                .build();

        List<EmployeeDto> employeeDtos = List.of(employeeDtoFirst, employeeDtoSecond);

        given(employeeService.getAllEmployees()).willReturn(Flux.fromIterable(employeeDtos));


        // when - action and the behaviour that we are going to test
        WebTestClient.ResponseSpec responseSpec = webTestClient
                .get()
                .uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        // then - verify the output
        responseSpec
                .expectStatus()
                .isOk()
                .expectBodyList(EmployeeDto.class)
                .consumeWith(System.out::println);
    }

    @Test
    public void givenEmployeeDto_whenUpdateEmployeeById_thenReturnUpdatedEmployeeObject() {
        // given - precondition or setup
        String employeeId = "123";
        EmployeeDto employeeDto = EmployeeDto
                .builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen.sadegh62@gmail.com")
                .build();

        given(employeeService.updateEmployee(any(EmployeeDto.class),
                any(String.class)))
                .willReturn(Mono.just(employeeDto));

        // when - action and the behaviour that we are going to test
        WebTestClient.ResponseSpec responseSpec = webTestClient
                .put()
                .uri("/api/employees/{id}", Collections.singletonMap("id", employeeId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange();

        // then - verify the output
        responseSpec
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    @Test
    public void givenEmployeeId_whenDeleteEmployeeById_thenReturnVoidAndDeletedEmployee() {
        // given - precondition or setup
        String employeeId = "123";

        given(employeeService.deleteEmployee(employeeId)).willReturn(Mono.empty());

        // when - action and the behaviour that we are going to test
        WebTestClient.ResponseSpec responseSpec = webTestClient
                .delete()
                .uri("/api/employees/{id}", Collections.singletonMap("id", employeeId))
                .exchange();

        // then - verify the output
        responseSpec
                .expectStatus()
                .isNoContent()
                .expectBody()
                .consumeWith(System.out::println);
    }
}









