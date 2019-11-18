package com.kt.smartfarm.service;

import com.kt.cmmn.util.ClassUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Map;


/**
 * 
 * 
 * @author LEE
 *
 */

@Data
@Getter
@Setter
public class ControlLogicSettingTemplateVO {
    public enum PublishLevel {
        OPEN, INTERNAL, OWNER
    }
    public enum RelativeLevel {
        COMMON, LOGIC
    }
    public String title;
	public Long controlSettingTemplateId;
	public RelativeLevel relativeLevel;
	public PublishLevel publishLevel;
	public String prdCode;
	public String growStage;
	public String templateDescription;

	public Integer greenHouseId;
	public String userId;
	public Integer logicId;
	public String defLogicName;
	public String javaClassName;
	public String logicName;
	public String logicEnv;
	public String logicPeriodEnv;
	public Integer logicPeriodSize;
	public String autoManualMode;
	public Date createDt;
	public Date updateDt;
	public Integer viewOrder;
	public Map<String,Object> toMap() {
		return ClassUtil.toHashMap(this);
	}
}
