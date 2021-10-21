package com.example.demo.service;

import com.example.demo.domain.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.EmployeeRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    EmployeeService service;
    EmployeeRepository repository;

    @BeforeEach
    void setUp(){
        EmployeeRepository repository = new EmployeeRepositoryImpl();
        service = new EmployeeServiceImpl(repository);
    }

    @DisplayName("Funcionalidad BUSCAR sobre empleados")
    @Nested
    class RetrieveTest {
        @DisplayName("Contar número de empleados")
        @Test
        void count() {
            Integer count = service.count();
            assertNotNull(count);
            assertEquals(3, count);
        }
        @DisplayName("Buscar todos los empleados")
        @Test
        void findAll() {
            List<Employee> employees = service.findAll();
            assertNotNull(employees);
            assertEquals(3, employees.size());
        }
        @DisplayName("Buscar un empleado de id conocido")
        @Test
        void findOneOKTest() {
            Employee employee = service.findOne(1L);
            assertNotNull(employee);
            assertEquals(1l, employee.getId());
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

        @DisplayName("Buscar un empleado con id que no existe en la base de datos")
        @Test
        void findOneNotExistTest() {
            Employee employee = service.findOne(999L);
            assertNull(employee);
        }
        @DisplayName("Buscar un empleado con id nulo")
        @Test
        void findOneExceptionTest(){
            assertThrows(
                    IllegalArgumentException.class,
                    ()->service.findOne(null)
            );
        }
        @DisplayName("Buscar un empleado - con Optional")
        @Test
        void findOneOKOptional() {
            Optional<Employee> employeeOpt = service.findOneOptional(1L);
            assertTrue(employeeOpt.isPresent());
            Long id = employeeOpt.get().getId();
            assertEquals(1, id);
        }
        @DisplayName("Buscar un empleado con id que no existe en la base de datos - con Optional")
        // ¡¡TIENE QUE SALTAR LA EXCEPCIÓN PORQUE ESTÁ PROGRAMADO ASÍ!!
        // java.lang.NullPointerException
        // Es una forma de avisar de que no está bien el id
        @Test
        void findOneNotExistOptional() {
            Optional<Employee> employeeOpt = service.findOneOptional(999L);
            assertTrue(employeeOpt.isPresent());
            assertTrue(employeeOpt.isEmpty());
        }
        @DisplayName("Buscar un empleado con id nulo - con Optional")
        @Test
        void findOneNullOptional() {
            Optional<Employee> employeeOpt = service.findOneOptional(null);
            assertTrue(employeeOpt.isEmpty());
        }
    }

    @DisplayName("Funcionalidad CREAR y MODIFICAR sobre empleados")
    @Nested
    class SaveTest {
        @DisplayName("Comprobar que se asigna un id cuando el id = null")
        @Test
        void saveIdNullTest(){
            Employee employee = new Employee(null,"Nombre",35);
            Employee result = service.save(employee);
            assertNotNull(result);
            assertNotNull(result.getId());
            assertEquals(4, result.getId());
        }
        @DisplayName("Comprobar que se asigna un id cuando el id = 0")
        @Test
        void saveIdZeroTest(){
            Employee employee = new Employee(0L,"Nombre",35);
            Employee result = service.save(employee);
            assertNotNull(result);
            System.out.println(result.getId());
            assertNotNull(result.getId());
            assertEquals(4, result.getId());
            assertEquals(4, service.count());
        }
        @DisplayName("Comprobar qué pasa con un id < 0")
        @Test
        void saveNegativeTest(){
            Employee employee = new Employee(-7L,"Nombre",35);
            Employee result = service.save(employee);
            assertNotNull(result);
            assertNotNull(result.getId());
            assertEquals(-7, result.getId());
            // org.opentest4j.AssertionFailedError:
            // En mi opinión, este método no debería asignar el id=-3 ¿o sí?
            // Podríamos cambiar el código e incluir la excepción de los id<0,
            // al igual que tiene id=null || id=0
            assertEquals(4, service.count());
        }
        @DisplayName("Comprobar que se actualizan los empleados")
        @Test
        void saveUpdateTest(){
            Employee employee = new Employee(1L, "Emp 1 - EDITADO", 30);
            assertEquals(3,service.count());
            Employee result = service.save(employee);
            assertEquals(3, service.count());
            assertEquals(1L,result.getId());
            Employee emp1  = service.findOne(1L);
            assertEquals("Emp 1 - EDITADO",emp1.getName());
        }
    }

    @DisplayName("Funcionalidad BORRAR sobre empleados")
    @Nested
    class DeleteTest {
        @DisplayName("Borrar un empleado con id nulo")
        @Test
        void deleteNullTest(){
            boolean result = service.delete(null);
            assertFalse(result);
        }
        @DisplayName("Borrar un empleado")
        @Test
        void deleteOKTest(){
            boolean result = service.delete(1L);
            assertTrue(result);
            assertTrue(service.count() > 0);
            service.delete(1L);
            assertEquals(2,service.count());
        }
        @DisplayName("Borrar un empleado con id que no existe en la base de datos")
        @Test
        void deleteNotExistsTest(){
            boolean result = service.delete(999L);
            assertFalse(result);
        }
        @DisplayName("Borrar todos los empleados")
        @Test
        void deleteAllTest(){
            assertTrue(service.count() > 0);
            service.deleteAll();
            assertEquals(0, service.count());
        }
    }
}



