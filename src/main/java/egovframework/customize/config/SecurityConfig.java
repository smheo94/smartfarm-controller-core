package egovframework.customize.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@PropertySource(value={"classpath:application.properties","file:///myapp/application.properties"}, ignoreResourceNotFound = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;
	@Value("${security.oauth2.resource.token-info-uri}")
	private String tokenInfoUri;

	@Value("${smartfarm.farm.auth-basic-id}")
	private String authBasicId;

	@Value("${smartfarm.farm.auth-user-secret}")
	private String authUserSecret;

	@Autowired
	BasicAuthenticationPoint authEntryPoint;
	@Autowired
	RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off

		http.headers().frameOptions().disable().and()
			.requestMatcher(new BasicRequestMatcher())
			.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
			.antMatchers("/index.jsp", "/swagger-resources/**","/swagger-ui.html",
					"/v2/api-docs", "/webjars/**").permitAll() // Swagger Support
			.antMatchers("/otp/**", "/userInfo/resetPassword", "/userInfo/device").permitAll() // OTP 코드 생성, 검증, 비밀번호 변경
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
		auth.inMemoryAuthentication().withUser(authBasicId).password(authUserSecret).roles("USER");
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