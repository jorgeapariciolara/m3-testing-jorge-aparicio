package com.example.demo.service;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

// Permite que se ejecuten todos los test del tirón, en vez de tener que ir clase a clase
@RunWith(JUnitPlatform.class)
@SelectPackages("com.example.demo")
public class SuiteTest {
}
