package com.volleyball.volleyballapi.api.utility;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PwUtils {
    public static String encryptPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 10000; // 迭代次數
        int keyLength = 256; // 鍵長度
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, iterations, keyLength);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = keyFactory.generateSecret(spec).getEncoded();

        return Base64.getEncoder().encodeToString(hash);
    }

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];  // 16 字節長的鹽值
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);  // 使用 Base64 編碼，使鹽值可儲存為字串
    }

    public static boolean verifyPassword(String inputPassword, String storedHash, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 用戶輸入的密碼與存儲的鹽值一起進行加密，生成哈希值
        String inputHash = encryptPassword(inputPassword, salt);

        // 比較生成的哈希值與存儲的哈希值
        return storedHash.equals(inputHash);  // 如果一致，返回 true，否則返回 false
    }

}
