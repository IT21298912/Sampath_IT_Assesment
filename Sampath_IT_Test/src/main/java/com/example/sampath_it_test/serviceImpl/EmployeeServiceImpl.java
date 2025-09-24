package com.example.sampath_it_test.serviceImpl;

import com.example.sampath_it_test.models.Employee;
import com.example.sampath_it_test.repositories.EmployeeRepository;
import com.example.sampath_it_test.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("Employee not found"));
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Employee employee1 = employeeRepository.findById(id).orElseThrow(()-> new RuntimeException("Employee not found"));

        employee1.setEmail(employee.getEmail());
        employee1.setFirstName(employee.getFirstName());
        employee1.setSalary(employee.getSalary());
        employee1.setLastName(employee.getLastName());

        return employeeRepository.save(employee1);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);

    }
}
