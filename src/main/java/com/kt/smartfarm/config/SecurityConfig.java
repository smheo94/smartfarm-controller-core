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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
@Slf4j

@PropertySource(value={"classpath:application.properties","file:/myapp/application.properties","file:/home/gsm/v4/conf/smartfarm-mgr-env.properties"}, ignoreResourceNotFound=true)
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
	BasicAuthenticationPoint authEntryPoint;
	@Autowired
	RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		log.info("LoadSecurityConfig : {} \t\n", tokenInfoUri);
		http.headers().frameOptions().disable().and()
			.requestMatcher(new BasicRequestMatcher())
			.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
			.antMatchers("/swagger-ui/**",  "/v3/api-docs/**", "/index.jsp", "/swagger-resources/**","/swagger-ui.html",
					"/v2/api-docs", "/webjars/**").permitAll() // Swagger Support
			.antMatchers("/otp/**", "/userInfo/resetPassword", "/userInfo/device", "/**/openapi/**").permitAll() // OTP 코드 생성, 검증, 비밀번호 변경
			.anyRequest().authenticated()
			.and().httpBasic().authenticationEntryPoint(authEntryPoint)
			.and().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)

//			.and().exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
//			@Override
//			public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
//				httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
//ObjectMapper objectMapper = new ObjectMapper();
//				Map<String, Object> data = new HashMap<>();
//				data.put("timestamp", Calendar.getInstance().getTime());
//				data.put("reason","오류가 발생했습니다. 관리자에게 문의해 주세요1");
//				httpServletResponse.getOutputStream()
//						.println(objectMapper.writeValueAsString(data));
//			}
//		})
		;
		// @formatter:on
	}
	private static class BasicRequestMatcher implements RequestMatcher {
		@Override
		public boolean matches(HttpServletRequest request) {
			String auth = request.getHeader("Authorization");
			return (auth != null && auth.startsWith("Basic"));
		}
	}
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser(authBasicAPIId).password(authBasicAPISecret).roles("API-USER").
		and().withUser(authBasicUserId).password(authBasicUserSecret).roles("USER");
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
//
//	@Bean
//	public OpenAPI customOpenAPI() {
//
//		return new OpenAPI().components(new Components()
//				.addSecuritySchemes("basicScheme",
//						new SecurityScheme().type(SecurityScheme.Type.OAUTH2).scheme("oauth2"))).info(new Info().title("RPA Common API").version("100")).addTagsItem(new Tag().name("mytag"));
//	}
//
//	@Bean
//	public OpenAPI customOpenAPI(@Value("3.0") String appVersion) {
//		return new OpenAPI()
//				.components(new Components().addSecuritySchemes("basicScheme",
//						new SecurityScheme().type(SecurityScheme.Type.OAUTH2).scheme("oauth2")))
//				.info(new Info().title("SpringShop API").version(appVersion)
//						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
//	}



}