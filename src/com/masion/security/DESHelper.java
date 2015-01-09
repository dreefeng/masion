package com.masion.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESHelper {
    private static String keyFileName = "des_key.xml"; // 密钥文件名
    private static final String ALGORITHM = "DES"; // 加密算法名
    private static final SecureRandom sr = new SecureRandom(); // 强加密随机数生成器

    // 测试方法
    public static void main(String[] args) {
        // 加解密字符串
        byte[] encryptedObj = encryptStr("心有灵犀一点通", "astronomer");
        System.out.println(new String(encryptedObj));
        byte[] decryptedObj = decrypt(encryptedObj, "astronomer");
        System.out.println(new String(decryptedObj));

        // 加解密文件
        encryptFile("plain.txt", "plain_encrypt.txt", "astronomer");
        decryptFile("plain_encrypt.txt", "plain_encrypt_decrypt.txt", "astronomer");

        // 使用保存的key加解密字符串
//        byte[] encryptedObj = encryptStr("春天花会开");
//        System.out.println(new String(encryptedObj));
//        byte[] decryptedObj = decrypt(encryptedObj);
//        System.out.println(new String(decryptedObj));
    }

    // 保存生成的密钥
    public static void storeSecretKey() {
        FileOutputStream out = null;
        ObjectOutputStream oout = null;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(sr);
            SecretKey secretKey = keyGenerator.generateKey();
            out = new FileOutputStream(keyFileName);
            oout = new ObjectOutputStream(out);
            oout.writeObject(secretKey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oout.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 获取保存的密钥
    public static SecretKey loadSecretKey() {
        SecretKey secretKey = null;
        try {
            FileInputStream in = new FileInputStream(keyFileName);
            ObjectInputStream oin = new ObjectInputStream(in);
            secretKey = (SecretKey)oin.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return secretKey;
    }

    // 生成密钥。由于每次产生的密钥都不同，不适合实际使用。
    private static SecretKey generateSecretKey() {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(ALGORITHM); // Get the key generator
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return keyGenerator.generateKey(); // Generate the secret key
    }

    // 根据密码的消息摘要和DESKeySpec生成密钥。
    private static SecretKey generateSecretKey(String key) {
        SecretKey secretKey = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = key.getBytes();
            md.update(bytes, 0, bytes.length);
            byte[] mdBytes = md.digest(); // Generate 16 bytes
            byte[] truncatedBytes = Arrays.copyOf(mdBytes, 8); // Fetch 8 bytes for DESKeySpec
            DESKeySpec keySpec = new DESKeySpec(truncatedBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            secretKey = keyFactory.generateSecret(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return secretKey;
    }

    // 根据密码和keyGenerator生成密钥。
    private static SecretKey generateSecretKey2(String key) {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(ALGORITHM); // Get the key generator
            SecureRandom sr = new SecureRandom(key.getBytes()); // Instantiate an instance of SecureRandom
            keyGenerator.init(sr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyGenerator.generateKey();
    }

    // 根据密码加密文件。
    public static void encryptFile(String file, String newFile, String key) {
        SecretKey secretKey = generateSecretKey(key);
        encryptFile(file, newFile, secretKey);
    }

    public static void encryptFile(String file, String newFile) {
        File keyFile = new File(keyFileName);
        if (!keyFile.exists()) {
            storeSecretKey();
        }
        SecretKey secretKey = loadSecretKey();
        encryptFile(file, newFile, secretKey);
    }

    private static void encryptFile(String file, String newFile, SecretKey secretKey) {
        InputStream in = null;
        CipherInputStream cin = null;
        OutputStream out = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
            in = new FileInputStream(file);
            cin = new CipherInputStream(in, cipher);
            out = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = cin.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                cin.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 根据密码解密文件。
    public static void decryptFile(String file, String newFile, String key) {
        SecretKey secretKey = generateSecretKey(key);
        decryptFile(file, newFile, secretKey);
    }

    public static void decryptFile(String file, String newFile) {
        File keyFile = new File(keyFileName);
        if (!keyFile.exists()) {
            storeSecretKey();
        }
        SecretKey secretKey = loadSecretKey();
        decryptFile(file, newFile, secretKey);
    }

    private static void decryptFile(String file, String newFile, SecretKey secretKey) {
        InputStream in = null;
        CipherOutputStream cout = null;
        OutputStream out = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
            in = new FileInputStream(file);
            out = new FileOutputStream(newFile);
            cout = new CipherOutputStream(out, cipher);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = in.read(buffer)) > 0) {
                cout.write(buffer, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                cout.close();
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 加密字符串。
    public static byte[] encryptStr(String str) {
        byte[] bytes = str.getBytes();
        File keyFile = new File(keyFileName);
        if (!keyFile.exists()) {
            storeSecretKey();
        }
        SecretKey secretKey = loadSecretKey();
        return encrypt(bytes, secretKey);
    }

    // 根据密码加密字符串。
    public static byte[] encryptStr(String str, String key) {
        byte[] bytes = str.getBytes();
        SecretKey secretKey = generateSecretKey(key);
        return encrypt(bytes, secretKey);
    }

    // 根据密码加密字节数组。
    public static byte[] encrypt(byte[] bytes, String key) {
        SecretKey secretKey = generateSecretKey(key);
        return encrypt(bytes, secretKey);
    }

    // 加密字节数组。
    public static byte[] encrypt(byte[] bytes) {
        File keyFile = new File(keyFileName);
        if (!keyFile.exists()) {
            storeSecretKey();
        }
        SecretKey secretKey = loadSecretKey();
        return encrypt(bytes, secretKey);
    }

    private static byte[] encrypt(byte[] bytes, SecretKey secretKey) {
        byte[] encryptedObj = null;

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM); // Get the cipher
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptedObj = cipher.doFinal(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encryptedObj;
    }

    // 解密字符串。
    public static byte[] decryptStr(String str) {
        byte[] bytes = str.getBytes();
        File keyFile = new File(keyFileName);
        if (!keyFile.exists()) {
            storeSecretKey();
        }
        SecretKey secretKey = loadSecretKey();
        return decrypt(bytes, secretKey);
    }

    // 根据密码解密字符串。
    public static byte[] decryptStr(String str, String key) {
        byte[] bytes = str.getBytes();
        SecretKey secretKey = generateSecretKey(key);
        return decrypt(bytes, secretKey);
    }

    // 根据密码解密字节数组。
    public static byte[] decrypt(byte[] bytes, String key) {
        SecretKey secretKey = generateSecretKey(key);
        return decrypt(bytes, secretKey);
    }

    // 解密字节数组。
    public static byte[] decrypt(byte[] bytes) {
        File keyFile = new File(keyFileName);
        if (!keyFile.exists()) {
            storeSecretKey();
        }
        SecretKey secretKey = loadSecretKey();
        return decrypt(bytes, secretKey);
    }

    private static byte[] decrypt(byte[] bytes, SecretKey secretKey) {
        byte[] decryptedObj = null;

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM); // Get the cipher
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decryptedObj = cipher.doFinal(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decryptedObj;
    }
}