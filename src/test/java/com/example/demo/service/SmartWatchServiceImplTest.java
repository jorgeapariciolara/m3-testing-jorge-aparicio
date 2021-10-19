package com.example.demo.service;

import com.example.demo.domain.SmartWatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SmartWatchServiceImplTest {

    SmartWatchServiceImpl service = new SmartWatchServiceImpl();

    @BeforeEach
    void setUp(){
        service = new SmartWatchServiceImpl();
    }

    @DisplayName("Funcionalidad BUSCAR sobre smartwatches")
    @Nested
    class RetrieveTest {
        @DisplayName("Contar número de smartwatches")
        @Test
        void countTest(){
            Integer num = service.count();
            assertAll(
                    () -> assertNotNull(num),
                    () -> assertTrue(num>0),
                    () -> assertEquals(3,num)
            );
        }
        @DisplayName("Buscar todos los smartwatches")
        @Test
        void findAllTest(){
            List<SmartWatch> smartWatches = service.findAll();
            assertAll(
                    () -> assertNotNull(smartWatches),
                    () -> assertEquals(3,smartWatches.size())
            );
        }
        @DisplayName("Buscar el smartwatch de id=1")
        @Test
        void findOneWatch1Test(){
            SmartWatch watch1 = service.findOne(1L);
            assertAll(
                    () -> assertNotNull(watch1),
                    () -> assertEquals(1l, watch1.getId())
            );
        }
        @DisplayName("Buscar el smartwatch de id=999")
        @Test
        void findOneWatch999Test(){
            SmartWatch watch999 = service.findOne(999L);
            assertNull(watch999);
        }
        /*
        @DisplayName("Buscar el smartwatch de id=null")
        @Test
        void findOneExceptionTest() {
            // THROWS -> Verifica si se ha lanzado una excepción
            assertThrows(
                    IllegalArgumentException.class,
                    () -> service.findOne(null)
            );
        }
        */
    }

    @DisplayName("Funcionalidad CREAR y MODIFICAR sobre smartwatches")
    @Nested
    class SaveTest {

    }

}
