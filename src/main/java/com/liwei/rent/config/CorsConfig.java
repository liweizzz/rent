package com.liwei.rent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许的来源，可以是具体的域名或 "*" 表示允许所有来源
        config.addAllowedOriginPattern("*");
        // 允许的请求头，可以根据实际情况添加
        config.addAllowedHeader("*");
        // 允许的 HTTP 方法，如 GET、POST 等
        config.addAllowedMethod("*");
        // 是否允许发送 Cookies
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
