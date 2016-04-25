package fit.bez.app.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class RSADecryptor {

    public static final String ALGORITHM = "RSA";

    private String privateKeyFile;
    private PrivateKey privateKey;


    public RSADecryptor(String privateKeyFile) {
        this.privateKeyFile = privateKeyFile;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(privateKeyFile));
            String line, key = "";

            while((line = reader.readLine()) != null) {
                key += line + "\n";
            }

            key = key.replace("-----BEGIN PRIVATE KEY-----\n", "");
            key = key.replace("-----END PRIVATE KEY-----", "");

            byte[] encoded = Base64.decodeBase64(key);

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            privateKey = kf.generatePrivate(keySpec);

        } catch(IOException e) {
            e.printStackTrace();
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch(InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }


    public String decrypt(byte[] text) {
        byte[] dectyptedText = null;
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            dectyptedText = cipher.doFinal(text);

        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return new String(dectyptedText);
    }


}
