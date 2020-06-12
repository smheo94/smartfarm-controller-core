package com.kt.cmmn.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptUtil {

    public static String encrypt(String text) {
        String algorithm = "PBEWithMD5AndDES";
        //String password = "SMARTFARM-PASS";
        String password = null;
        return encrypt(algorithm, password, text);
    }

    public static String encrypt(String password, String text) {
        String algorithm = "PBEWithMD5AndDES";
        return encrypt(algorithm, password, text);
    }

    public static String encrypt(String algorithm, String password, String text) {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm(algorithm);
        pbeEnc.setPassword(password);
        return pbeEnc.encrypt(text);
    }

    public static String decrypt(String password, String encText) {
        return decrypt("PBEWithMD5AndDES", password, encText);
    }
    public static String decrypt(String algorithm, String password, String encText) {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm(algorithm);
        pbeEnc.setPassword(password);
        return pbeEnc.decrypt(encText);
    }
}
