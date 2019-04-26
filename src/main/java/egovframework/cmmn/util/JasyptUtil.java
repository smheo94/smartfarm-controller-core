package egovframework.cmmn.util;

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
}
