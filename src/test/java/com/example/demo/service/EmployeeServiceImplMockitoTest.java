package com.example.demo.service;

import com.example.demo.domain.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.EmployeeRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class EmployeeServiceImplMockitoTest {

    EmployeeService service;
    EmployeeRepository repository;

    @BeforeEach
    void setUp(){
        EmployeeRepository repository = new EmployeeRepositoryImpl();
        service = new EmployeeServiceImpl(repository);
    }

    @DisplayName("Buscar un empleado con cualquier id")
    @Test
    void findOneAnyTest(){
        repository = mock(EmployeeRepository.class);
        service = new EmployeeServiceImpl(repository);
        Employee emp = new Employee(2L,"Empleado2",47);
        when(repository.findOne(anyLong())).thenReturn(emp);
        Employee result = service.findOne(2L);
        assertNotNull(result);
        assertEquals(2,result.getId());
        assertEquals("Empleado2",result.getName());
        assertEquals(47,result.getAge());
        verify(repository).findOne(2L);
    }


}
