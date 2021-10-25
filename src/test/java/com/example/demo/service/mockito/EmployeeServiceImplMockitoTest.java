package com.example.demo.service.mockito;

import com.example.demo.domain.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceImplMockitoTest {

    EmployeeRepository repositoryMock;
    EmployeeService service;

    @BeforeEach
    void setUp() {
        repositoryMock = mock(EmployeeRepository.class);
        service = new EmployeeServiceImpl(repositoryMock);
    }
    @DisplayName("Funcionalidad BUSCAR sobre empleados")
    @Nested
    class RetrieveTest {
        @DisplayName("Contar el número de empleados")
        @Test
        void count() {
            when(repositoryMock.count()).thenReturn(3);
            Integer result = service.count();
            assertNotNull(result);
            assertEquals(3, result);
            verify(repositoryMock).count();
        }

        @DisplayName("Buscar todos los empleados")
        @Test
        void findAll() {
            List<Employee> employees = Arrays.asList(
                    new Employee(1l, "Paco", 35),
                    new Employee(2l, "Pilar", 27),
                    new Employee(3l, "Lola", 42),
                    new Employee(4l, "Pedro", 19)
            );
            when(repositoryMock.findAll()).thenReturn(employees);

            List<Employee> result = service.findAll();

            assertNotNull(result);
            assertEquals(4, result.size());
            verify(repositoryMock).findAll();
        }

        @DisplayName("Buscar un empleado utilizando el id")
        @Test
        void findOne() {
            List<Employee> employees = Arrays.asList(
                    new Employee(1l, "Paco", 35),
                    new Employee(2l, "Pilar", 27),
                    new Employee(3l, "Lola", 42),
                    new Employee(4l, "Pedro", 19)
            );
            when(repositoryMock.findOne(1L)).thenReturn(employees.get(0));
            when(repositoryMock.findOne(2L)).thenReturn(employees.get(1));
            when(repositoryMock.findOne(3L)).thenReturn(employees.get(2));
            when(repositoryMock.findOne(4L)).thenReturn(employees.get(3));

            Employee employee1 = service.findOne(1L);
            Employee employee2 = service.findOne(2L);
            Employee employee3 = service.findOne(3L);
            Employee employee4 = service.findOne(4L);

            assertNotNull(employee1);
            assertEquals("Paco", employee1.getName());
            assertEquals(35, employee1.getAge());
            assertNotNull(employee2);
            assertEquals("Pilar", employee2.getName());
            assertEquals(27, employee2.getAge());
            assertNotNull(employee3);
            assertEquals("Lola", employee3.getName());
            assertEquals(42, employee3.getAge());
            assertNotNull(employee1);
            assertEquals("Pedro", employee4.getName());
            assertEquals(19, employee4.getAge());
            verify(repositoryMock).findOne(1L);
            verify(repositoryMock).findOne(2L);
            verify(repositoryMock).findOne(3L);
            verify(repositoryMock).findOne(4L);
        }

        @DisplayName("OPTIONAL - Buscar un empleado utilizando el id")
        @Test
        void findOneOptional() {
            Employee employee1 = new Employee(1l, "Paco", 35);
            Employee employee2 = new Employee(2l, "Pilar", 27);
            Employee employee3 = new Employee(3l, "Lola", 42);
            Employee employee4 = new Employee(4l, "Pedro", 19);
            when(repositoryMock.findOne(1L)).thenReturn(employee1);
            when(repositoryMock.findOne(2L)).thenReturn(employee2);
            when(repositoryMock.findOne(3L)).thenReturn(employee3);
            when(repositoryMock.findOne(4L)).thenReturn(employee4);
            when(repositoryMock.findOne(anyLong())).thenReturn(employee4);

            Optional<Employee> employeeOpt1 = service.findOneOptional(1L);
            Optional<Employee> employeeOpt2 = service.findOneOptional(2L);

            assertTrue(employeeOpt1.isPresent());
            assertTrue(employeeOpt2.isPresent());
        }

        @DisplayName("OPTIONAL - Buscar un empleado con id nulo")
        @Test
        void findOneNullOptional() {
            Employee employee1 = new Employee(1l, "Paco", 35);
            when(repositoryMock.findOne(anyLong())).thenReturn(null);

            Optional<Employee> employeeOpt = service.findOneOptional(1L);

            assertTrue(employeeOpt.isEmpty()); // ¡¡NOS SALTA UN NULL.POINTER.EXCEPTION!!
            verify(repositoryMock).findOne(anyLong());
        }

        @DisplayName("OPTIONAL - Buscar un empleado con id inexistente en la base de datos")
        @Test
        void findOneNotContainsOptional() {
            when(repositoryMock.findOne(anyLong())).thenThrow(IllegalArgumentException.class);

            Optional<Employee> employeeOpt = service.findOneOptional(999L);

            assertTrue(employeeOpt.isEmpty());
            verify(repositoryMock).findOne(anyLong());
        }
    }

    @DisplayName("Funcionalidad CREAR y MODIFICAR sobre empleados")
    @Nested
    class SaveTest {
        @DisplayName("Guardar un empleado")
        @Test
        void save() {
            Employee employee = new Employee();
            Employee employee1 = new Employee(1l, "Paco", 35);
            Employee employee2 = new Employee(2l, "Pilar", 27);
            Employee employee3 = new Employee(3l, "Lola", 42);
            Employee employee4 = new Employee(4l, "Pedro", 19);
            when(repositoryMock.save(any())).thenReturn(employee);

            Employee result = service.save(employee);

            assertNotNull(result);
            verify(repositoryMock).save(employee);
        }
    }

    @DisplayName("Funcionalidad BORRAR sobre empleados")
    @Nested
    class DeleteTest {
        @DisplayName("Borrar un empleado")
        @Test
        void delete() {
            when(repositoryMock.delete(any())).thenReturn(true);
            boolean result = service.delete(1l);
            assertTrue(result);
            verify(repositoryMock).delete(anyLong());
        }

        @DisplayName("Borrar todos los empleados")
        @Test
        void deleteAll() {
            service.deleteAll();
            verify(repositoryMock).deleteAll();
        }
    }
}