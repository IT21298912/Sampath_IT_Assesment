package com.example.sampath_it_test.services;

import com.example.sampath_it_test.models.Employee;

import java.util.List;

public interface EmployeeService {

    public List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    Employee createEmployee(Employee employee);
    Employee updateEmployee(Long id, Employee employee);
    void deleteEmployee(Long id);

}
