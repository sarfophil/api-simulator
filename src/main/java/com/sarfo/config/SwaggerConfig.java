package com.sarfo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.sarfo"))
                .build().apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        return new ApiInfo("Sarfo API Simulator",
                "Sarfo API Simulator",
                "1.0", "Free to use",
                new springfox.documentation.service.Contact("Wind Team", "http://javabrains.io", "wind@mum.edu"),
                "API License", "http://sarfo.io", Collections.emptyList());

    }
}
