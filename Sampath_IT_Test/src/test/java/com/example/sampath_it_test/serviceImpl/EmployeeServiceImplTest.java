package com.example.sampath_it_test.serviceImpl;

import com.example.sampath_it_test.models.Employee;
import com.example.sampath_it_test.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee(1L, "Tharindu", "Nisal", "tharindu.nisal@example.com", 75000.0);
    }

    @Test
    void testCreateEmployee() {
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee savedEmployee = employeeService.createEmployee(employee);

        assertNotNull(savedEmployee);
        assertEquals("Tharindu", savedEmployee.getFirstName());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = Arrays.asList(employee);
        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getAllEmployees();

        assertEquals(1, result.size());
        assertEquals("Tharindu", result.get(0).getFirstName());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetEmployeeById_Found() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee found = employeeService.getEmployeeById(1L);

        assertNotNull(found);
        assertEquals("Tharindu", found.getFirstName());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> employeeService.getEmployeeById(1L));

        assertEquals("Employee not found", exception.getMessage());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateEmployee_Found() {
        Employee updated = new Employee(null, "Updated", "User", "updated@example.com", 80000.0);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee result = employeeService.updateEmployee(1L, updated);

        assertEquals("Updated", result.getFirstName());
        assertEquals("User", result.getLastName());
        assertEquals("updated@example.com", result.getEmail());
        assertEquals(80000.0, result.getSalary());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> employeeService.updateEmployee(1L, employee));

        assertEquals("Employee not found", exception.getMessage());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void testDeleteEmployee() {
        doNothing().when(employeeRepository).deleteById(1L);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }
}
