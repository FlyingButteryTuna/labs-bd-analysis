package org.labs.lab1.services;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;

public class StringCipher {
    public static String obfuscateString(String input, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String deobfuscateString(String input, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(input);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    @FunctionalInterface
    public interface CipherFunction {
        String apply(String t, SecretKey r) throws Exception;
    }

    public enum CipherModes {
        OBFUSCATE,
        DEOBFUSCATE
    }
}
