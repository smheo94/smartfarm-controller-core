package com.kt.smartfarm.config;

import com.kt.smartfarm.service.GsmEnvService;
import com.kt.smartfarm.supervisor.mapper.GsmEnvMapper;
import com.kt.cmmn.SystemType;
import com.kt.smartfarm.intercepter.SmartFarmDataInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

@Configuration
@Slf4j
//@PropertySource("classpath:smartfarm-mgr-env.properties")
//@PropertySource(value={"classpath:application.properties","classpath:smartfarm-mgr-env.properties"}, ignoreResourceNotFound=true)
@PropertySource(value={"classpath:application.properties","file:/myapp/application.properties","file:/home/gsm/v4/conf/smartfarm-mgr-env.properties"}, ignoreResourceNotFound=true)
//@PropertySource(value={"classpath:application.properties","classpath:encryption.properties","file:/myapp/application.properties","file:/home/gsm/v4/conf/smartfarm-mgr-env.properties"}, ignoreResourceNotFound=true)
@SuppressWarnings("PMD")
public class SmartfarmInterceptorConfig extends WebMvcConfigurerAdapter {

    @Value("${smartfarm.supervisor.env.api-url:https://apismartfarm.kt.co.kr/env/}")
    public String ENV_API;
    @Value("${smartfarm.gsm-key}")
    public String GSM_KEY;
    @Value("${smartfarm.system.type}")
    public String SYSTEM_TYPE;
    @Value("${smartfarm.supervisor.subpath}")
    public String PROXY_SUB_PATH;
    @Value("${smartfarm.db.url}")
    private String DB_URL;
//    @Value("${variable.secret.var}")
//    private String pass;
    @Resource(name="gsmEnvMapper")
    GsmEnvMapper gsmMapper;

    public Boolean isSmartfarmSystem() {
        return SystemType.SYSTEM_TYPE_SMARTFARM.equalsIgnoreCase(SYSTEM_TYPE);
    }

    @Override
    @DependsOn( {"propertyConfig"})
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("Load Config : {}, {}, {}", GSM_KEY, SYSTEM_TYPE, DB_URL);
//        System.out.println("PASS */* = " + pass);
        registry.addInterceptor(new SmartFarmDataInterceptor(isSmartfarmSystem(), PROXY_SUB_PATH, GSM_KEY, gsmMapper)).addPathPatterns("/**");
//        registry.addInterceptor(new SmartFarmDataInterceptor(SYSTEM_TYPE, GSM_KEY, gsmMapper)).addPathPatterns("/**");
    }


}
