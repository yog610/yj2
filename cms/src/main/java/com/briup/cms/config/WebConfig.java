package com.briup.cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.briup.cms.web.interceptor.JwtInterceptor;
@Configuration
public class WebConfig implements WebMvcConfigurer{
	/*
	 * 注册拦截器，并指定拦截路径
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
		.addInterceptor(jwtInterceptor())
		.addPathPatterns("/auth/**");
	
	}
	//将自定义拦截添加到容器中
	@Bean
	public JwtInterceptor jwtInterceptor() {
		return new JwtInterceptor();
	}
	/*
	 * 使用cors解决跨域问题
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			        .allowedOrigins("*")
			        //如果是true, .allowedOrigins("*")不能是*  必须是具体的跨域路径，否则不安全。
			        .allowCredentials(false)
			        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
			        .allowedHeaders("*")
			        .maxAge(3600);
	}
}
