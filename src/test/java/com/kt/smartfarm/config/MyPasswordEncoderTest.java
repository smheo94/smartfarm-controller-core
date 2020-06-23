package com.kt.smartfarm.config;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyPasswordEncoderTest {

    @Test
    public void encode() {
        MyPasswordEncoder encoder = new MyPasswordEncoder();
        System.out.println( encoder.encode("farm2019#!"));
        Assert.assertTrue(true);
    }
}