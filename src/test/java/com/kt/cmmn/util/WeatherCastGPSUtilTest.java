package com.kt.cmmn.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.Assert;

import java.util.HashMap;

import static org.junit.Assert.*;


@Slf4j
public class WeatherCastGPSUtilTest {

    @Test
    public void getGPSWeater() {

        HashMap<String, Object> gridxy = WeatherCastGPSUtil.getGridxy(35.838090128339005, 128.50348443484742);
        HashMap<String, Object> gridxy2 = WeatherCastGPSUtil.getGridxy(36.5377868330829, 128.808170118549);
        HashMap<String, Object> gridxy3 = WeatherCastGPSUtil.getGridxy(37.49335435209985, 127.14485945099159);
        log.info("GPS Weather 1 : {}", gridxy);
        log.info("GPS Weather 2 : {}", gridxy2);
        log.info("GPS Weather 2 : {}", gridxy3);
        Assert.isTrue(true);
    }

}