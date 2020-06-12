package com.kt.smartfarm.config;

import org.apache.commons.io.FileUtils;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.spring31.properties.EncryptablePropertyPlaceholderConfigurer;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.util.Base64;

@Configuration
public class EncryptConfig {
    private static String seed1 = "";
    private static String seed2 = "";
    static  {
        getAlPw();
    }
//    @Bean
//    public static EnvironmentStringPBEConfig environmentVariablesConfiguration() {
//        EnvironmentStringPBEConfig environmentVariablesConfiguration = new EnvironmentStringPBEConfig();
//        environmentVariablesConfiguration.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
//        environmentVariablesConfiguration.setPassword(seed1);
//        return environmentVariablesConfiguration;
//    }

    public static MyEncryptor encryptor = new MyEncryptor(seed1);

    public static StringEncryptor configurationEncryptor() {
        return encryptor;
    }

    @Bean(name="propertyConfigurer")
    public static PropertyPlaceholderConfigurer propertyConfigurer() {
        EncryptablePropertyPlaceholderConfigurer propertyConfigurer = new EncryptablePropertyPlaceholderConfigurer(configurationEncryptor());
        propertyConfigurer.setIgnoreResourceNotFound(true);
        propertyConfigurer.setIgnoreUnresolvablePlaceholders(true);
        propertyConfigurer.setSystemPropertiesMode(2);
        propertyConfigurer.setLocations(
                new ClassPathResource("application.properties"),
                new FileSystemResource("/myapp/application.properties"),
                new FileSystemResource("/home/gsm/v4/conf/smartfarm-mgr-env.properties")
        );
        //propertyConfigurer.setLocation(new ClassPathResource("database.properties"));
        // propertyConfigurer.setLocation(resource);
        return propertyConfigurer;
    }

    private static void getAlPw( ) {
        try {
            String d = FileUtils.readLines(new File("/etc/.sf.k"), Charset.defaultCharset()).get(0);
            String a = FileUtils.readLines(new File("/home/gsm/.sf.k"), Charset.defaultCharset()).get(0);
            String z = FileUtils.readLines(new File("/usr/local/etc/.sf.k"), Charset.defaultCharset()).get(0);
            seed1 = z.substring(3,10) + a.substring(5, 9) + d;
        } catch (Exception e) {
            seed1 = "";
        }
    }

    public static class MyEncryptor implements  StringEncryptor {
        private String key;
        public MyEncryptor(String value) {
            this.key = value;
        }

        @Override
        public String encrypt(String msg) {
            try {
                SecureRandom random = new SecureRandom();
                byte bytes[] = new byte[20];
                random.nextBytes(bytes);
                byte[] saltBytes = bytes;

                SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), saltBytes, 70000, 128);
                SecretKey secretKey = factory.generateSecret(spec);
                SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secret);
                AlgorithmParameters params = cipher.getParameters();

                byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
                byte[] encryptedTextBytes = cipher.doFinal(msg.getBytes("UTF-8"));
                byte[] buffer = new byte[saltBytes.length + ivBytes.length + encryptedTextBytes.length];
                System.arraycopy(saltBytes, 0, buffer, 0, saltBytes.length);
                System.arraycopy(ivBytes, 0, buffer, saltBytes.length, ivBytes.length);
                System.arraycopy(encryptedTextBytes, 0, buffer, saltBytes.length + ivBytes.length, encryptedTextBytes.length);
                return Base64.getEncoder().encodeToString(buffer);
            } catch ( Exception e) {
                return null;
            }
        }

        @Override
        public String decrypt(String msg) {
            try {
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(msg));
                byte[] saltBytes = new byte[20];
                buffer.get(saltBytes, 0, saltBytes.length);
                byte[] ivBytes = new byte[cipher.getBlockSize()];
                buffer.get(ivBytes, 0, ivBytes.length);
                byte[] encryoptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes.length];
                buffer.get(encryoptedTextBytes);

                SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), saltBytes, 70000, 128);
                SecretKey secretKey = factory.generateSecret(spec);
                SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
                cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));
                byte[] decryptedTextBytes = cipher.doFinal(encryoptedTextBytes);
                return new String(decryptedTextBytes);
            } catch ( Exception e) {
                return null;
            }
        }
    }
}
