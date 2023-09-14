package com.alienworkspace.testing.springtesting.controller;

import com.alienworkspace.testing.springtesting.model.Employee;
import com.alienworkspace.testing.springtesting.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.CoreMatchers.is;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;

    @BeforeEach
    void setup(){
        employee = Employee.builder()
                .firstName("Israel")
                .lastName("AKanji")
                .email("akanji@gmail.com")
                .build();
    }

//  JUnit test for Operation
    @DisplayName("JUnit test for Create Employee Operation")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnTheEmployeeObject() throws Exception {

        // given - declaration or setup
        given(employeeService.create(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));


        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

//  JUnit test for Operation
    @DisplayName("JUnit test for Get all Employees Operation")
    @Test
    public void givenEmployeeObjects_whenGetAllEmployees_thenReturnListOfEmployees() throws Exception {
        // given - declaration or setup
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        employees.add(Employee.builder().id(2L).firstName("John").lastName("Ebuka").email("jebuka@gmail.com").build());
        given(employeeService.findAll())
                .willReturn(employees);
        // when - action or behaviour that we are going to test
        ResultActions actions = mockMvc.perform(get("/api/employees"));
        // then - verify the output
        actions.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(employees.size())))
                .andExpect(jsonPath("$[0].firstName", is(employee.getFirstName())));
    }

//  JUnit test for Operation
    @DisplayName("JUnit test for Get Employee By Id Operation")
    @Test
    public void givenEmployeeObjectAndEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        // given - declaration or setup
        given(employeeService.findById(1L))
                .willReturn(Optional.of(employee));
        // when - action or behaviour that we are going to test
        ResultActions actions = mockMvc.perform(get("/api/employees/{id}",1));
        // then - verify the output
        actions.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

//  JUnit test for Operation
    @DisplayName("JUnit test for Get Employee By Id Operation With Not Found")
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmployeeNotFound() throws Exception {
        // given - declaration or setup
        given(employeeService.findById(3L))
                .willReturn(Optional.empty());
        // when - action or behaviour that we are going to test
        ResultActions actions = mockMvc.perform(get("/api/employees/{id}",3));
        // then - verify the output
        actions.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

//  JUnit test for Operation
    @DisplayName("JUnit test for Update Employee Operation With Not Found")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() throws Exception {
        // given - declaration or setup

        given(employeeService.findById(2L)).willReturn(Optional.of(employee));

        Employee expectedEmployee = Employee.builder()
                .firstName("Abel")
                .lastName("Okeke")
                .email("akanji@gmail.com")
                .build();
        given(employeeService.update(any(Employee.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when - action or behaviour that we are going to test
        ResultActions actions = mockMvc.perform(put("/api/employees/{id}", 2L).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expectedEmployee)));
        // then - verify the output
        actions.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Abel")))
                .andExpect(jsonPath("$.lastName", is("Okeke")))
                .andExpect(status().isOk());
    }
//  JUnit test for Operation
    @DisplayName("JUnit test for Update Employee Operation With Not Found")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnNotFound() throws Exception {
        // given - declaration or setup

        given(employeeService.findById(1L)).willReturn(Optional.empty());

        Employee expectedEmployee = Employee.builder()
                .firstName("Abel")
                .lastName("Okeke")
                .email("akanji@gmail.com")
                .build();
        given(employeeService.update(any(Employee.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when - action or behaviour that we are going to test
        ResultActions actions = mockMvc.perform(put("/api/employees/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expectedEmployee)));
        // then - verify the output
        actions.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

//  JUnit test for Operation
    @DisplayName("JUnit test for Delete Employee Operation")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenSuccess() throws Exception {
        // given - declaration or setup

        willDoNothing().given(employeeService).deleteById(1L);
        // when - action or behaviour that we are going to test
        ResultActions actions = mockMvc.perform(delete("/api/employees/{id}", 1L));
        // then - verify the output
        actions.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", CoreMatchers.is("Deleted")));
    }
}
