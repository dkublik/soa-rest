package pl.dk.soa.service.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.not;
import static springfox.documentation.builders.PathSelectors.regex;


// http://localhost:8182/swagger-ui.html
@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    private Predicate<String> actuatorRegex() {
        return regex("application");
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("all-api")
                .enableUrlTemplating(false)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(not(regex("/error.*")))
                .paths(not(actuatorRegex()))
                .build();
    }
}
