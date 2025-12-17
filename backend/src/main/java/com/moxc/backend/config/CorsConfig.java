package com.moxc.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局跨域配置
 * 在企业项目中通常使用这种方式统一管理跨域策略
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 配置所有接口都允许跨域
        registry.addMapping("/**")
                // 允许的源（前端地址）
                // 生产环境应配置具体的域名，不要使用 "*"
                .allowedOrigins(
                        "http://localhost:5173",  // Vue开发服务器默认端口
                        "http://localhost:5174",  // 其他可能的端口
                        "http://127.0.0.1:5173",  // IP地址
                        "http://127.0.0.1:5174"
                )
                // 允许的HTTP方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                // 允许的请求头
                .allowedHeaders("*")
                // 是否允许携带凭证（如Cookie）
                .allowCredentials(true)
                // 预检请求的缓存时间（秒），减少预检请求次数
                .maxAge(3600);
    }
}