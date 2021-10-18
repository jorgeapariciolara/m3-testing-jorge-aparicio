package com.example.demo.service;

import com.example.demo.domain.SmartPhone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SmartPhoneServiceImplTest {

    SmartPhoneServiceImpl service = new SmartPhoneServiceImpl();

    @BeforeEach
    void setUp () {
        service = new SmartPhoneServiceImpl();
    }

    @Test
    @DisplayName("Contar número de smartphones")
    void countTest(){
        Integer num = service.count();
        assertAll(
                () -> assertNotNull(num),
                () -> assertTrue(num>0),
                () -> assertEquals(3,num)
        );
    }

    @Test
    @DisplayName("Buscar todos los smartphones")
    void findAllTest() {
        List<SmartPhone> smartPhones = service.findAll();
        assertAll(
                () -> assertNotNull(smartPhones),
                () -> assertEquals(3,smartPhones.size())
        );
    }

    @Test
    @DisplayName("Buscar el smartphone de id=1")
    void findOnePhone1Test() {
        SmartPhone phone1 = service.findOne(1L);
        assertAll(
                () -> assertNotNull(phone1),
                () -> assertEquals(1l, phone1.getId())
        );
    }

    @Test
    @DisplayName("Buscar el smartphone de id=999")
    void findOnePhone999Test() {
        SmartPhone phone999 = service.findOne(999L);
        assertNull(phone999); // Pensamos que tiene que devolver null y lo comprobamos
    }

    @Test
    @DisplayName("Buscar el smartphone de id=null")
    void findOneExceptionTest(){
        // THROWS -> Verifica si se ha lanzado una excepción
        assertThrows(IllegalArgumentException.class,()->service.findOne(null));
    }

    @Test
    @DisplayName("Guardar smartphone nuevo")
    void saveNewPhoneTest(){

        // **********************************************************************

    }

    @Test
    @DisplayName("Guardar smartphone con id existente (id = 1) ==> Modificar = Actualizar")
    void saveOnePhone1Test(){
        SmartPhone phone = service.findOne(1L);
        service.save(phone);
        assertAll(
                () -> assertNotNull(service.save(phone)),
                () -> assertEquals(phone,service.save(phone))
        );
    }

    @Test
    @DisplayName("Asignar id a los smartphones")
    void getMaxSmartPhoneIdTest() {

        // ***********************************************************************

    }

    @Test
    @DisplayName("Borrar el smartphone de id=1 ")
    void deleteOnePhone1Test(){
        service.delete(1L);
        assertAll(
                () -> assertEquals(false,service.delete(1L))
        );
    }

    @Test
    @DisplayName("Borrar el smartphone de id=999 ")
    void deleteOnePhone999Test(){
        service.delete(999L);
        assertAll(
                () -> assertEquals(false,service.delete(999L))
        );
    }

    @Test
    @DisplayName("Borrar todos los smartphones")
    void deleteAllTest(){
        service.deleteAll();
        assertAll(
                () -> assertEquals(0, service.count())
        );
    }

    @Test
    @DisplayName("Buscar smartphones con wifi")
    void findByWifiTrueTest(){
        List<SmartPhone> smartPhones = service.findByWifi(true);
        assertAll(
                () -> assertNotNull(smartPhones),
                () -> assertEquals(2, smartPhones.size())
        );
    }

    @Test
    @DisplayName("Buscar smartphones sin wifi")
    void findByWifiFalseTest(){
        List<SmartPhone> smartPhones = service.findByWifi(false);
        assertAll(
                () -> assertNotNull(smartPhones),
                () -> assertEquals(1, smartPhones.size())
        );
    }
}
