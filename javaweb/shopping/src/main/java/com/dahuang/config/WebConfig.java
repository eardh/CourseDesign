package com.dahuang.config;

import com.dahuang.config.interceptor.AuthInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 核心配置类
 * @author dahuang
 * @date 2021/6/9 14:59
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthInterceptor authInterceptor;

    /**
     * 配置自定义拦截器
     * @author dahuang
     * @date 2021/6/11 8:53
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("拦截器配置");
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/login",
                        "/checkout",
                        "/check_login",
                        "/register",
                        "/logout",
                        "/file/images/**",
                        "/product/*"
                );
    }

    /**
     * 静态资源映射配置
     * @author dahuang
     * @date 2021/6/11 16:12
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.info("访问文件资源路径配置");
        registry.addResourceHandler("/file/images/**")
                .addResourceLocations("classpath:/FileService/images/");
    }

    /**
     * 跨域问题解决
     * 1、采用 addCorsMappings(CorsRegistry registry)，有拦截器失效
     * 2、采用下面配置 CorsFilter 来实现，--最佳方式
     * @author dahuang
     * @date 2021/6/11 9:47
     * @return org.springframework.web.filter.CorsFilter
     */
    @Bean
    public CorsFilter corsFilter() {
        logger.info("跨域请求配置");
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:63342");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(corsConfigurationSource);
    }

}
