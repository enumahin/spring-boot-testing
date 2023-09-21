package com.alienworkspace.testing.springtesting.integration;

import com.alienworkspace.testing.springtesting.model.Employee;
import com.alienworkspace.testing.springtesting.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        employeeRepository.deleteAll();
    }

    //  JUnit test for Operation
    @DisplayName("JUnit test for Create Employee Operation")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnTheEmployeeObject() throws Exception {
        // given - declaration or setup
        Employee employee = Employee.builder()
                .firstName("Israel")
                .lastName("AKanji")
                .email("akanji@gmail.com")
                .build();

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
    @DisplayName("JUnit test for Throw Exception of Duplicate Email Operation")
    @Test
    public void givenEmployeeObjectWithDuplicateEmail_whenCreateEmployee_thenThrowException() throws Exception {

        // given - declaration or setup
        Employee employee = Employee.builder()
                .firstName("Israel")
                .lastName("AKanji")
                .email("akanji@gmail.com")
                .build();
        employeeRepository.save(employee);
        Employee employee2 = Employee.builder()
                .firstName("Emma")
                .lastName("Powerful")
                .email("akanji@gmail.com")
                .build();

        // when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee2)));


        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());
//                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
//                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
//                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    //  JUnit test for Operation
    @DisplayName("JUnit test for Get all Employees Operation")
    @Test
    public void givenEmployeeObjects_whenGetAllEmployees_thenReturnListOfEmployees() throws Exception {
        // given - declaration or setup
        List<Employee> employees = new ArrayList<>();
        Employee employee = Employee.builder()
                .firstName("Israel")
                .lastName("AKanji")
                .email("akanji@gmail.com")
                .build();
        employees.add(employee);
        employees.add(Employee.builder().firstName("John").lastName("Ebuka").email("jebuka@gmail.com").build());
        employeeRepository.saveAll(employees);

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
        Employee employee = Employee.builder()
                .firstName("Israel")
                .lastName("AKanji")
                .email("akanji@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when - action or behaviour that we are going to test
        ResultActions actions = mockMvc.perform(get("/api/employees/{id}",employee.getId()));
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
        Employee employee = Employee.builder()
                .firstName("Israel")
                .lastName("AKanji")
                .email("akanji@gmail.com")
                .build();
        employeeRepository.save(employee);
        // when - action or behaviour that we are going to test
        ResultActions actions = mockMvc.perform(get("/api/employees/{id}",1));
        // then - verify the output
        actions.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    //  JUnit test for Operation
    @DisplayName("JUnit test for Update Employee Operation With Not Found")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() throws Exception {
        // given - declaration or setup
        Employee employee = Employee.builder()
                .firstName("Israel")
                .lastName("AKanji")
                .email("akanji@gmail.com")
                .build();
        employeeRepository.save(employee);

        Employee expectedEmployee = Employee.builder()
                .firstName("Abel")
                .lastName("Okeke")
                .email("aokeke@gmail.com")
                .build();

        // when - action or behaviour that we are going to test
        ResultActions actions = mockMvc.perform(put("/api/employees/{id}", employee.getId()).contentType(MediaType.APPLICATION_JSON)
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
        Employee employee = Employee.builder()
                .firstName("Israel")
                .lastName("AKanji")
                .email("akanji@gmail.com")
                .build();
        employeeRepository.save(employee);

        Employee expectedEmployee = Employee.builder()
                .firstName("Abel")
                .lastName("Okeke")
                .email("akanji@gmail.com")
                .build();

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
        Employee employee = Employee.builder()
                .firstName("Israel")
                .lastName("AKanji")
                .email("akanji@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when - action or behaviour that we are going to test
        ResultActions actions = mockMvc.perform(delete("/api/employees/{id}", employee.getId()));
        // then - verify the output
        actions.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", CoreMatchers.is("Deleted")));
    }

}
