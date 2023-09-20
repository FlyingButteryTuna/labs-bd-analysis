package org.labs.lab1.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labs.lab1.services.XmlSerDes;

import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeesTest {
    final String keyString = "I/xx1qb4fBMOMcpkzJhhtQ==";
    final String incorrectKeyString = "I/xx1qb4fBMOMcpkzJttgQ==";

    File testDatafile;
    File testObfuscatedDatafile;

    private final XmlSerDes<Employees> xmlSerDes = new XmlSerDes<>(Employees.class);

    @BeforeEach
    public void setUp() {
        ClassLoader classLoader = getClass().getClassLoader();
        testDatafile = new File(classLoader.getResource("test_data.xml").getFile());
        testObfuscatedDatafile = new File(classLoader.getResource("test_obfuscated_data.xml").getFile());
    }

    @Test
    public void testObfuscateEmployees() throws IOException {
        Employees employees = xmlSerDes.deserialize(testDatafile);
        employees.obfuscate(keyString);
        Employees expectedEmployees = xmlSerDes.deserialize(testObfuscatedDatafile);

        assertEquals(expectedEmployees, employees);
    }

    @Test
    public void testDeobfuscateEmployees() throws IOException {
        Employees employees = xmlSerDes.deserialize(testObfuscatedDatafile);
        employees.deobfuscate(keyString);
        Employees expectedEmployees = xmlSerDes.deserialize(testDatafile);

        assertEquals(expectedEmployees, employees);
    }

    @Test
    public void testObfuscateEmployeesWrongKey() throws IOException {
        Employees employees = xmlSerDes.deserialize(testDatafile);
        employees.obfuscate(incorrectKeyString);
        Employees expectedEmployees = xmlSerDes.deserialize(testObfuscatedDatafile);

        assertNotEquals(expectedEmployees, employees);
    }

    @Test
    public void testDeobfuscateEmployeesWrongKey() throws IOException {
        Employees employees = xmlSerDes.deserialize(testObfuscatedDatafile);
        employees.deobfuscate(incorrectKeyString);
        Employees expectedEmployees = xmlSerDes.deserialize(testDatafile);

        assertNotEquals(expectedEmployees, employees);
    }
}
