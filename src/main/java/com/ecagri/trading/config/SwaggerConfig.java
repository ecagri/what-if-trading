package com.ecagri.trading.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("what if trading")
                        .version("1.0")
                        .description("This is the documentation for REST APIs for a trading app.")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Çağrı Çaycı")
                                .url("https://github.com/ecagri"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))  // Add security requirement
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT") // Specify that it’s a JWT token
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")));  // Token is passed in the Authorization header
    }
}
