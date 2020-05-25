package com.kt.cmmn.util;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.junit.Assert.*;

public class RestClientUtilTest {

    @Test
    public void testRestClient() {
        //RestClientUtil client = new RestClientUtil(null, null, null);
        ResponseEntity<HashMap<String, Object>> result = RestClientUtil.post("https://192.168.10.252:33876/env/gsm", null, false);
        System.out.println(result.getStatusCode());
        assertTrue(result.getStatusCode() == HttpStatus.OK);
    }
}