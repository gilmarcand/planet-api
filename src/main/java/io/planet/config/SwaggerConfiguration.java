package io.planet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Controller
@Configuration
public class SwaggerConfiguration {

    @Value("${SwaggerConfiguration.title:}")
    private String title;
    @Value("${SwaggerConfiguration.description:}")
    private String description;
    @Value("${SwaggerConfiguration.name:}")
    private String name;
    @Value("${SwaggerConfiguration.url:}")
    private String url;
    @Value("${SwaggerConfiguration.email:}")
    private String email;

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.planet.api"))
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title(title)
                        .description(description)
                        .contact(new Contact(name,url, email))
                        .build());
    }

    @RequestMapping(value = "/")
    public String index() {
        return "redirect:swagger-ui.html";
    }

}
