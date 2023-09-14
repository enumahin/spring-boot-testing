package com.alienworkspace.testing.springtesting.service;

import com.alienworkspace.testing.springtesting.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee create(Employee employee);

    Employee update(Employee employee);

    void delete(Employee employee);

    void deleteById(Long employeeId);

    Optional<Employee> findById(Long employeeId);

    List<Employee> findAll();
}
