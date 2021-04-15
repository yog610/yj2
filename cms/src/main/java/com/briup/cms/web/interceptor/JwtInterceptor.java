package com.briup.cms.web.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.briup.cms.util.JwtUtil;

public class JwtInterceptor implements HandlerInterceptor {
	// 校验token
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/*
		 *  如果不是映射到方法直接通过
		 *  意思是指没有被拦截的方法直接放行，不需要验证token
		 *  此处指/auth/*  拦截配置自定义
		 */
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		// 从http请求头取出token
		String token = request.getHeader("token");
		if(token == null) {
			throw new RuntimeException("无token，请重新登陆");
		}
		// 验证token
		JwtUtil.checkSign(token);
		// 验证通过后，取出jwt中存放的数据
		
		// 获取token中的userId
		String userId = JwtUtil.getUserId(token);
		System.out.println("id: "+userId);
		// 获取token中的info等数据
		Map<String, Object> info = JwtUtil.getInfo(token);
		info.forEach((k,v)->System.out.println(k+":"+v));
		return true;
	}

}
