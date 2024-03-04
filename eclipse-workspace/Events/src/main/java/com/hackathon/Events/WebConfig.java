package com.hackathon.Events;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@EnableJpaRepositories(basePackages = "com.hackathon.Events.repositories")
@EntityScan(basePackages = "com.hackathon.Events.models")
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/images/**",
                "/css/**")
                .addResourceLocations(
                        "classpath:/templates/images/",
                        "classpath:/templates/css/");
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) { 
        registry.addInterceptor(new CustomInterceptor()); 
    } 

}
