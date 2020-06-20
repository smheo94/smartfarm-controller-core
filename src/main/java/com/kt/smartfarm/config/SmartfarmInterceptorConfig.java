package com.kt.smartfarm.config;

import com.kt.cmmn.SystemType;
import com.kt.smartfarm.intercepter.SmartFarmDataInterceptor;
import com.kt.smartfarm.mapper.GsmEnvMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

@Configuration
@Slf4j
@EnableWebMvc
@EnableScheduling
//@PropertySource(value={"classpath:application.properties","file:/myapp/application.properties","file:/home/gsm/v4/conf/smartfarm-mgr-env.properties"}, ignoreResourceNotFound=true)
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

    @Profile("dev")
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("Load Config : {}, {}, {}", GSM_KEY, SYSTEM_TYPE, DB_URL);
//        System.out.println("PASS */* = " + pass);
        registry.addInterceptor(new SmartFarmDataInterceptor(isSmartfarmSystem(), PROXY_SUB_PATH, GSM_KEY, gsmMapper)).addPathPatterns("/**");
//        registry.addInterceptor(new SmartFarmDataInterceptor(SYSTEM_TYPE, GSM_KEY, gsmMapper)).addPathPatterns("/**");
    }


}
