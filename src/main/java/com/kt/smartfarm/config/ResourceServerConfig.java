package com.kt.smartfarm.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@Slf4j
//@PropertySource(value={"classpath:application.properties","file:/myapp/application.properties","file:/home/gsm/v4/conf/smartfarm-mgr-env.properties"}, ignoreResourceNotFound=true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Value("${smartfarm.system.type}")
	public String SYSTEM_TYPE;

	@Autowired
	private BasicAuthenticationPoint authEntryPoint;

	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
	    resources.resourceId("resource_id");
	}

	@Autowired
	Environment env;
	private String [] getMatchersList() {
		try {
			if (env != null && "dev".equals(env.getProperty("spring.profiles.active"))) {
				return new String[]{"/otp/**", "/userInfo/resetPassword", "/userInfo/device", "/**/openapi/**", "/system/ping/**",
						"/swagger-ui/**", "/v3/api-docs/**", "/index.jsp", "/swagger-resources/**", "/swagger-ui.html",
						"/v2/api-docs", "/webjars/**"};
			}
		} catch (Exception e) {
			log.warn("getMatchersList Error: {}", env, e);
		}
		return new String[] {"/otp/**", "/userInfo/resetPassword", "/userInfo/device", "/**/openapi/**", "/system/ping/**"};
	}
	@Override
	public void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()
					.antMatchers(getMatchersList()).permitAll() // Swagger Support
					.anyRequest().authenticated()
					.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and().httpBasic().authenticationEntryPoint(authEntryPoint)
					.and().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
					;
			// @formatter:on
//		}
	}
}