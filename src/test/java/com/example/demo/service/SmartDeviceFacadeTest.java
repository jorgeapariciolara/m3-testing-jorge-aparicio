package com.example.demo.service;

import com.example.demo.domain.SmartDevice;
import com.example.demo.domain.SmartPhone;
import com.example.demo.domain.SmartWatch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmartDeviceFacadeTest {

    @Test
    void createSmartPhone() {
        SmartDevice result = SmartDeviceFacade.createSmartPhone();

        assertNotNull(result);

        assertNotNull(result.getId());
        assertNotNull(result.getName());
        assertNotNull(result.getRam());
        assertNotNull(result.getBattery());
        assertNotNull(result.getCpu());
        assertTrue(result.getCpu().getOn());
        assertNotNull(result.getWifi());

        assertTrue(result instanceof SmartPhone);
        assertFalse(result instanceof SmartWatch);
    }

    @Test
    void createSmartWatch() {
        SmartDevice result = SmartDeviceFacade.createSmartWatch();

        assertNotNull(result.getId());
        assertNotNull(result.getName());
        assertNotNull(result.getRam());
        assertNotNull(result.getBattery());
        assertNotNull(result.getCpu());
        assertNotNull(result.getWifi());

        assertFalse(result instanceof SmartPhone);
        assertTrue(result instanceof SmartWatch);

    }
}
