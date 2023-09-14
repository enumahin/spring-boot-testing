package com.alienworkspace.testing.springtesting.service;

import com.alienworkspace.testing.springtesting.exception.EmailAlreadyExistsException;
import com.alienworkspace.testing.springtesting.exception.ResourceNotFoundException;
import com.alienworkspace.testing.springtesting.model.Employee;
import com.alienworkspace.testing.springtesting.repository.EmployeeRepository;
import com.alienworkspace.testing.springtesting.service.impl.EmployeeServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .id(1L)
                .firstName("Kim")
                .lastName("west")
                .email("kim_west@gmail.com")
                .build();
    }

//  JUnit test for save Employee Operation
    @DisplayName("JUnit test for save Employee Operation")
    @Test
    public void givenEmployeeObject_whenSaveEmployeeMethod_thenReturnEmployeeObject(){
        // given - declaration or setup

        // The code below is called stubbing, and stubbing states that for every dependency call in the method under test
        // We need to mock all of them, like here, the save employee method calls employeeRepository.findByEmail and
        // employeeRepository.save so that's why are stubbing the 2 methods below.
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        // when - action or behaviour that we are going to test
        Employee savedEmployee = employeeService.create(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

//  JUnit test for save Employee Operation
    @DisplayName("JUnit test for save Employee Operation which throws exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenReturnThrowsException(){
        // given - declaration or setup

        // The code below is called stubbing, and stubbing states that for every dependency call in the method under test
        // We need to mock all of them, like here, the save employee method calls employeeRepository.findByEmail
        // so that's why are stubbing the 1 methods below.
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        // when - action or behaviour that we are going to test
        Assertions.assertThrows(EmailAlreadyExistsException.class, () -> employeeService.create(employee));
        // then - verify the output (After throwing the email exist exception, the execution control should continue
        // , that means the employeeRepository should not save any employee)
        verify(employeeRepository, never()).save(any(Employee.class));
    }

//  JUnit test for Operation
    @DisplayName("JUnit test for Find All Operation")
    @Test
    public void givenEmployeeObjects_whenCalledGetAllEmployees_thenReturnListOfEmployees(){
        // given - declaration or setup
        Employee employee2 = Employee.builder()
                .id(2L)
                .firstName("Imo")
                .lastName("Kamsi")
                .email("imo_kam@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employee2));

        // when - action or behaviour that we are going to test
        List<Employee> employees = employeeService.findAll();

        // then - verify the output
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isGreaterThan(0);
    }

//  JUnit test for Operation
    @DisplayName("JUnit test for Find All Operation with empty list")
    @Test
    public void givenEmptyEmployeeObjects_whenCalledGetAllEmployees_thenReturnEmptyListOfEmployees(){
        // given - declaration or setup
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when - action or behaviour that we are going to test
        List<Employee> employees = employeeService.findAll();

        // then - verify the output
        assertThat(employees).isEmpty();
        assertThat(employees.size()).isEqualTo(0);
    }


    //  JUnit test for Operation
    @DisplayName("JUnit test for Find Employee By Id Operation")
    @Test
    public void givenEmployeeObject_whenCalledGetEmployeeById_thenReturnEmployee(){
        // given - declaration or setup
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when - action or behaviour that we are going to test
        Employee employee = employeeService.findById(1L).get();

        // then - verify the output
        assertThat(employee).isNotNull();
        assertThat(employee).isInstanceOf(Employee.class);
    }


    //  JUnit test for Operation
    @DisplayName("JUnit test for Find Employee By Id Operation With Exception")
    @Test
    public void givenEmployeeId_whenCalledGetEmployeeById_thenReturnEmployeeWithException(){
        // given - declaration or setup
        given(employeeRepository.findById(3L)).willReturn(Optional.empty());

        // when - action or behaviour that we are going to test
        Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.findById(3L)
        );

        // then - verify the output
        never();
    }

//  JUnit test for Operation
//  @DisplayName("JUnit test for Operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        // given - declaration or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        employee.setFirstName("Updated");
        employee.setLastName("Record");

        // when - action or behaviour that we are going to test
        Employee updatedEmployee = employeeService.update(employee);

        // then - verify the output
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Updated");
        assertThat(updatedEmployee.getLastName()).isEqualTo("Record");
        assertThat(updatedEmployee).isInstanceOf(Employee.class);
    }

//  JUnit test for Delete Employee Operation
    @DisplayName("JUnit test for Delete Employee Operation")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturnNothing(){
        // given - declaration or setup
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        willDoNothing().given(employeeRepository).deleteById(1L);

        // when - action or behaviour that we are going to test
        employeeService.deleteById(1L);

        // then - verify the output
        verify(employeeRepository, times(1)).deleteById(1L);
    }
}
