package com.kt.smartfarm.service;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

@Data
public class HouseProductVO {
    public Long id;
    public Long gsmKey;
    public Long greenHouseId;
    public String prdCode;
    public String startDate;
    public String endDate;
//    public Double plantedArea;
//    public Double areaHorizontal;
//    public Double areaVertical;
//    public Double plantSpacing;
//    public Double perTree;
    public Long createDt;
    public Long updateDt;
    public Long deleteDt;
    public Integer isDeleted;
    HashMap<String,Object> properties = new HashMap<>();

}