package fit.bez.app.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class RSAEncryptor {

    public static final String ALGORITHM = "RSA";

    private String publicKeyFile;
    private PublicKey publicKey;


    public RSAEncryptor(String publicKeyFile) {
        this.publicKeyFile = publicKeyFile;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(publicKeyFile));
            String line, key = "";

            while((line = reader.readLine()) != null) {
                key += line + "\n";
            }

            key = key.replace("-----BEGIN PUBLIC KEY-----\n", "");
            key = key.replace("-----END PUBLIC KEY-----", "");

            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(key));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(pubKeySpec);

        } catch(IOException e) {
            e.printStackTrace();
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch(InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }


    public byte[] encrypt(String text) {
        byte[] cipherText = null;
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            cipherText = cipher.doFinal(text.getBytes());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }
}
