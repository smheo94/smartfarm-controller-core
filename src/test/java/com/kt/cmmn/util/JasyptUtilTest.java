package com.kt.cmmn.util;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class JasyptUtilTest {

    @Test
    public void encrypt() {
        //given
        String originText = "shddjq01##";
        String encPass = "SMARTFARM-PASS";

        //when
        String encText = JasyptUtil.encrypt(encPass, originText);
        String recoverText = JasyptUtil.decrypt(encPass, encText);
        System.out.printf( "%s - %s\n", originText, encText);
        //then
        assertTrue(Objects.equals(recoverText , originText));
    }
}