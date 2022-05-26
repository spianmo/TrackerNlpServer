package com.tracker.nlp.config;

import com.tracker.nlp.filter.CrosXssFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.DispatcherType;

/**
 * Copyright (c) 2022
 *
 * @Project: CollectionBackend
 * @Author: Finger
 * @FileName: LocalFilterConfig.java
 * @LastModified: 2022/01/15 18:07:15
 */
@Configuration
@RequiredArgsConstructor
public class FilterConfig {
    /**
     * 跨域过滤器
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", config);
        CorsFilter corsFilter = new CorsFilter(corsConfigurationSource);
        registration.setOrder(Integer.MAX_VALUE - 2);
        registration.setFilter(corsFilter);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<CrosXssFilter> httpTraceLogFilterRegistration() {
        FilterRegistrationBean<CrosXssFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new CrosXssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("CrosXssFilter");
        registration.setOrder(Integer.MAX_VALUE - 1);
        return registration;
    }
}
