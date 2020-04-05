package com.kt.smartfarm.service;

import com.kt.smartfarm.lamplog.LampLog;
import com.kt.smartfarm.lamplog.models.LOG_TYPE;

public interface LampLogService {
	LampLog createTransactionLog(String operation, LOG_TYPE logType, String userId, String destinationHost, String destinationIP);
	void sendResponseLog(LampLog llog);
	void sendLog(LampLog llog);
}
