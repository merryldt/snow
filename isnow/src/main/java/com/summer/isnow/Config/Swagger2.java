package com.summer.isnow.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2 {

  @Value("${swagger.show}")
  private boolean swaggerShow;

  @Bean
  public Docket createRestApi() {
    ParameterBuilder tokenBuilder = new ParameterBuilder();
    List<Parameter> parameterList = new ArrayList<Parameter>();
//    tokenBuilder.name("Authorization")
//            .defaultValue("Bearer") //加上从/api/user/APIToken获取到的token
//            .description("令牌")
//            .modelRef(new ModelRef("string"))
//            .parameterType("header")
//            .required(false).build();
//    parameterList.add(tokenBuilder.build());
    return new Docket(DocumentationType.SWAGGER_2).enable(swaggerShow).apiInfo(apiInfo()).select()
        .apis(RequestHandlerSelectors.basePackage("com.summer")).paths(PathSelectors.any())
        .build().globalOperationParameters(parameterList);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("summer apis").description("restful api")
        .termsOfServiceUrl("https://www.summere.com").version("1.0").build();
  }
}
