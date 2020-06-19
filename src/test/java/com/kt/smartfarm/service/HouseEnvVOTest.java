package com.kt.smartfarm.service;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class HouseEnvVOTest {

    @Test
    public void tojson() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("{ \"properties\" : \"{ \\\"a\\\" : 1}\" } ");
            HouseEnvVO ev = new ObjectMapper().readValue(sb.toString()
                    , HouseEnvVO.class);
            Assert.assertTrue(true);
        } catch (IOException e) {   e.printStackTrace();
            Assert.assertTrue(false);

        }


    }

}