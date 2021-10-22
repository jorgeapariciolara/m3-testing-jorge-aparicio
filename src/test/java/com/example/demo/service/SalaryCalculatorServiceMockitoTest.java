package com.example.demo.service;

import com.example.demo.domain.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SalaryCalculatorServiceMockitoTest {

    IRPFCalculator irpfCalculator;
    IVACalculator ivaCalculator;

    SalaryCalculatorService service;

    @BeforeEach
    void setUp(){
        irpfCalculator = mock(IRPFCalculator.class);
        ivaCalculator = mock(IVACalculator.class);
        service = new SalaryCalculatorService(irpfCalculator,ivaCalculator);
    }

    @Test
    void name(){

        when(irpfCalculator.calculateIRPF(anyDouble())).thenReturn(4950d);
        when(ivaCalculator.calculateIVA(anyDouble())).thenReturn(7969.5);

        Employee employee = new Employee(1L,"Nombre1",30);
        double result = service.calculateSalary(employee);

        InOrder order = inOrder(irpfCalculator,ivaCalculator);

        assertNotNull(result);
        assertEquals(45919.50,result);
        verify(irpfCalculator).calculateIRPF(anyDouble());
        verify(ivaCalculator).calculateIVA(anyDouble());

        order.verify(irpfCalculator).calculateIRPF (anyDouble());
        order.verify(ivaCalculator).calculateIVA(anyDouble());
    }
}

