package com.kt.cmmn.util;

import com.kt.smartfarm.config.EncryptConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jasypt.registry.AlgorithmRegistry;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.assertTrue;


@Slf4j
public class JasyptUtilTest {

    @Test
    public void getAllAlgorithms() {
        log.info("allDigestAlgorithms : {}", AlgorithmRegistry.getAllDigestAlgorithms());
        log.info("allPBEAlgorithms : {}", AlgorithmRegistry.getAllPBEAlgorithms());
    }


    @Test
    public void encryptEncryptConfig() {
        String originText [] = { "kt-local-user", "f730660e5cd8e98c1d2621192eb44e32", "sf_dev", "2019shddjq2019", "shddjq01##","farm2019#!","05dc8fbd5e01a6625e8a6eb1ddf482c9", "kt-gsm-controller", "kt-smartfarm", "kt-gsm-fp", "kt-gsm-fp-secret", "http://dev1705.vivans.net:47900/oauth/check_token" , "51724690439c3e4e89a9efc5e2ca567d5e4b09e6fa69a06bd65e7597418737cc"
        ,"echo smartfarm2@ | sudo sh /home/vivans/mini_vms_pkg/script/updateGit.sh"
                ,"echo smartfarm2@ | sudo sh /home/vivans/mini_vms_pkg/script/update.sh"
                ,"echo smartfarm2@ | sudo sh /home/vivans/mini_vms_pkg/script/restart.sh"
                ,"echo smartfarm2@ | sudo sh /home/vivans/mini_vms_pkg/script/ffmpeg_rtsp_record.sh %s %s %s"
                ,"echo smartfarm2@ | sudo sh /home/vivans/mini_vms_pkg/script/startWebRTC.sh"
                ,"echo smartfarm2@ | sudo sh /home/vivans/mini_vms_pkg/script/stopWebRTC.sh"
                ,"echo smartfarm2@ | sudo sh /home/vivans/mini_vms_pkg/script/checkWebRTCProcess.sh"
                ,"ag"
                ,"smartfarm2@"
                , "a123456-"
                , "admin"
                , "rtsp://admin:a123456-@192.168.2.101/media/video1"

        };
        Arrays.asList(originText).stream().forEachOrdered(s -> {
            String seedText = "64cf3d525a2512ea71ee7ad6b4a178af25ad2c57";
            EncryptConfig.MyEncryptor myEncryptor = new EncryptConfig.MyEncryptor(seedText);
            String encryptText = myEncryptor.encrypt(s);
            log.info("'" + s + "' = '" + encryptText +"'");
            String revText = myEncryptor.decrypt(encryptText);
            assertTrue(revText.equals(s));
        });
    }

    @Test
    public void writeEncFile() throws IOException {
        String seedText = "64cf3d525a2512ea71ee7ad6b4a178af25ad2c57";
        FileUtils.write(new File("/etc/.sf.k"), "512ea71ee7ad6b4a178af25ad2c57", Charset.defaultCharset());
        FileUtils.write(new File("/home/gsm/.sf.k"), "sd5e625a2512ea71e", Charset.defaultCharset());
        FileUtils.write(new File("/usr/local/etc/.sf.k"), "56864cf3d525a2512ea7", Charset.defaultCharset());

        String d = FileUtils.readLines(new File("/etc/.sf.k"), Charset.defaultCharset()).get(0);
        String a = FileUtils.readLines(new File("/home/gsm/.sf.k"), Charset.defaultCharset()).get(0);
        String z = FileUtils.readLines(new File("/usr/local/etc/.sf.k"), Charset.defaultCharset()).get(0);
        String readText = z.substring(3,10) + a.substring(5, 9) + d;
        log.info("{}", seedText);
        log.info("{}", readText);

        assertTrue(seedText.equals(readText));
    }




    @Test
    public void encryptAESTest2() {
        //given
        Set allPBEAlgorithms = AlgorithmRegistry.getAllPBEAlgorithms();

        allPBEAlgorithms.stream().forEachOrdered(
                s ->{
                    try {
                        String originText = "sf_dev";
                        String algolizm = s.toString();
                        String seedText = "64cf3d525a2512ea71ee7ad6b4a178af25ad2c57";
                        String encryptText = JasyptUtil.encrypt(algolizm, seedText, originText);
                        System.out.print(s + "/ " + originText + " = " + encryptText);
                        String revText = JasyptUtil.decrypt(algolizm, seedText, encryptText);
                        System.out.println(" : Supported");
                    } catch (Exception e) {
                        System.out.println(" Not Supported");
                    }
                }
        );


        assertTrue(true);
    }

    @Test
    public void encryptAESTest() {
        //given
        String originText = "sf_dev";
        String algolizm = "PBEWITHHMACSHA256ANDAES_256";
        String seedText = "64cf3d525a2512ea71ee7ad6b4a178af25ad2c57";
        String encryptText = JasyptUtil.encrypt(algolizm, seedText, originText);
        System.out.println(originText + " = " + encryptText);
        String revText = JasyptUtil.decrypt(algolizm,seedText, encryptText );
        assertTrue(revText.equals(originText));
    }

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




//    public static class MyEncryptor implements TextEncryptor {
//            private final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
//
//            public MyEncryptor() {
//                this.encryptor.setAlgorithm("PBEWITHMD5ANDTRIPLEDES");
//            }
//
//            public void setPassword(String password) {
//                this.encryptor.setPassword(password);
//            }
//
//            public void setPasswordCharArray(char[] password) {
//                this.encryptor.setPasswordCharArray(password);
//            }
//
//            public String encrypt(String message) {
//                return this.encryptor.encrypt(message);
//            }
//
//            public String decrypt(String encryptedMessage) {
//                return this.encryptor.decrypt(encryptedMessage);
//            }
//    }
}