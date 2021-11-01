package com.example.demo.service.junit;

import com.example.demo.domain.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.EmployeeRepositoryImpl;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class EmployeeServiceImplTest {

    EmployeeService service;

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
            assertAll(
                    () -> assertNotNull(employee),
                    () -> assertEquals(1l, employee.getId()),
                    () -> assertEquals("Emp 1", employee.getName()),
                    () -> assertEquals(30, employee.getAge())
            );
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
            assertAll(
                    () -> assertTrue(employeeOpt.isPresent()),
                    () -> assertEquals(1l, employeeOpt.get().getId()),
                    () -> assertEquals("Emp 1", employeeOpt.get().getName()),
                    () -> assertEquals(30, employeeOpt.get().getAge())
            );
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
            assertAll(
                    () -> assertNotNull(result),
                    () -> assertNotNull(result.getId()),
                    () -> assertEquals(4, result.getId()),
                    () -> assertEquals("Nombre", result.getName()),
                    () -> assertEquals(35, result.getAge())
            );
        }
        @DisplayName("Comprobar que se asigna un id cuando el id = 0")
        @Test
        void saveIdZeroTest(){
            Employee employee = new Employee(0L,"Nombre",35);
            Employee result = service.save(employee);
            assertAll(
                    () -> assertNotNull(result),
                    () -> assertNotNull(result.getId()),
                    () -> assertEquals(4, result.getId()),
                    () -> assertEquals("Nombre", result.getName()),
                    () -> assertEquals(35, result.getAge())
            );
        }
        @DisplayName("Comprobar qué pasa con un id < 0")
        @Test
        void saveNegativeTest(){
            Employee employee = new Employee(-7L,"Nombre",35);
            Employee result = service.save(employee);
            assertAll(
                    () -> assertNotNull(result),
                    () -> assertNotNull(result.getId()),
                    () -> assertEquals(4, result.getId()),
                    // org.opentest4j.AssertionFailedError: expected: <4> but was: <-7>
                    // En mi opinión, este método no debería asignar el id=-7 ¿o sí?
                    // Se podría cambiar el código e incluir esta excepción (id==null||id<=0)
                    () -> assertEquals("Nombre", result.getName()),
                    () -> assertEquals(35, result.getAge())
            );
        }
        @DisplayName("Comprobar que se actualizan los empleados")
        @Test
        void saveUpdateTest(){
            Employee employee = new Employee(1L, "Emp 1 - EDITADO", 30);
            assertEquals(3,service.count());
            Employee result = service.save(employee);
            assertEquals(3, service.count());
            Employee emp1  = service.findOne(1L);
            assertAll(
                    () -> assertEquals(1L,result.getId()),
                    () -> assertEquals("Emp 1 - EDITADO",emp1.getName()),
                    () -> assertEquals(30,emp1.getAge())
            );
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



