package com.example.demo.service;

import com.example.demo.domain.SmartPhone;
import com.example.demo.domain.SmartWatch;
import com.example.demo.domain.pieces.*;
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
        @DisplayName("Comprobar que se asigna un id cuando el id = null")
        @Test
        void saveIdNullTest(){
            SmartWatch watch = new SmartWatch(null, "ONE SMARTWATCH",
                    new RAM(1L, "DDR4", 2),
                    new Battery(1L, 4500.0),
                    new CPU(1L, 4),
                    true,
                    new HealthMonitor(1L, 0.0, 0));
            SmartWatch result = service.save(watch);
            assertNotNull(result);
            assertNotNull(result.getId());
            assertEquals(4, result.getId());
        }
        @DisplayName("Comprobar que se asigna un id cuando el id = 0")
        @Test
        void saveIdZeroTest(){
            SmartWatch watch = new SmartWatch(0L, "ONE SMARTWATCH",
                    new RAM(1L, "DDR4", 2),
                    new Battery(1L, 4500.0),
                    new CPU(1L, 4),
                    true,
                    new HealthMonitor(1L, 0.0, 0));
            SmartWatch result = service.save(watch);
            assertNotNull(result);
            assertNotNull(result.getId());
            assertEquals(4, result.getId());
        }
        @DisplayName("Comprobar qué pasa con un id < 0")
        @Test
        void saveNegativeTest(){
            SmartWatch watch = new SmartWatch(-9L, "ONE SMARTWATCH",
                    new RAM(1L, "DDR4", 2),
                    new Battery(1L, 4500.0),
                    new CPU(1L, 4),
                    true,
                    new HealthMonitor(1L, 0.0, 0));
            SmartWatch result = service.save(watch);
            assertNotNull(result);
            assertNotNull(result.getId());
            assertEquals(-9, result.getId());
            // En mi opinión, este método no debería asignar el id=-9 ¿o sí?
            // Podríamos cambiar el código e incluir la excepción de los id<0,
            // if (smartwatch.getId() == null || smartwatch.getId() == 0L || smartwatch.getId() < 0)
            assertEquals(4, service.count());
        }
        @DisplayName("Comprobar que se actualizan los smartwatches")
        @Test
        void saveUpdateTest(){
            SmartWatchServiceImpl service = new SmartWatchServiceImpl();
            SmartWatch smartWatch = new SmartWatch(1L, "Fitbit sense EDITADO",
                    new RAM(1L, "DDR4", 2),
                    new Battery(1L, 4500.0),
                    new CPU(1L, 4),
                    true,
                    new HealthMonitor(1L, 0.0, 0));
            // Comprobamos nº de smartphones
            assertEquals(3,service.count());
            SmartWatch result = service.save(smartWatch);
            assertEquals(3, service.count());
            // Comprobamos el id
            assertEquals(1L,result.getId());
            // Comprobamos el nombre
            smartWatch = service.findOne(1L);
            assertEquals("Fitbit sense EDITADO",smartWatch.getName());
        }
    }

    @DisplayName("Funcionalidad BORRAR sobre smartwatches")
    @Nested
    class DeleteTest {
        @DisplayName("Borrar el smartwatch de id=null")
        @Test
        void deleteNullTest(){
            SmartWatchServiceImpl service = new SmartWatchServiceImpl();
            boolean result = service.delete(null);
            assertFalse(result);
        }
        @DisplayName("Borrar el smartwatch de id=1")
        @Test
        void deleteOKTest(){
            SmartWatchServiceImpl service = new SmartWatchServiceImpl();
            boolean result = service.delete(1L);
            assertTrue(result);
            assertTrue(service.count() > 0);
            service.delete(1L);
            assertEquals(2,service.count());
        }
        @DisplayName("Borrar el smartwatch de id=999 ")
        @Test
        void deleteNotContainsTest(){
            SmartWatchServiceImpl service = new SmartWatchServiceImpl();
            boolean result = service.delete(999L);
            assertFalse(result);
        }
        @DisplayName("Borrar todos los smartwatches")
        @Test
        void deleteAllTest(){
            assertTrue(service.count() > 0);
            service.deleteAll();
            assertEquals(0, service.count());
        }

    }

}
