package org.labs.lab1.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labs.lab1.entities.Address;
import org.labs.lab1.entities.Employee;
import org.labs.lab1.entities.Employees;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class XmlSerDesTest {
    private File file;
    private final Employee test_employee = new Employee("1", "Lokesh", "Gupta", "lokesh.gupta@gmail.com",
            new Address("India", "1-8-32/5, Bapu Bagh Cly, P G Road", "Hyderabad"));
    private final XmlSerDes<Employees> xmlSerDes = new XmlSerDes<>(Employees.class);

    @BeforeEach
    public void setUp() {
        ClassLoader classLoader = getClass().getClassLoader();
        file = new File(classLoader.getResource("test_data.xml").getFile());
    }

    @Test
    public void testDeserialize() throws IOException {
        Employees employees = xmlSerDes.deserialize(file);
        assertEquals(employees.getEmployees().get(0), test_employee);
    }

    @Test
    public void testSerialize() throws IOException {
        Employees employees = new Employees();
        List<Employee> list = new ArrayList<>();

        list.add(test_employee);
        employees.setEmployees(list);
        String filePath = "test_serialize.xml";
        xmlSerDes.serialize(filePath, employees);
        File file =  new File(filePath);
        assertNotNull(file);
        file.delete();
    }
}
