package com.example.userms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@SecurityScheme(
//        name = "Bearer Authentication",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        scheme = "bearer"
//)
@RequiredArgsConstructor
public class OpenApiConfig {

    private final ApplicationContext applicationContext;

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
//                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication") )
                .addServersItem(new Server().url("http://localhost:8082/api/users/")
                        .description("staging"))
                .info(createApiInfo());
    }

    private Info createApiInfo() {
        return new Info()
                .title(applicationContext.getApplicationName())
                .description("API documentation for " + applicationContext.getApplicationName())
                .version("2.0");
    }
}
