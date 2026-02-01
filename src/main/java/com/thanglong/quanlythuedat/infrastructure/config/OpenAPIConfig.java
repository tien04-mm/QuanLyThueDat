package com.thanglong.quanlythuedat.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API HỆ THỐNG QUẢN LÝ THUẾ ĐẤT ĐAI")
                        .version("1.0")
                        .description("Tài liệu API cho Frontend và Mobile App")
                )
                .servers(List.of(
                        new Server().url("http://localhost:9090").description("Server Local")
                ));
    }
}