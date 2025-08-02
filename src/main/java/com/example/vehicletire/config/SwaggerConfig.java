package com.example.vehicletire.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Veículos e Pneus")
                        .description("API para gerenciamento de veículos e pneus")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Nome da Equipe")
                                .email("contato@exemplo.com")));
    }
}