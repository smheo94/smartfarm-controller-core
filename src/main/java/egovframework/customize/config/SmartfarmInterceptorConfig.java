package egovframework.customize.config;

import com.kt.smartfarm.supervisor.mapper.GsmEnvMapper;
import egovframework.customize.intercepter.SmartFarmDataInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

@Configuration
//@PropertySource("classpath:smartfarm-mgr-env.properties")
//@PropertySource(value={"classpath:application.properties","classpath:smartfarm-mgr-env.properties"}, ignoreResourceNotFound=true)
@PropertySource(value={"classpath:application.properties","file:/myapp/application.properties","file:/home/gsm/v4/conf/smartfarm-mgr-env.properties"}, ignoreResourceNotFound=true)
//@PropertySource(value={"classpath:application.properties","classpath:encryption.properties","file:/myapp/application.properties","file:/home/gsm/v4/conf/smartfarm-mgr-env.properties"}, ignoreResourceNotFound=true)
@SuppressWarnings("PMD")
public class SmartfarmInterceptorConfig extends WebMvcConfigurerAdapter {

	
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
    @Override
    @DependsOn( {"propertyConfig"})
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.printf("Load Config : %s , %s, %s\t\n", GSM_KEY, SYSTEM_TYPE, DB_URL);
//        System.out.println("PASS */* = " + pass);
        registry.addInterceptor(new SmartFarmDataInterceptor(SYSTEM_TYPE, GSM_KEY, gsmMapper)).addPathPatterns("/**");
//        registry.addInterceptor(new SmartFarmDataInterceptor(SYSTEM_TYPE, GSM_KEY, gsmMapper)).addPathPatterns("/**");
    }


}
