package egovframework.customize.config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan("egovframework.customize.config")
@SuppressWarnings("PMD")
@EnableWebMvc
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    @Bean
    public Docket api() {
        System.out.println("------------------------------------------------------swaggerConfig");
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("egovframework.customize.web"))
                .paths(PathSelectors.any())
                .build();

    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }
}

