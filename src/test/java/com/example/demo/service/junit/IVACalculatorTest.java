package com.example.demo.service.junit;

import com.example.demo.service.IVACalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IVACalculatorTest {

    IVACalculator calculator = new IVACalculator();

    @Test
    @DisplayName("Calcular IVA de entero positivo = 100")
    void calculateIVATest(){
        double result = calculator.calculateIVA(100);
        assertEquals(21,result);
    }

    @Test
    @DisplayName("Calcular IVA de entero negativo = -100")
    void calculateIVANegativeTest(){
        double result = calculator.calculateIVA(-100);
        assertEquals(-21,result);
    }

    @Test
    @DisplayName("Calcular IVA de 0")
    void calculateIVAZeroTest(){
        double result = calculator.calculateIVA(0);
        assertEquals(0,result);
    }

}
