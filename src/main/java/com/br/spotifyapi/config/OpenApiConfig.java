package com.br.spotifyapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI spotifyApiOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Spotify API")
                        .description("API REST para gerenciamento de artistas e álbuns integrada à Spotify Web API")
                        .version("1.0.0"));
    }
}