package org.labs.lab1.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class StringCipherTest {
    final String keyString = "I/xx1qb4fBMOMcpkzJhhtQ==";
    final String incorrectKeyString = "I/xx1qb4fBMOMcpkzJttgQ==";
    final String origianlString = "TestString";
    final String expectedObfuscatedString = "N+Kclyl/HL/rHOD1vVfSgA==";
    private SecretKey secretKey;
    private SecretKey incorrectSecretKey;

    @BeforeEach
    public void setUp() {
        byte[] decodedKey = Base64.getDecoder().decode(keyString);
        secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        decodedKey = Base64.getDecoder().decode(incorrectKeyString);
        incorrectSecretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    @Test
    public void testObfuscateString() throws Exception {
        String obfuscatedString = StringCipher.obfuscateString(origianlString, secretKey);
        assertEquals(expectedObfuscatedString, obfuscatedString);
    }

    @Test
    public void testObfuscateStringWrongKey() throws Exception {
        String obfuscatedString = StringCipher.obfuscateString(origianlString, incorrectSecretKey);
        assertNotEquals(expectedObfuscatedString, obfuscatedString);
    }

    @Test
    public void testDeobfuscateString() throws Exception {
        String deobfuscatedString = StringCipher.deobfuscateString(expectedObfuscatedString, secretKey);
        assertEquals(origianlString, deobfuscatedString);
    }

    @Test
    public void testDeobfuscateStringWrongKey() throws Exception {
        assertThrows(BadPaddingException.class, () -> {
            StringCipher.deobfuscateString(expectedObfuscatedString, incorrectSecretKey);
        });
    }
}
