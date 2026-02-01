package com.thanglong.quanlythuedat.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") 
        // Cho phép tất cả các đường dẫn API
                
                // [QUAN TRỌNG] Điền cổng của Frontend vào đây
                // localhost:5173 (React/Vite mặc định)
                // localhost:3000 (React thường)
                .allowedOrigins("http://localhost:5173", "http://localhost:3000") 
                
                // Cho phép các phương thức
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                
                // Cho phép gửi kèm header (Token, Auth...)
                .allowedHeaders("*")
                
                // Cho phép gửi Cookie/Credential nếu cần
                .allowCredentials(true);
    }
}