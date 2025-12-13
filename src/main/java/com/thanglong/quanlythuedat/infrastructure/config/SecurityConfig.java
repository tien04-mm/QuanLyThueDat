package com.thanglong.quanlythuedat.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 1. Cho phép gửi Cookie/Credential (Quan trọng nếu sau này dùng Token/Session)
        config.setAllowCredentials(true);

        // 2. Chỉ định rõ các domain Frontend được phép truy cập
        // Lưu ý: Khi setAllowCredentials(true) thì KHÔNG ĐƯỢC dùng "*"
        config.setAllowedOrigins(List.of(
                "http://localhost:5173", // Vite (React)
                "http://localhost:3000"  // React thường (Dự phòng)
        ));

        // 3. Cho phép tất cả các Header (Authorization, Content-Type...)
        config.setAllowedHeaders(List.of("*"));

        // 4. Cho phép tất cả các Method (GET, POST, PUT, DELETE, OPTIONS)
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));

        // 5. Áp dụng cấu hình này cho mọi đường dẫn API
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}