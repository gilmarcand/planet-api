package io.planet.config;

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

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Star Wars Planets API")
            .description("REST API to manage planets, extending from [Star Wars public API](https://swapi.co/)")
            .version("1.0.0")
            .contact(new Contact("Gilmar Candido","", "gilmarcand@gmail.com"))
            .build();
    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.planet.api"))
                .build()
                .apiInfo(apiInfo());
    }

    @RequestMapping(value = "/")
    public String index() {
        return "redirect:config-ui.html";
    }

}
