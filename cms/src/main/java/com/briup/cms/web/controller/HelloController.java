package com.briup.cms.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.briup.cms.util.JwtUtil;
import com.briup.cms.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
//类注释
@Api(tags = "测试模块")
@RestController
public class HelloController {
	  //方法注释
	@ApiOperation(value = "hello测试",notes = "第一个swagger测试程序")
	/*
	 * 	参数注释
	 *  dataType：参数类型 默认String,其他可为Integer
	 *  paramType：参数放在哪个地方
            · header --> 请求参数的获取：@RequestHeader
            · query --> 请求参数的获取：@RequestParam
            · path（用于restful接口）--> 请求参数的获取：@PathVariable
            · body（不常用）
            · form（不常用） 
	 */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "用户名",dataType = "String",required = true,paramType = "query"),
    })
	@GetMapping("/auth/hello")
	public Result hello(String name) {
		if("tom".equals(name)) {
			throw new RuntimeException("test...");
		}
		return Result.success("hello world!"+name);
	}
	@GetMapping("/login")
	public Result login(String username, String password) {
		//假设数据库中查询到了该用户，这里测试先随机生成一个UUID，作为用户ID
		String userId = UUID.randomUUID().toString();
		Map<String,Object> info = new HashMap<>();
		info.put("username", "tom");
		info.put("role", "admin");
		//生成JWT字符串
		String token = JwtUtil.sign(userId, info);
		return Result.success("token: "+token);
	}
}
