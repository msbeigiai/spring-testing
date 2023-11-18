package com.msbeigi.sprintboot.repository;

import com.msbeigi.sprintboot.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();
    }

    // JUnit test for save employee op
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        // given - precondition or setup
        /*Employee employee = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();*/

        // when - action and the behaviour that we are gonna test
        Employee savedEmployee = employeeRepository.save(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    public void givenEmployeeList_whenFindAll_thenReturnEmployeeList() {
        // given - precondition or setup
        /*Employee employee1 = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();*/

        Employee employee2 = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@gmail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        // when - action and the behaviour that we are going to test
        List<Employee> employeeList = employeeRepository.findAll();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
        // given - precondition or setup
        /*Employee employee1 = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();*/

        Employee savedEmployee = employeeRepository.save(employee);

        // when - action and the behaviour that we are going to test
        Optional<Employee> employeeDb = employeeRepository.findById(savedEmployee.getId());

        // then - verify the output
        assertThat(employeeDb).isNotEmpty();
        assertThat(employeeDb.get().getId()).isEqualTo(savedEmployee.getId());
    }

    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
        // given - precondition or setup
        /*Employee employee = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();*/
        employeeRepository.save(employee);

        // when - action and the behaviour that we are going to test
        Employee foundEmployee = employeeRepository.findByEmailIgnoreCase(employee.getEmail()).orElseThrow();

        // then - verify the output
        assertThat(foundEmployee).isNotNull();
        assertThat(foundEmployee.getEmail()).isEqualTo(employee.getEmail());
    }

    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given - precondition or setup
        /*Employee employee1 = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();*/
        employeeRepository.save(employee);

        // when - action and the behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).orElseThrow();

        savedEmployee.setEmail("mohsen2@gmail.com");
        savedEmployee.setFirstName("MohsenJan");
        savedEmployee.setLastName("Sadegh");

        Employee updatedEmployee = employeeRepository.save(savedEmployee);
        // then - verify the output
        assertThat(savedEmployee.getEmail()).isEqualTo(updatedEmployee.getEmail());
        assertThat(savedEmployee.getFirstName()).isEqualTo(updatedEmployee.getFirstName());
        assertThat(savedEmployee.getLastName()).isEqualTo(updatedEmployee.getLastName());
    }

    @Test
    public void givenEmployeeObject_whenDeleteById_thenDeleteEmployee() {
        // given - precondition or setup
        /*Employee employee1 = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();*/
        employeeRepository.save(employee);

        // when - action and the behaviour that we are going to test
        employeeRepository.deleteById(employee.getId());

        Optional<Employee> mayBeEmployee = employeeRepository.findById(employee.getId());

        // then - verify the output
        assertThat(mayBeEmployee).isEmpty();
    }

    @Test
    public void givenEmployeeObject_whenSaveWithCustomQuery_thenReturnEmployee() {
        // given - precondition or setup
        /*Employee employee1 = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();*/
        employeeRepository.save(employee);

        // when - action and the behaviour that we are going to test
        Optional<Employee> savedEmployee =
                employeeRepository.findFirstNameAndLastName(employee.getFirstName(), employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotEmpty();
        assertThat(savedEmployee.get().getEmail()).isEqualTo("mohsen@gmail.com");
        assertThat(savedEmployee.get().getFirstName()).isEqualTo("Mohsen");
        assertThat(savedEmployee.get().getLastName()).isEqualTo("Sadeghbeigi");
    }

    @Test
    public void givenEmployeeObject_whenSaveWithCustomQueryNameParams_thenReturnEmployee() {
        // given - precondition or setup
        /*Employee employee = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();*/
        employeeRepository.save(employee);

        // when - action and the behaviour that we are going to test
        Optional<Employee> savedEmployee =
                employeeRepository.findFirstNameAndLastNameNamedParams(employee.getFirstName(),
                        employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotEmpty();
        assertThat(savedEmployee.get().getEmail()).isEqualTo("mohsen@gmail.com");
        assertThat(savedEmployee.get().getFirstName()).isEqualTo("Mohsen");
        assertThat(savedEmployee.get().getLastName()).isEqualTo("Sadeghbeigi");
    }

    @Test
    public void givenEmployeeObject_whenFindByFirstNameAndLastNameByNativeQuery_thenReturnEmployee() {
        // given - precondition or setup
        /*Employee employee = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();*/
        employeeRepository.save(employee);

        // when - action and the behaviour that we are going to test
        Optional<Employee> savedEmployee =
                employeeRepository.findByFirstNameAndLastNameWithNativeQuery(employee.getFirstName(),
                        employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotEmpty();
        assertThat(savedEmployee.get().getEmail()).isEqualTo("mohsen@gmail.com");
        assertThat(savedEmployee.get().getFirstName()).isEqualTo("Mohsen");
        assertThat(savedEmployee.get().getLastName()).isEqualTo("Sadeghbeigi");
    }

    @Test
    public void givenEmployeeObject_whenFindByFirstNameAndLastNameByNativeQueryAndNameParams_thenReturnEmployee() {
        // given - precondition or setup
        /*Employee employee = Employee.builder()
                .firstName("Mohsen")
                .lastName("Sadeghbeigi")
                .email("mohsen@gmail.com")
                .build();*/
        employeeRepository.save(employee);

        // when - action and the behaviour that we are going to test
        Optional<Employee> savedEmployee =
                employeeRepository.findByFirstNameAndLastNameWithNativeQueryAndNameParams(employee.getFirstName(),
                        employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotEmpty();
        assertThat(savedEmployee.get().getEmail()).isEqualTo("mohsen@gmail.com");
        assertThat(savedEmployee.get().getFirstName()).isEqualTo("Mohsen");
        assertThat(savedEmployee.get().getLastName()).isEqualTo("Sadeghbeigi");
    }
}