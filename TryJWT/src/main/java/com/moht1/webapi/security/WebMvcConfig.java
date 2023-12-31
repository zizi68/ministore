package com.moht1.webapi.security;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofBytes(100000000L));
        factory.setMaxRequestSize(DataSize.ofBytes(100000000L));
        return factory.createMultipartConfig();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/images/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // registry.addInterceptor(new AirJ18Interceptor())
        // .addPathPatterns("/api/booking/**");
    }
} 
