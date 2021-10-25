package com.example.demo.service.junit;

import com.example.demo.domain.SmartDevice;
import com.example.demo.domain.SmartPhone;
import com.example.demo.domain.SmartWatch;
import com.example.demo.domain.pieces.*;
import com.example.demo.service.SmartDeviceFactory;
import com.example.demo.service.SmartPhoneServiceImpl;
import com.example.demo.service.SmartWatchServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SmartDeviceFactoryTest {

    @DisplayName("Crear un smartphone utilizando el tipo = PHONE")
    @Test
    void createByTypePhoneTest() {
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
