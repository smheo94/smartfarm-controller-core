package com.kt.cmmn.util;


import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Slf4j
public class SunriseSunset {
    public static final SunriseSunset SUNRIZESUNSET = new SunriseSunset(37.511530, 127.043137);

    private final Location location;
    private final SunriseSunsetCalculator calculator;

    public SunriseSunset(double latitude, double longitude) {
        this.location = new Location(latitude, longitude);
        this.calculator = new SunriseSunsetCalculator(location, TimeZone.getDefault());
    }

    public Calendar getTodaySunrise() {
        return calculator.getOfficialSunriseCalendarForDate(Calendar.getInstance());
    }

    public Calendar getTodaySunset() {
        return calculator.getOfficialSunsetCalendarForDate(Calendar.getInstance());
    }

    public Calendar getSunrise(Calendar cal) {
        return calculator.getOfficialSunriseCalendarForDate(cal);
    }

    public Calendar getSunset(Calendar cal) {
        return calculator.getOfficialSunsetCalendarForDate(cal);
    }

    public TimeInfo getCurrentTimeInfo() {
        Calendar cal = Calendar.getInstance();
        return new TimeInfo(Calendar.getInstance(), getSunrise(cal), getSunset(cal));
    }

    public Map<String,Object> getSunriseSunSetMap() {
        String sunrise = getCurrentTimeInfo().getSunrizeTimeString().replaceAll(":", "");
        String sunset = getCurrentTimeInfo().getSunsetTimeString().replaceAll(":", "");
        Map<String,Object> result = new HashMap<>();
        result.put("loc_date", DateUtil.getDateHourOnly().getTime());
        result.put("sunrise", sunrise);
        result.put("sunset", sunset);
        return result;
    }
}
