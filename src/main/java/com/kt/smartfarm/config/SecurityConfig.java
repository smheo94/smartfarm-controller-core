package com.kt.smartfarm.config;

//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.info.License;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import io.swagger.v3.oas.models.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Configuration
@EnableWebSecurity
//@PropertySource(value={"classpath:application.properties","file:/myapp/application.properties","file:/home/gsm/v4/conf/smartfarm-mgr-env.properties"}, ignoreResourceNotFound=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${security.oauth2.client.client-id}")
	public String clientId;
	@Value("${security.oauth2.client.client-secret}")
	public String clientSecret;
	@Value("${security.oauth2.resource.token-info-uri}")
	public String tokenInfoUri;

	@Value("${smartfarm.farm.auth-basic-id}")
	private String authBasicAPIId;

	@Value("${smartfarm.farm.auth-user-secret}")
	private String authBasicAPISecret;

	@Value("${smartfarm.farm.auth-basic-userid}")
	private String authBasicUserId;

	@Value("${smartfarm.farm.auth-basic-usersecret}")
	private String authBasicUserSecret;


	@Autowired
	Environment env;
	@Autowired
	BasicAuthenticationPoint authEntryPoint;
	@Autowired
	RestAuthenticationEntryPoint restAuthenticationEntryPoint;
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
		// @formatter:off
		log.info("LoadSecurityConfig : {} \t\n", tokenInfoUri);
        http.headers().frameOptions().disable().and()
                .requestMatcher(new BasicRequestMatcher())
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(getMatchersList()).permitAll()
                .anyRequest().authenticated()
                .and().requestCache().requestCache(new NullRequestCache())
                .and().httpBasic().authenticationEntryPoint(authEntryPoint)
                .and().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);

		// @formatter:on
	}
	private static class BasicRequestMatcher implements RequestMatcher {
		@Override
		public boolean matches(HttpServletRequest request) {
			String auth = request.getHeader("Authorization");
			return (auth != null && auth.startsWith("Basic"));
		}
	}
	@Bean
	public PasswordEncoder getPasswordEncoder(){
		return new MyPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser(authBasicAPIId).password(authBasicAPISecret).roles("API-USER").and().passwordEncoder(getPasswordEncoder());
		auth.inMemoryAuthentication().withUser(authBasicUserId).password(authBasicUserSecret).roles("USER").and().passwordEncoder(getPasswordEncoder());
	}
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
	    authenticationManager.setTokenServices(tokenService());
	    return authenticationManager;
	}
	
	@Bean
	public RemoteTokenServices tokenService() {
	    RemoteTokenServices tokenService = new RemoteTokenServices();
	    tokenService.setCheckTokenEndpointUrl(tokenInfoUri);
	    tokenService.setClientId(clientId);
	    tokenService.setClientSecret(clientSecret);
	    return tokenService;
	}
}