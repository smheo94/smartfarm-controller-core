package egovframework.customize.task.scheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import egovframework.cmmn.util.CommonUtil;
import egovframework.cmmn.util.DateUtil;
import egovframework.customize.service.EgovSampleService;

/*
 * 1시간 단위 동네예보 스케쥴러 
 * 노지채소 전용
 * DateUtil, HttpUtil
 * TODO : profile리스트 가지고 오는것. db에 넣는 것.
 */
@Service
public class EgovTaskScheduler {	

	public void runPidSchedule(){		
		System.out.println("Scheduler");
	}
	
}

