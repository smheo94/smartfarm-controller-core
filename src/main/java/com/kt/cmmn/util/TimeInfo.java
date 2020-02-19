package com.kt.cmmn.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

public class TimeInfo {
    private final Calendar now;
    private final Calendar sunrize;
    private final Calendar sunset;
    private final LocalDateTime sunrizeLDT;
    private final LocalDateTime sunsetLDT;
    public TimeInfo(Calendar now, Calendar sunrize, Calendar sunset) {
        this.now = now;
        this.sunrize = sunrize;
        this.sunset = sunset;
        this.sunrizeLDT = toLocalDateTime(sunrize);
        this.sunsetLDT = toLocalDateTime(sunset);
    }
    public static LocalDateTime toLocalDateTime(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        TimeZone tz = calendar.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(), zid);
    }
    public boolean isDay(LocalDateTime dateTime) {
        return dateTime.isAfter(this.sunrizeLDT) && dateTime.isBefore(this.sunsetLDT);
    }
    public Calendar getNow() {
        return now;
    }

    public Calendar getSunrize() {
        return sunrize;
    }

    public Calendar getSunset() {
        return sunset;
    }

    public String getNowTimeString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return format.format(now.getTime());
        //return DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss").withZone(TimeZone.getDefault().toZoneId()). ..print(now.getTimeInMillis());
    }

    public String getSunrizeTimeString() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(sunrize.getTime());
        //return DateTimeFormat.forPattern("HH:mm").print(sunrize.getTimeInMillis());
    }

    public String getSunsetTimeString() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(sunset.getTime());
        //return DateTimeFormat.forPattern("HH:mm").print(sunset.getTimeInMillis());
    }

}
