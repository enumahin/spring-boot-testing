package com.alienworkspace.testing.springtesting.controller;

import com.alienworkspace.testing.springtesting.model.Employee;
import com.alienworkspace.testing.springtesting.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.create(employee);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable("id") Long employeeId){
        employee.setId(employeeId);
        return employeeService.findById(employee.getId())
                .map(savedEmployee -> {
                    savedEmployee.setFirstName(employee.getFirstName());
                    savedEmployee.setLastName(employee.getLastName());
                    savedEmployee.setEmail(employee.getEmail());
                    return new ResponseEntity<>(employeeService.update(savedEmployee), HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> findAllEmployee(){
        return employeeService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Employee> findById(@PathVariable("id") Long employeeId){
     return employeeService.findById(employeeId)
             .map(ResponseEntity::ok)
             .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteById(@PathVariable("id") Long employeeId){
         employeeService.deleteById(employeeId);
         return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
