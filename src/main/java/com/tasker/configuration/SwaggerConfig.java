package com.tasker.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by firthouse on 6/11/17.
 */
@Configuration
@EnableSwagger2
//@ComponentScan(basePackages = "com.mangofactory.swagger")
public class SwaggerConfig {
/*
    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                //This info will be used in Swagger. See realisation of ApiInfo for more details.
                .apiInfo(new ApiInfo(
                        "Trustcology API",
                        "This app is by JEDIS",
                        null,
                        null,
                        null,
                        null
                ))
                //Here we disable auto generating of responses for REST-endpoints
                .useDefaultResponseMessages(false)
                //Here we specify URI patterns which will be included in Swagger docs. Use regex for this purpose.
                .includePatterns("/landlords.*");
    }
*/

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()                 .apis(RequestHandlerSelectors.basePackage("com.trustco.controller"))
                .paths(regex("/balloon.*"))
                .build();

    }

    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Spring Boot REST API",
                "Spring Boot REST API for Online Funding",
                "1.0",
                "Terms of service",
                new springfox.documentation.service.Contact("Jedis Team", "Web Url", "--"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
        return apiInfo;
    }
}
