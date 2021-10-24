package com.example.demo.domain;

import com.example.demo.repository.EmployeeRepositoryImpl;
import com.example.demo.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeMockitoTest {

    EmployeeRepositoryImpl employeeRepository;
    EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp(){
        employeeRepository = mock(EmployeeRepositoryImpl.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
        final Map<Long, Employee> employees = new HashMap<>();

        Employee emp1 = new Employee(1L, "Emp 1", 30);
        Employee emp2 = new Employee(2L, "Emp 2", 40);
        Employee emp3 = new Employee(3L, "Emp 3", 50);
        employees.put(1L, emp1);
        employees.put(2L, emp2);
        employees.put(3L, emp3);
    }

    @Test
    void getId() {
        Employee emp1 = new Employee(1L, "Emp 1", 30);
        when(employeeRepository.findOne(anyLong())).thenReturn(emp1);
        Employee employee = employeeService.findOne(anyLong());
        assertNotNull(employee);
        assertEquals(1,employee.getId());
        assertEquals("Emp1",employee.getName());
        assertEquals(30,employee.getAge());
        verify(employeeRepository.findOne(anyLong()));
    }

    @Test
    void setId() {
    }

    @Test
    void getName() {
    }

    @Test
    void setName() {
    }

    @Test
    void getAge() {
    }

    @Test
    void setAge() {
    }
}