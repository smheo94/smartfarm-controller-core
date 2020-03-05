package com.kt.smartfarm.service;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Data
public class HouseProductVO {
    public Long id;
    public Long gsmKey;
    public Long greenHouseId;
    public String prdCode;
    public String startDate;
    public String endDate;
}