package org.labs.lab2;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import static org.labs.lab2.Cipherer.decryptData;
import static org.labs.lab2.Cipherer.encryptData;
import static org.labs.lab2.Signer.signData;
import static org.labs.lab2.Signer.verifySignedData;

public class Main {
    public static void main(String[] args) throws Exception {
        Security.setProperty("crypto.policy", "unlimited");
        int maxKeySize = javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
        System.out.println("Max Key Size for AES : " + maxKeySize + "\n");

        Security.addProvider(new BouncyCastleProvider());
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509", "BC");

        X509Certificate certificate = (X509Certificate) certificateFactory
                .generateCertificate(new FileInputStream("certs/public.cer"));
        String pswd = "password";
        char[] keystorePswd = pswd.toCharArray();
        char[] keyPswd = pswd.toCharArray();

        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(new FileInputStream("certs/private.p12"), keystorePswd);
        PrivateKey privateKey = (PrivateKey) keystore.getKey("baeldung", keyPswd);


        String secretMessage = "IMMA TELL YOU A SECRET! I LOVE JAVA";
        System.out.println("Original Message:\n" + secretMessage);
        byte[] stringToEncrypt = secretMessage.getBytes();
        byte[] encryptedData = encryptData(stringToEncrypt, certificate);
        System.out.println("Encrypted Message:");
        System.out.println(new String(encryptedData));
        byte[] rawData = decryptData(encryptedData, privateKey);
        String decryptedMessage = new String(rawData);
        System.out.println("Decrypted Message: \n" + decryptedMessage + "\n");

        byte[] signedData = signData(rawData, certificate, privateKey);
        Boolean check = verifySignedData(signedData);
        System.out.println(check ? "Success" : "Big Ouch");

    }
}
