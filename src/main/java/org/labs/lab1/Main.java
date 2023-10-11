package org.labs.lab1;

import org.labs.lab1.entities.Employees;
import org.labs.lab1.services.StringCipher;
import org.labs.lab1.services.XmlSerDes;
import java.io.File;

public class Main {
    private static final String xmlFilePath = "data.xml";

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException("2 parameters required");
        }

        StringCipher.CipherModes mode;
        switch (args[0]) {
            case "obfs" -> mode = StringCipher.CipherModes.OBFUSCATE;
            case "deobfs" -> mode = StringCipher.CipherModes.DEOBFUSCATE;
            default -> throw new IllegalArgumentException("The first parameter must be either 'obfs' for " +
                        "obfuscation or 'deobfs' for deobfuscation.");
        }

        File xmlFile = new File(xmlFilePath);
        XmlSerDes<Employees> xmlSerDes = new XmlSerDes<>(Employees.class);
        Employees employees = xmlSerDes.deserialize(xmlFile);

        String key = args[1];
        switch (mode) {
            case OBFUSCATE -> employees.obfuscate(key);
            case DEOBFUSCATE -> employees.deobfuscate(key);
        }

        xmlSerDes.serialize(xmlFilePath, employees);
    }
}