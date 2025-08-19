package com.example.instagram.auth.filter;

import com.example.instagram.auth.JwtTokenProvider;
import com.example.instagram.auth.config.AuthArgumentResolver;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class FilterConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    public FilterConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthArgumentResolver());
    }

    @Bean
    public FilterRegistrationBean<Filter> addJwtFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new JwtFilter(jwtTokenProvider));  // Filter 등록
        filterRegistrationBean.setOrder(1);                                 // Filter 순서 설정
        filterRegistrationBean.addUrlPatterns("/*");                        // 전체 URL에 Filter 적용

        return filterRegistrationBean;
    }
}