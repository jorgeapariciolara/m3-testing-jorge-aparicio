package com.example.demo.service.mockito;

import com.example.demo.domain.Employee;
import com.example.demo.domain.SmartPhone;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.EmployeeRepositoryImpl;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceImplMockitoTest {

    EmployeeService service;
    EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        EmployeeRepository repository = new EmployeeRepositoryImpl();
        service = new EmployeeServiceImpl(repository);
    }

    @DisplayName("Contar el número de empleados")
    @Test
    void count() {
        when(repository.count()).thenReturn(3);
        Integer result = service.count();
        assertNotNull(result);
        assertEquals(3, result);
        verify(repository.count());
    }

    @DisplayName("Buscar un empleado utilizando el id")
    @Test
    void findOneTest() {
        Employee emp = new Employee(1L, "Empleado1", 47);
        when(repository.findOne(1L)).thenReturn(emp);
        Employee result = service.findOne(1L);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Empleado1", result.getName());
        assertEquals(47, result.getAge());
    }

    @DisplayName("Buscar un empleado con cualquier id")
    @Test
    void findOneAnyTest() {
        repository = mock(EmployeeRepository.class);
        service = new EmployeeServiceImpl(repository);
        Employee emp = new Employee(2L, "Empleado2", 47);
        when(repository.findOne(anyLong())).thenReturn(emp);
        Employee result = service.findOne(2L);
        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals("Empleado2", result.getName());
        assertEquals(47, result.getAge());
        verify(repository).findOne(anyLong());
    }

    @DisplayName("Guardar un empleado de id nulo")
    @Test
    void saveNullTest() {
        repository = mock(EmployeeRepository.class);
        service = new EmployeeServiceImpl(repository);
        Employee emp = new Employee(null, "EmpleadoNull", 37);
        when(repository.save(emp)).thenReturn(emp);
        Employee result = service.save(emp);
        assertNotNull(result);
        assertNotNull(result.getId()); // No debería asignar id nulo a estos empleados ¿o sí?
        assertNotNull(result.getName());
        assertNotNull(result.getAge());
        assertEquals(4, result.getId()); // Debería asignar el id más alto del repositorio
        verify(repository).save(emp);
    }

    @DisplayName("Guardar un empleado de id inexistente en la base de datos")
    @Test
    void saveNotExistTest() {
        repository = mock(EmployeeRepository.class);
        service = new EmployeeServiceImpl(repository);
        Employee emp = new Employee(999L, "Empleado999", 37);
        when(repository.save(emp)).thenReturn(emp);
        Employee result = service.save(emp);
        assertNotNull(result);
        assertNotNull(result.getId()); // No debería asignar id nulo a estos empleados ¿o sí?
        assertNotNull(result.getName());
        assertNotNull(result.getAge());
        assertEquals(4, result.getId()); // Debería asignar el id más alto del repositorio
        verify(repository).save(emp);
    }

    @DisplayName("Guardar un empleado de id = 0")
    @Test
    void saveZeroTest() {
        repository = mock(EmployeeRepository.class);
        service = new EmployeeServiceImpl(repository);
        Employee emp = new Employee(0L, "Empleado0", 37);
        when(repository.save(emp)).thenReturn(emp);
        Employee result = service.save(emp);
        assertNotNull(result);
        assertNotNull(result.getId()); // No debería asignar id = 0 a estos empleados ¿o sí?
        assertNotNull(result.getName());
        assertNotNull(result.getAge());
        assertEquals(4, result.getId()); // Debería asignar el id más alto del repositorio
        verify(repository).save(emp);
    }

    @DisplayName("Guardar un empleado de id < 0")
    @Test
    void saveNegativeTest() {
        repository = mock(EmployeeRepository.class);
        service = new EmployeeServiceImpl(repository);
        Employee emp = new Employee(-5L, "EmpleadoNegativo", 37);
        when(repository.save(emp)).thenReturn(emp);
        Employee result = service.save(emp);
        assertNotNull(result);
        assertNotNull(result.getId()); // No debería asignar id = 0 a estos empleados ¿o sí?
        assertNotNull(result.getName());
        assertNotNull(result.getAge());
        assertEquals(4, result.getId()); // Debería asignar el id más alto del repositorio
        verify(repository).save(emp);
    }
}
    /*
    @DisplayName("Borrar un empleado utilizando el id")
    @Test
    void deleteOk() {
        repository = mock(EmployeeRepository.class);
        service = new EmployeeServiceImpl(repository);
        Employee emp = new Employee(anyLong(),"Empleado10",57);
        when(repository.delete(10L)).thenReturn
        assertTrue(repository.count() > 0);
        boolean result = service.delete(anyLong());
        assertTrue(result);
        assertEquals(2, repository.count());
        verify(repository).delete(anyLong());
    }
    @DisplayName("Borrar todos los empleados")
    @Test
    void deleteAll() {
        repository = mock(EmployeeRepository.class);
        service = new EmployeeServiceImpl(repository);
        when (repository.()).thenThrow();
        assertTrue(service.count() > 0);
        service.deleteAll();
        assertEquals(0, service.count());
        verify(repository).deleteAll();
    }
    */