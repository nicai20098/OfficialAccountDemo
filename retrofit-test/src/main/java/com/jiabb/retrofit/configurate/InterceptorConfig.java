package com.jiabb.retrofit.configurate;

import com.jiabb.retrofit.interceptor.LogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jiabinbin
 * @date 2020/12/23 11:22 下午
 * @classname InterceptorConfig
 * @description 将log监听器放入spring中
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public LogInterceptor initTraceInterceptor() {
        return new LogInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(initTraceInterceptor()).addPathPatterns("/**");
    }

}
