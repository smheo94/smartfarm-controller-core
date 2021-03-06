package com.kt.smartfarm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Profile("dev")
@Configuration
@EnableSwagger2
//@PropertySource(value={"classpath:application.properties","file:/myapp/application.properties","file:/home/gsm/v4/conf/smartfarm-mgr-env.properties"}, ignoreResourceNotFound=true)
@SuppressWarnings("PMD")
public class SwaggerConfig {
    @Value("${security.oauth2.client.client-id}")
    private String clientId;
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;
    @Autowired
    Environment env;

    @Profile("dev")
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kt.smartfarm.web"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Arrays.asList(apiKey()))
                .securityContexts(securityContexts());
    }
    private ApiKey apiKey() {
        return new ApiKey("Authorization", "api-key", "header");
    }

    @Profile("dev")
    @Bean
    public SecurityConfiguration securityInfo() {
        SecurityConfiguration sc = new SecurityConfiguration(clientId, clientSecret, "spring_oauth", clientId, "", ApiKeyVehicle.HEADER, "api-key", " ");
        return sc;
    }

    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = {
                new AuthorizationScope("read", "for read operations")
                , new AuthorizationScope("write", "for write operations")
        };
        return scopes;
    }


    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        String[] paths = {"/env.*"};
        for (String path: paths) {
            securityContexts.add(SecurityContext.builder()
                    .securityReferences(Arrays.asList(new SecurityReference("Authorization", scopes())))
                    .forPaths(PathSelectors.regex(path))
                    .build());
        }

        return securityContexts;
    }
}


