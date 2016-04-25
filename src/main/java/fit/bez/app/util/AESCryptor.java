package fit.bez.app.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class AESCryptor {

    private String IV, keyText, mode;
    private Cipher cipher;
    private SecretKeySpec key;

    public AESCryptor(String IV, String keyText, String mode) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
        this.IV = IV;
        this.keyText = keyText;
        this.mode = mode;

        cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING", "SunJCE");
        key = new SecretKeySpec(keyText.getBytes("UTF-8"), "AES");

    }

    public byte[] encrypt(String text) throws UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return cipher.doFinal(text.getBytes("UTF-8"));
    }


    public String decrypt(byte[] cipherText) throws UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException {
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(cipher.doFinal(cipherText), "UTF-8");
    }


}
