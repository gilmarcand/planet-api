package io.planet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = { "io.planet" , "co.swapi"})
@EnableCaching
public class SwPlanetsApplicationRunner{

    public static void main(String[] args) {
        new SpringApplication(SwPlanetsApplicationRunner.class).run(args);
    }

}
