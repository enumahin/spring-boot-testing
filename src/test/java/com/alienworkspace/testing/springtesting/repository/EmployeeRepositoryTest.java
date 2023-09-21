//package com.alienworkspace.testing.springtesting.repository;
//
//import com.alienworkspace.testing.springtesting.model.Employee;
//import static org.assertj.core.api.Assertions.assertThat;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//import java.util.Optional;
//
//@DataJpaTest
//public class EmployeeRepositoryTest {
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    private Employee employee;
//
//    @BeforeEach
//    public void addEmployee(){
////         employee = Employee.builder()
////                .firstName("John")
////                .lastName("Doe")
////                .email("johnDoe@gmail.com")
////                .build();
//
//    }
//
//    //JUnit test for save Employee Operation
////    @DisplayName("JUnit test for save Employee Operation")
//    @Test
//    public void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee(){
//
//        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("John")
//                .lastName("Doe")
//                .email("johnDoe@gmail.com")
//                .build();
//
//        //when - action or the behavior that we are going to test
//        Employee savedEmployee = employeeRepository.save(employee);
//
//        //then - verify the output
//        assertThat(savedEmployee).isNotNull();
//        assertThat(savedEmployee.getId()).isGreaterThan(0L);
//    }
//
//    // JUnit test for get all Employees Operation
//
//    @Test
//    public void givenListOfEmployees_whenFindAllEmployees_thenReturnsListOfEmployees(){
//
//        // Given - No data
//        Employee employee = Employee.builder()
//                .firstName("John")
//                .lastName("Doe")
//                .email("johnDoe@gmail.com")
//                .build();
//
//        Employee employee1 = Employee.builder()
//                .firstName("Umar")
//                .lastName("Alba")
//                .email("umar_alba@gmail.com")
//                .build();
//
//        employeeRepository.saveAll(List.of(employee, employee1));
//
//        // When call Get All Employees
//        List<Employee> employees = employeeRepository.findAll();
//
//        // Then verify the output
//        assertThat(employees).isNotNull();
//        assertThat(employees.size()).isEqualTo(2);
//    }
//
//    //JUnit test to get employee by id Operation
//    @DisplayName("JUnit test to get employee by id Operation")
//    @Test
//    public void givenEmployee_whenCallFindById_thenReturnAnEmployeeObject(){
//            // given - declaration or setup
//        Employee employee = Employee.builder()
//                .firstName("John")
//                .lastName("Doe")
//                .email("johnDoe@gmail.com")
//                .build();
//
//            employeeRepository.save(employee);
////            employeeRepository.save(employee1);
//
//            // when - action or behaviour that we are going to test
//            Employee returnedEmployee = employeeRepository.findById(employee.getId()).orElse(null);
//
//            // then - verify the output
//            assertThat(returnedEmployee).isNotNull();
//            assertThat(returnedEmployee.getFirstName()).isEqualTo(employee.getFirstName());
//    }
//
//    //JUnit test for get employee by email Operation
//    @DisplayName("JUnit test for get employee by email Operation")
//    @Test
//    public void givenAddEmployees_whenCallFindEmployeeByEmail_thenReturnMatchingEmployeeObject(){
//            // given - declaration or setup
//        Employee employee = Employee.builder()
//                .firstName("John")
//                .lastName("Doe")
//                .email("johnDoe@gmail.com")
//                .build();
////
//        Employee employee1 = Employee.builder()
//                .firstName("Umar")
//                .lastName("Alba")
//                .email("umar_alba@gmail.com")
//                .build();
//        employeeRepository.saveAll(List.of(employee, employee1));
//
//
//            // when - action or behaviour that we are going to test
//        Employee employee2 = employeeRepository.findByEmail("johnDoe@gmail.com").orElse(null);
//
//            // then - verify the output
//        assertThat(employee2).isNotNull();
//        assertThat(employee2).isInstanceOf(Employee.class);
//    }
//
//    //JUnit test for update employee Operation
//    @DisplayName("JUnit test for update employee Operation")
//    @Test
//    public void givenEmployeeObject_whenUpdateEmployee_thenUpdatedEmployeeObject(){
//        // given - declaration or setup
//        Employee employee = Employee.builder()
//                .firstName("Umar")
//                .lastName("Alba")
//                .email("umar_alba@gmail.com")
//                .build();
//        Employee emp = employeeRepository.save(employee);
//        System.out.println(emp);
//        // when - action or behaviour that we are going to test
//        Employee savedEmployee = employeeRepository.findById(employee.getId()).orElse(null);
//        assertThat(savedEmployee).isNotNull();
//        savedEmployee.setLastName("Does");
//        savedEmployee.setEmail("johnDoes@gmail.com");
//        Employee updatedEmployee = employeeRepository.save(savedEmployee);
//
//        // then - verify the output
//        assertThat(updatedEmployee.getLastName()).isEqualTo("Does");
//        assertThat(updatedEmployee.getEmail()).isEqualTo("johnDoes@gmail.com");
//    }
//
//    //JUnit test for Operation
//    @DisplayName("JUnit test for delete employee Operation")
//    @Test
//    public void givenEmployeeObject_whenDeleteEmployee_thenReturnTrue(){
//        // given - declaration or setup
//        Employee employee = Employee.builder()
//                .firstName("Umar")
//                .lastName("Alba")
//                .email("umar_alba@gmail.com")
//                .build();
//        employeeRepository.save(employee);
//
//        // when - action or behaviour that we are going to test
//        employeeRepository.delete(employee);
//        Optional<Employee> removedEmployee = employeeRepository.findById(employee.getId());
//
//        // then - verify the output
//        assertThat(removedEmployee).isEmpty();
//    }
//
//    //JUnit test for custom query using JPQL with index Operation
//    @DisplayName("JUnit test for custom query using JPQL with index Operation")
//    @Test
//    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject(){
//        // given - declaration or setup
//        Employee employee = Employee.builder()
//                .firstName("Umar")
//                .lastName("Alba")
//                .email("umar_alba@gmail.com")
//                .build();
//        employeeRepository.save(employee);
//        String firstName = "Umar";
//        String lastName = "Alba";
//
//        // when - action or behaviour that we are going to test
//        Employee returnedEmployee = employeeRepository.findByCustomJPQL(firstName, lastName);
//
//        // then - verify the output
//        assertThat(returnedEmployee).isNotNull();
//        assertThat(returnedEmployee).isInstanceOf(Employee.class);
//    }
//
//
//    //JUnit test for custom query using JPQL with named params Operation
//    @DisplayName("JUnit test for custom query using JPQL with named params Operation")
//    @Test
//    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject(){
//        // given - declaration or setup
//        Employee employee = Employee.builder()
//                .firstName("Umar")
//                .lastName("Alba")
//                .email("umar_alba@gmail.com")
//                .build();
//        employeeRepository.save(employee);
//        String firstName = "Umar";
//        String lastName = "Alba";
//
//        // when - action or behaviour that we are going to test
//        Employee returnedEmployee = employeeRepository.findByCustomJPQLNamedParams(firstName, lastName);
//
//        // then - verify the output
//        assertThat(returnedEmployee).isNotNull();
//        assertThat(returnedEmployee).isInstanceOf(Employee.class);
//    }
//
//
//
//    //JUnit test for custom query using Native Query Operation
//    @DisplayName("JUnit test for custom query using native Query Operation")
//    @Test
//    public void givenFirstNameAndLastName_whenFindByNativeQuery_thenReturnEmployeeObject(){
//        // given - declaration or setup
//        Employee employee = Employee.builder()
//                .firstName("Umar")
//                .lastName("Alba")
//                .email("umar_alba@gmail.com")
//                .build();
//        employeeRepository.save(employee);
//        String firstName = "Umar";
//        String lastName = "Alba";
//
//        // when - action or behaviour that we are going to test
//        Employee returnedEmployee = employeeRepository.findByNativeQuery(firstName, lastName);
//
//        // then - verify the output
//        assertThat(returnedEmployee).isNotNull();
//        assertThat(returnedEmployee).isInstanceOf(Employee.class);
//    }
//
//    //JUnit test for custom query using Native Query with named Params Operation
//    @DisplayName("JUnit test for custom query using native Query with named params Operation")
//    @Test
//    public void givenFirstNameAndLastName_whenFindByNativeQueryNamed_thenReturnEmployeeObject(){
//        // given - declaration or setup
//        Employee employee = Employee.builder()
//                .firstName("Umar")
//                .lastName("Alba")
//                .email("umar_alba@gmail.com")
//                .build();
//        employeeRepository.save(employee);
//        String firstName = "Umar";
//        String lastName = "Alba";
//
//        // when - action or behaviour that we are going to test
//        Employee returnedEmployee = employeeRepository.findByNativeQueryNamedParams(firstName, lastName);
//
//        // then - verify the output
//        assertThat(returnedEmployee).isNotNull();
//        assertThat(returnedEmployee).isInstanceOf(Employee.class);
//    }
//}
