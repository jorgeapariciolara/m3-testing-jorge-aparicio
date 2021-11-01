package com.example.demo.service.junit;

import com.example.demo.domain.SmartDevice;
import com.example.demo.domain.SmartPhone;
import com.example.demo.domain.SmartWatch;
import com.example.demo.service.SmartDeviceFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmartDeviceFacadeTest {

    @DisplayName("Crear un smartphone")
    @Test
    void createSmartPhone() {
        SmartDevice result = SmartDeviceFacade.createSmartPhone();
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals("Default smartphone", result.getName()),
                () -> assertEquals(1L, result.getRam().getId()),
                () -> assertEquals("DDR4", result.getRam().getType()),
                () -> assertEquals(8, result.getRam().getGigabytes()),
                () -> assertEquals(1L, result.getBattery().getId()),
                () -> assertEquals(0.0, result.getBattery().getCapacity()),
                () -> assertEquals(1L, result.getCpu().getId()),
                () -> assertEquals(4, result.getCpu().getCores()),
                () -> assertTrue(result.getCpu().getOn()),
                () -> assertEquals(true, result.getWifi())
        );

        SmartPhone phone = (SmartPhone) result;
        assertAll(
                () -> assertNotNull(phone.getCamera()),
                () -> assertEquals(1L,phone.getCamera().getId()),
                () -> assertEquals("front camera",phone.getCamera().getModel()),
                () -> assertEquals(12.5,phone.getCamera().getMegapixels())
        );

        assertTrue(result instanceof SmartPhone);
        assertFalse(result instanceof SmartWatch);
    }

    @DisplayName("Crear un smartwatch")
    @Test
    void createSmartWatch() {
        SmartDevice result = SmartDeviceFacade.createSmartWatch();

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals("Default smartwatch", result.getName()),
                () -> assertEquals(1L, result.getRam().getId()),
                () -> assertEquals("DDR4", result.getRam().getType()),
                () -> assertEquals(8, result.getRam().getGigabytes()),
                () -> assertEquals(1L, result.getBattery().getId()),
                () -> assertEquals(0.0, result.getBattery().getCapacity()),
                () -> assertEquals(1L, result.getCpu().getId()),
                () -> assertEquals(4, result.getCpu().getCores()),
                () -> assertEquals(true, result.getWifi())
        );

        SmartWatch watch = (SmartWatch) result;
        assertAll(
                () -> assertNotNull(watch.getMonitor()),
                () -> assertEquals(1L, watch.getMonitor().getId()),
                () -> assertEquals(0.0, watch.getMonitor().getBloodPressure()),
                () -> assertEquals(0, watch.getMonitor().getSleepQuality())
        );

        assertFalse(result instanceof SmartPhone);
        assertTrue(result instanceof SmartWatch);
    }
}
