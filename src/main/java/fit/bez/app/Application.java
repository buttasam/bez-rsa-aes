package fit.bez.app;

import fit.bez.app.util.AESCryptor;
import fit.bez.app.util.FileManager;
import fit.bez.app.util.RSADecryptor;
import fit.bez.app.util.RSAEncryptor;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * created by Samuel Butta (buttasam@fit.cvut.cz)
 * BI-BEZ
 */

public class Application {

    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IOException, NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {

        Application application = new Application();
        application.encrypt();

        application.decrypt();

    }

    public void encrypt() throws NoSuchProviderException, NoSuchAlgorithmException, NoSuchPaddingException, IOException, IllegalBlockSizeException, InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException {
        String initVector = "AAAAAAAAAAAAAAAA";
        String keyText = "0123456789abcdef";
        String mode = "CBC";

        RSAEncryptor rsaEncryptor = new RSAEncryptor("pubkey.pem");
        AESCryptor aesCryptor = new AESCryptor(initVector, keyText, mode);
        FileManager manager = new FileManager();

        String plainData = manager.readTextFile("data_to_encrypt.txt");

        String mataDataToEncrypt = initVector + "\n" + keyText + "\n" + mode + "\n";

        byte[] encryptMetaData = rsaEncryptor.encrypt(mataDataToEncrypt);
        byte[] encryptData = aesCryptor.encrypt(plainData);

        manager.writeEncryptData(encryptData, "encrypted_data.bin");
        manager.writeEncryptData(encryptMetaData, "encrypted_meta_data.bin");

        System.out.println("--- Data zašifrována ---");
    }


    public void decrypt() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, IllegalBlockSizeException, InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException {
        FileManager manager = new FileManager();
        RSADecryptor rsaDecryptor = new RSADecryptor("privkey.pem");

        byte[] encryptMetaData = manager.readEncryptData("encrypted_meta_data.bin");
        byte[] enctyptData = manager.readEncryptData("encrypted_data.bin");

        String metaData = rsaDecryptor.decrypt(encryptMetaData);

        String[] metaDataSplit = metaData.split("\n");

        String initVector = metaDataSplit[0];
        String keyText = metaDataSplit[1];
        String mode = metaDataSplit[2];

        AESCryptor aesCryptor = new AESCryptor(initVector, keyText, mode);


        String decryptData = aesCryptor.decrypt(enctyptData);
        manager.writeTextFile("decrypted_data.txt", decryptData);

        System.out.println("--- Data dešifrována ---");
    }
}
