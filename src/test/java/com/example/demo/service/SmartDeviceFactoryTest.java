package com.example.demo.service;

import com.example.demo.domain.SmartDevice;
import com.example.demo.domain.SmartPhone;
import com.example.demo.domain.SmartWatch;
import com.example.demo.domain.pieces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SmartDeviceFactoryTest {

    SmartPhoneServiceImpl smartPhoneService = new SmartPhoneServiceImpl();
    SmartWatchServiceImpl smartWatchService = new SmartWatchServiceImpl();
    @DisplayName("Crear un smartphone utilizando el tipo = PHONE")
    @Test
    void createByTypePhoneTest() {
        SmartPhone phone = new SmartPhone(null, "ONE SMARTPHONE",
                new RAM(1L, "DDR4", 8),
                new Battery(1L, 4500.0),
                new CPU(1L, 4),
                false,
                new Camera(1L, "front camera", 12.5));
        SmartDevice result = SmartDeviceFactory.createByType("phone");

        assertNotNull(result.getId());
        assertNotNull(result.getName());
        assertNotNull(result.getRam());
        assertNotNull(result.getBattery());
        assertNotNull(result.getCpu());
        assertTrue(result.getCpu().getOn());
        assertNotNull(result.getWifi());
        assertTrue(result instanceof SmartPhone);
    }
    @DisplayName("Crear un smartphone utilizando el tipo = WATCH")
    @Test
    void createByTypeWatchTest() {
        SmartWatch watch = new SmartWatch(null, "ONE SMARTWATCH",
                new RAM(1L, "DDR4", 2),
                new Battery(1L, 4500.0),
                new CPU(1L, 4),
                true,
                new HealthMonitor(1L, 0.0, 0));
        SmartDevice result = SmartDeviceFactory.createByType("watch");

        assertNotNull(result.getId());
        assertNotNull(result.getName());
        assertNotNull(result.getRam());
        assertNotNull(result.getBattery());
        assertNotNull(result.getCpu());
        assertNotNull(result.getWifi());
        assertTrue(result instanceof SmartWatch);
    }
    @DisplayName("Crear un smartphone utilizando OTRO TIPO")
    // ¡¡TIENE QUE SALTAR LA EXCEPCIÓN PORQUE ESTÁ PROGRAMADO ASÍ!!
    // java.lang.IllegalArgumentException: Unexpected value: SmartTV
    @Test
    void createByTypeUnexpectedValueTest() {
        String type = "SmartTV";
        SmartDevice result = SmartDeviceFactory.createByType(type);
        assertThrows(
                IllegalArgumentException.class,
                () -> SmartDeviceFactory.createByType(type)
        );
        assertNull(result.getId());
    }

    }
