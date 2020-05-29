package com.kt.cmmn.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.jasypt.util.text.TextEncryptor;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;


public class JasyptUtilTest {

    @Test
    public void encryptTest() {
        //given
        //MyEncryptor encryptor = new MyEncryptor();
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword("SMARTFARM-PASS");
        //encryptor.setPassword("erichwang123");
        String orgText ="sf_dev";
        //when
        String encText = encryptor.encrypt(orgText);
        System.out.println(encText);
        String decText = encryptor.decrypt(encText);
        //then
        assertTrue(orgText.equals(decText));
    }
    @Test
    public void encyptSha256Test() {
        String orgText = "farm2019";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(orgText);
        System.out.println(encode);

        //then
        assertTrue(encoder.matches(orgText, encode));

    }

    @Test
    public void recoveryTest() {
        //given
        //MyEncryptor encryptor = new MyEncryptor();
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword("SMARTFARM-PASS");
        //encryptor.setPassword("erichwang123");
        String encText ="QzAe7prqIG82wiq/CSA5fMpBkdfUi6Mb";
        //when
        String encText2 = "35Q2XrBhLTUfpO/KIf3ATGB4IPgqDXre";
        String decText = encryptor.decrypt(encText);
        String decText2 = encryptor.decrypt(encText2);
        System.out.println(decText);
        //then
        assertTrue( decText2.equals(decText));

    }




    public static class MyEncryptor implements TextEncryptor {
            private final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

            public MyEncryptor() {
                this.encryptor.setAlgorithm("PBEWITHMD5ANDTRIPLEDES");
            }

            public void setPassword(String password) {
                this.encryptor.setPassword(password);
            }

            public void setPasswordCharArray(char[] password) {
                this.encryptor.setPasswordCharArray(password);
            }

            public String encrypt(String message) {
                return this.encryptor.encrypt(message);
            }

            public String decrypt(String encryptedMessage) {
                return this.encryptor.decrypt(encryptedMessage);
            }
    }
}