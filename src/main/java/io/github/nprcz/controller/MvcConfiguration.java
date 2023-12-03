package io.github.nprcz.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    private Set<HandlerInterceptor> interceptors;

    public MvcConfiguration(final Set<HandlerInterceptor> interceptors) {
        this.interceptors = interceptors;
    }
    //wsrzykiwanie wszystkich interceptors ze wszystkich klas automatycznie
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    interceptors.forEach(registry::addInterceptor);}
}
