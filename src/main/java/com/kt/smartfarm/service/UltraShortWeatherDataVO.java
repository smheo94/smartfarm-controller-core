package com.kt.smartfarm.service;

import com.kt.cmmn.util.DateUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;

@Slf4j
@Data
public class UltraShortWeatherDataVO {
    public String baseDateTime;
    public Date baseDate;
    public String baseTime;
    public String nx;
    public String ny;
    public Double pty;
    public Double reh;
    public Double rn1;
    public Double t1h;
    public Double uuu;
    public Double vec;
    public Double vvv;
    public Double wsd;
    private void setBaseDateTime() {
        try {
            if (baseTime != null && baseDate != null) {
                baseDateTime = DateUtil.getDateFormatedString(baseDate.toString() + " " + baseTime,"yyyy-MM-dd HHmm","yyyy-MM-dd HH:mm:ss" );
                //baseDateTime = DateUtil2.toString(DateUtil2.parseLocalDateTime(baseDate.toString() + " " + baseTime, "yyyy-MM-dd HHmm"), "yyyy-MM-dd HH:mm:ss");
            }
        } catch (Exception e) {
            log.info("set LocalDateTime Error :{}",  e);
        }
    }
    public void setBaseDate(Date value) {
        this.baseDate = value;
        setBaseDateTime();
    }
    public void setBaseTime(String value) {
        this.baseTime = value;
        setBaseDateTime();
    }
}