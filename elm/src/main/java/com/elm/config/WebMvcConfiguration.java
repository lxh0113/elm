package com.elm.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class WebMvcConfiguration {

    @Bean
    public Docket docket()
    {
        ApiInfo apiInfo= new ApiInfoBuilder()
                .title("饿了么")
                .version("2.0")
                .description("接口文档")
                .build();

        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.elm.controller"))
                .paths(PathSelectors.any())
                .build();

        return docket;

    }
}
