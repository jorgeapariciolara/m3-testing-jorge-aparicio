package com.example.demo.service.junit;

import com.example.demo.service.IRPFCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IRPFCalculatorTest {

    IRPFCalculator calculator = new IRPFCalculator();

    @Test
    @DisplayName("Calcular IRPF de entero positivo = 100")
    void calculateIRPFTest(){
        double result = calculator.calculateIRPF(100);
        assertEquals(15,result);
    }

    @Test
    @DisplayName("Calcular IRPF de entero negativo = -100")
    void calculateIRPFNegativeTest(){
        double result = calculator.calculateIRPF(-100);
        assertEquals(-15,result);
    }

    @Test
    @DisplayName("Calcular IRPF de 0")
    void calculateIRPFZeroTest(){
        double result = calculator.calculateIRPF(0);
        assertEquals(0,result);
    }
}
