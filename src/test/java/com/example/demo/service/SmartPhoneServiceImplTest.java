package com.example.demo.service;

import com.example.demo.domain.SmartPhone;
import com.example.demo.domain.pieces.Battery;
import com.example.demo.domain.pieces.CPU;
import com.example.demo.domain.pieces.Camera;
import com.example.demo.domain.pieces.RAM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SmartPhoneServiceImplTest {

    SmartPhoneServiceImpl service = new SmartPhoneServiceImpl();

    @BeforeEach
    void setUp () {
        service = new SmartPhoneServiceImpl();
    }

    @DisplayName("Funcionalidad BUSCAR sobre smartphones")
    @Nested
    class RetrieveTest {
        @DisplayName("Contar número de smartphones")
        @Test
        void countTest() {
            Integer num = service.count();
            assertAll(
                    () -> assertNotNull(num),
                    () -> assertTrue(num > 0),
                    () -> assertEquals(3, num)
            );
        }
        @DisplayName("Buscar todos los smartphones")
        @Test
        void findAllTest() {
            List<SmartPhone> smartPhones = service.findAll();
            assertAll(
                    () -> assertNotNull(smartPhones),
                    () -> assertEquals(3, smartPhones.size())
            );
        }
        @DisplayName("Buscar el smartphone de id=1")
        @Test
        void findOnePhone1Test() {
            SmartPhone phone1 = service.findOne(1L);
            assertAll(
                    () -> assertNotNull(phone1),
                    () -> assertEquals(1l, phone1.getId())
            );
        }
        @DisplayName("Buscar el smartphone de id=999")
        @Test
        void findOnePhone999Test() {
            SmartPhone phone999 = service.findOne(999L);
            assertNull(phone999); // Pensamos que tiene que devolver null y lo comprobamos
        }
        @DisplayName("Buscar el smartphone de id=null")
        @Test
        void findOneExceptionTest() {
            // THROWS -> Verifica si se ha lanzado una excepción
            assertThrows(
                    IllegalArgumentException.class,
                    () -> service.findOne(null)
            );
        }
        @DisplayName("Buscar smartphones con wifi")
        @Test
        void findByWifiTrueTest(){
            List<SmartPhone> smartPhones = service.findByWifi(true);
            assertAll(
                    () -> assertNotNull(smartPhones),
                    () -> assertEquals(2, smartPhones.size())
            );
        }
        @DisplayName("Buscar smartphones sin wifi")
        @Test
        void findByWifiFalseTest(){
            List<SmartPhone> smartPhones = service.findByWifi(false);
            assertAll(
                    () -> assertNotNull(smartPhones),
                    () -> assertEquals(1, smartPhones.size())
            );
        }
    }

    @DisplayName("Funcionalidad CREAR y MODIFICAR sobre smartphones")
    @Nested
    class SaveTest {
        @DisplayName("Comprobar que se asigna un id cuando el id = null")
        @Test
        void saveIdNullTest(){
            SmartPhoneServiceImpl service = new SmartPhoneServiceImpl();
            SmartPhone phone = new SmartPhone(null, "Otro smartphone",
                    new RAM(1L, "DDR4", 8),
                    new Battery(1L, 4500.0),
                    new CPU(1L, 4),
                    false,
                    new Camera(1L, "front camera", 12.5));
            SmartPhone result = service.save(phone);
            assertNotNull(result);
            assertNotNull(result.getId());
            assertEquals(4, result.getId());
        }
        @DisplayName("Comprobar que se asigna un id cuando el id = 0")
        @Test
        void saveIdZeroTest(){
            SmartPhoneServiceImpl service = new SmartPhoneServiceImpl();
            SmartPhone phone = new SmartPhone(0L, "Otro smartphone",
                    new RAM(1L, "DDR4", 8),
                    new Battery(1L, 4500.0),
                    new CPU(1L, 4),
                    false,
                    new Camera(1L, "front camera", 12.5));
            SmartPhone result = service.save(phone);
            assertNotNull(result);
            assertNotNull(result.getId());
            assertEquals(4, result.getId());
        }
        @DisplayName("Comprobar qué pasa con un id < 0")
        @Test
        void saveNegativeTest(){
            SmartPhoneServiceImpl service = new SmartPhoneServiceImpl();
            SmartPhone phone = new SmartPhone(-3L, "Otro smartphone",
                    new RAM(1L, "DDR4", 8),
                    new Battery(1L, 4500.0),
                    new CPU(1L, 4),
                    false,
                    new Camera(1L, "front camera", 12.5));
            SmartPhone result = service.save(phone);
            assertNotNull(result);
            assertNotNull(result.getId());
            assertEquals(-3, result.getId());
            // En mi opinión, este método no debería asignar el id=-3 ¿o sí?
            // Podríamos cambiar el código e incluir la excepción de los id<0,
            // al igual que tiene id=null || id=0
            assertEquals(4, service.count());
        }
        @DisplayName("Comprobar que se actualizan los smartphones")
        @Test
        void saveUpdateTest(){
            SmartPhoneServiceImpl service = new SmartPhoneServiceImpl();
            SmartPhone phone = new SmartPhone(1L, "One plus 9 editado",
                    new RAM(1L, "DDR4", 8),
                    new Battery(1L, 4500.0),
                    new CPU(1L, 4),
                    false,
                    new Camera(1L, "front camera", 12.5));
            // Comprobamos nº de smartphones
            assertEquals(3,service.count());
            SmartPhone result = service.save(phone);
            assertEquals(3, service.count());
            // Comprobamos el id
            assertEquals(1L,result.getId());
            // Comprobamos el nombre
            SmartPhone phone1 = service.findOne(1L);
            assertEquals("One plus 9 editado",phone1.getName());
        }
    }

    @DisplayName("Funcionalidad BORRAR sobre smartphones")
    @Nested
    class DeleteTest {
        @DisplayName("Borrar el smartphone de id=null")
        @Test
        void deleteNullTest(){
            SmartPhoneServiceImpl service = new SmartPhoneServiceImpl();
            boolean result = service.delete(null);
            assertFalse(result);
        }
        @DisplayName("Borrar el smartphone de id=1")
        @Test
        void deleteOKTest(){
            SmartPhoneServiceImpl service = new SmartPhoneServiceImpl();
            boolean result = service.delete(1L);
            assertTrue(result);
            assertTrue(service.count() > 0);
            service.delete(1L);
            assertEquals(2,service.count());
        }
        @DisplayName("Borrar el smartphone de id=999 ")
        @Test
        void deleteNotContainsTest(){
            SmartPhoneServiceImpl service = new SmartPhoneServiceImpl();
            boolean result = service.delete(999L);
            assertFalse(result);
        }
        @DisplayName("Borrar todos los smartphones")
        @Test
        void deleteAllTest(){
            assertTrue(service.count() > 0);
            service.deleteAll();
            assertEquals(0, service.count());
        }
    }

}
