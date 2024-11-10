package by.rustem;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class SimplePinEncryptionUtil{

    public static SecretKey generateSecretKey(){
        try{
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            return keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при генерации ключи для шифрования: " + e.getMessage(), e);
        }
    }

    public static String encryptPin(String pin, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedPin = cipher.doFinal(pin.getBytes());
            return Base64.getEncoder().encodeToString(encryptedPin);
        } catch (Exception e){
            throw new RuntimeException("Ошибка при шифровании пин-кода: " + e.getMessage(), e);
        }
    }

    public static String decryptPin(String encryptedPin, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedPin = Base64.getDecoder().decode(encryptedPin);
            byte[] decryptedPin = cipher.doFinal(decodedPin);
            return new String(decryptedPin);
        } catch (Exception e){
            throw new RuntimeException("Ошибка при дешифровании пин-кода: " + e.getMessage(), e);
        }
    }
}
