package com.kt.smartfarm.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushHistoryVO {
    public Long id;
    public Integer smsPush;
    public Date pushDate;
    public Long greenHouseId;
    public Integer pushType;
    public String pushTitle;
    public String pushMessage;
    public String  pushInterval;
    public Long deviceId;
    public Integer cctvId;
    public Integer requestCount;
    public String historyKey;
    public String errorMessage;
    public String imageUrl;
    public String weather;
}
