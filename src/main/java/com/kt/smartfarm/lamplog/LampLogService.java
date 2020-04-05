package com.kt.smartfarm.lamplog;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.smartfarm.lamplog.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Configuration
public class LampLogService
{
    private static AtomicLong logSeq = new AtomicLong();
    private String nextSeq() {
        return String.valueOf(logSeq.addAndGet(1));
    }
    private String curTimeStr() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }
    private String getCallerIP() {
        return request.getRemoteAddr();
    }
    private String remoteIP() {
        return request.getHeader("X-Real-IP");
    }
    private String getDockerHostName() {
        return  dockerHostName;
    }
    private String getDockerHostIp() {
        return dockerHostIp;
    }
    private String getServiceId() {
        return serviceId;
    }
    @Value("${smartfarm.lamp.service_id:SF_OAUTH2}")
    private String serviceId;
    @Value("${smartfarm.lamp.docker_host:oauth2}")
    private String dockerHostName;
    @Value("${smartfarm.lamp.docker_ip:0.0.0.0}")
    private String dockerHostIp;
    @Value("${smartfarm.lamp.logpath:/var/log/lamplog/}")
    private String lampLogPath;
    @Value("${smartfarm.system.typ:supervisor}")
    private String systemType;

    @Autowired(required = false)
    private HttpServletRequest request;
    @Autowired
    private ApplicationEventPublisher publisher;

    public LampLog createTransactionLog(String operation, LOG_TYPE logType, String userId, String destinationHost, String destinationIP) {
        return LampLog.builder()
                .realTimeStamp(System.currentTimeMillis())
                .timestamp(curTimeStr())
                .service(getServiceId())
                .operation(operation)
                .bizTransactionId(getServiceId() + UUID.randomUUID().toString())
                .logType(logType)
                .seq(nextSeq())
                .caller(LampCaller.builder().channelIp(getCallerIP()).build())
                .host(new LampHost(getDockerHostName(), getDockerHostIp()))
                .user(LampUser.builder().id(userId).ip(remoteIP()).build())
                .destination(new LampHost(destinationHost, destinationIP))
                .url(request.getRequestURI()).build();

    }

    //private static final Logger lampLogger = LoggerFactory.getLogger("LampLogger");
    private final ObjectMapper lampObjMapper =  new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public void sendResponseLog(LampLog llog) {
        if( Objects.equals("smartfarm", systemType) ) {
            return;
        }
        llog.setSeq(nextSeq());
        llog.setTimestamp(curTimeStr());
        publisher.publishEvent(llog);
    }
    public void sendLog(LampLog llog) {
        if( Objects.equals("smartfarm", systemType) ) {
            return;
        }
        publisher.publishEvent(llog);
    }
    @EventListener
    @Async
    public void receiveLog(LampLog llog) {
        Writer fileWrite =  null;
        try {
            String msg = lampObjMapper.writeValueAsString(llog);
            String logFile = lampLogPath + "/" + serviceId + "_"  + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".log";
            fileWrite = new FileWriter(logFile, true);
            fileWrite.write(msg);
            fileWrite.flush();
            //lampLogger.info(lampObjMapper.writeValueAsString(llog));
        } catch (IOException e) {
            log.warn("SendLog Fail : {}",llog,e);
        } finally {
            if( fileWrite != null) {
                try {
                    fileWrite.close();
                } catch (IOException e) {
                    log.warn("File Close Fail : {}",llog,e);
                }
            }
        }
    }
}

