package egovframework.customize.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import java.util.Objects;

@Configuration
@EnableResourceServer
@PropertySource(value={"classpath:application.properties","file:///myapp/application.properties"}, ignoreResourceNotFound = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Value("${smartfarm.system.type}")
	public String SYSTEM_TYPE;


	@Autowired
	private BasicAuthenticationPoint authEntryPoint;
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
	    resources.resourceId("resource_id");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		if(Objects.equals(SYSTEM_TYPE, EnvironmentConfig.SYSTEM_TYPE_SMARTFARM )) {
//			//Basic Auth를 사용
//			http.csrf().disable().authorizeRequests()
//					.antMatchers("/swagger-resources/**","/swagger-ui.html","/v2/api-docs", "/webjars/**", "/api/**").permitAll()
//					.antMatchers("/", "/api/public-key", "/error").permitAll()
//					.anyRequest().authenticated()
//					.and().httpBasic()
//					.authenticationEntryPoint(authEntryPoint);

		} else{
			// @formatter:off
			http.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()
					.antMatchers("/index.jsp", "/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**").permitAll() // Swagger Support
					.antMatchers("/otp/**", "/userInfo/resetPassword", "/userInfo/device").permitAll() // OTP 코드 생성, 검증, 비밀번호 변경
					.anyRequest().authenticated()
					.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			// @formatter:on
		}
	}
}