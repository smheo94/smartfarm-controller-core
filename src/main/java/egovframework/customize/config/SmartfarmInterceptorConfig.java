package egovframework.customize.config;

import egovframework.customize.intercepter.SmartFarmDataInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@PropertySource("classpath:application.properties")
public class SmartfarmInterceptorConfig extends WebMvcConfigurerAdapter {


    @Value("${smartfarm.gsm-key}")
    public String GSM_KEY;
    @Value("${smartfarm.system.type}")
    public String SYSTEM_TYPE;

    @Override
    @DependsOn( {"propertyConfig"})
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.printf("Load Config : %s , %s\t\n", GSM_KEY, SYSTEM_TYPE);
        registry.addInterceptor(new SmartFarmDataInterceptor(SYSTEM_TYPE, GSM_KEY)).addPathPatterns("/**");

    }
}
