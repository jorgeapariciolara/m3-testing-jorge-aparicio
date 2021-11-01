package com.example.demo.service.junit;

import com.example.demo.domain.SmartPhone;
import com.example.demo.domain.pieces.Battery;
import com.example.demo.domain.pieces.CPU;
import com.example.demo.domain.pieces.Camera;
import com.example.demo.domain.pieces.RAM;
import com.example.demo.service.SmartPhoneServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

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
        @DisplayName("Buscar un smartphone")
        @Test
        void findOneOKTest() {
            SmartPhone smartPhone = service.findOne(1L);
            assertAll(
                    () -> assertNotNull(smartPhone),
                    () -> assertEquals(1L, smartPhone.getId()),
                    () -> assertEquals("One plus 9", smartPhone.getName()),
                    () -> assertEquals(1L, smartPhone.getRam().getId()),
                    () -> assertEquals("DDR4", smartPhone.getRam().getType()),
                    () -> assertEquals(8, smartPhone.getRam().getGigabytes()),
                    () -> assertEquals(1L, smartPhone.getBattery().getId()),
                    () -> assertEquals(4500.0, smartPhone.getBattery().getCapacity()),
                    () -> assertEquals(1L, smartPhone.getCpu().getId()),
                    () -> assertEquals(4, smartPhone.getCpu().getCores()),
                    () -> assertEquals(false, smartPhone.getWifi()),
                    () -> assertEquals(1L, smartPhone.getCamera().getId()),
                    () -> assertEquals("front camera", smartPhone.getCamera().getModel()),
                    () -> assertEquals(12.5, smartPhone.getCamera().getMegapixels())
            );
        }
        @DisplayName("Buscar un smartphone con id que no existe en la base de datos")
        @Test
        void findOneNotExistsTest() {
            SmartPhone smartPhone = service.findOne(999L);
            assertNull(smartPhone);
        }
        @DisplayName("Buscar un smartphone con id nulo")
        @Test
        void findOneExceptionTest() {
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
            assertEquals(4, service.count());
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
            assertEquals(4, result.getId());
            // En mi opinión, este método no debería asignar el id=-3 ¿o sí?
            // CAMBIO el código e incluyo la excepción de los id<0,
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
            assertEquals(3,service.count());
            SmartPhone result = service.save(phone);
            assertEquals(3, service.count());
            assertEquals(1L,result.getId());
            SmartPhone phone1 = service.findOne(1L);
            assertEquals("One plus 9 editado",phone1.getName());
        }
    }

    @DisplayName("Funcionalidad BORRAR sobre smartphones")
    @Nested
    class DeleteTest {
        @DisplayName("Borrar un smartphone con id nulo")
        @Test
        void deleteNullTest(){
            SmartPhoneServiceImpl service = new SmartPhoneServiceImpl();
            boolean result = service.delete(null);
            assertFalse(result);
        }
        @DisplayName("Borrar un smartphone")
        @Test
        void deleteOKTest(){
            SmartPhoneServiceImpl service = new SmartPhoneServiceImpl();
            boolean result = service.delete(1L);
            assertTrue(result);
            assertTrue(service.count() > 0);
            service.delete(1L);
            assertEquals(2,service.count());
        }
        @DisplayName("Borrar un smartphone con id que no existe en la base de datos")
        @Test
        void deleteNotExistsTest(){
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
