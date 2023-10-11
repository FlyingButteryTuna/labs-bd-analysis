package org.labs.lab1.entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import org.labs.lab1.services.StringCipher;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "employees")
public class Employees {
    @JacksonXmlProperty(localName = "employee")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Employee> employees;

    public void obfuscate(String AesKey) {
        byte[] decodedKey = Base64.getDecoder().decode(AesKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        try {
            applyCipherFuncToFields(StringCipher::obfuscateString, originalKey);
        } catch (Exception e) {
            System.out.println("Couldn't obfuscate xml file. " + e.getMessage());
        }
    }

    public void deobfuscate(String AesKey) {
        byte[] decodedKey = Base64.getDecoder().decode(AesKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        try {
            applyCipherFuncToFields(StringCipher::deobfuscateString, originalKey);
        } catch (Exception e) {
            System.out.println("Couldn't deobfuscate xml file. " + e.getMessage());
        }
    }

    private void applyCipherFuncToFields(StringCipher.CipherFunction func, SecretKey originalKey) throws java.lang.Exception {
        for (Employee employee : employees) {
            employee.setFirstName(func.apply(employee.getFirstName(), originalKey));
            employee.setLastName(func.apply(employee.getLastName(), originalKey));
            employee.setEmail(func.apply(employee.getEmail(), originalKey));
            employee.setId(func.apply(employee.getId(), originalKey));

            Address currentAddress = employee.getAddress();
            Address newAddress = new Address();
            newAddress.setCity(func.apply(currentAddress.getCity(), originalKey));
            newAddress.setCountry(func.apply(currentAddress.getCountry(), originalKey));
            newAddress.setStreetName(func.apply(currentAddress.getStreetName(), originalKey));

            employee.setAddress(newAddress);
        }
    }
}
