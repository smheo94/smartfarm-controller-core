package egovframework.customize.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

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

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.headers().frameOptions().disable().and()
			.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
			.antMatchers("/index.jsp", "/swagger-resources/**","/swagger-ui.html",
					"/v2/api-docs", "/webjars/**").permitAll() // Swagger Support
			.antMatchers("/otp/**", "/userInfo/resetPassword", "/userInfo/device").permitAll() // OTP 코드 생성, 검증, 비밀번호 변경
			.anyRequest().authenticated()
			.and()
			.httpBasic();
		// @formatter:on
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