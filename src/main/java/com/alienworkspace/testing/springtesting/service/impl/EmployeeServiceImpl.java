package com.alienworkspace.testing.springtesting.service.impl;

import com.alienworkspace.testing.springtesting.exception.EmailAlreadyExistsException;
import com.alienworkspace.testing.springtesting.exception.ResourceNotFoundException;
import com.alienworkspace.testing.springtesting.model.Employee;
import com.alienworkspace.testing.springtesting.repository.EmployeeRepository;
import com.alienworkspace.testing.springtesting.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        if(employeeRepository.findByEmail(employee.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("Employee", employee.getEmail());
        }

        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        return employeeRepository.findById(employee.getId())
                .map(savedEmployee -> {
                    savedEmployee.setFirstName(employee.getFirstName());
                    savedEmployee.setLastName(employee.getLastName());
                    savedEmployee.setEmail(employee.getEmail());
                    return employeeRepository.save(savedEmployee);
                }).orElseThrow(
                        () -> new ResourceNotFoundException("Employee", "ID", employee.getId().toString())
                );
    }

    @Override
    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }

    @Override
    public void deleteById(Long employeeId) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Employee", "ID", employeeId.toString())
                );
        employeeRepository.deleteById(existingEmployee.getId());
    }

    @Override
    public Optional<Employee> findById(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
                if(employee.isEmpty())
                    throw  new ResourceNotFoundException("Employee", "ID", employeeId.toString());
                return employee;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
