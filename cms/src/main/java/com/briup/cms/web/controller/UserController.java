package com.briup.cms.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.briup.cms.bean.User;
import com.briup.cms.service.UserService;
import com.briup.cms.util.JwtUtil;
import com.briup.cms.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "用户管理模块")
@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@ApiOperation(value = "新增用户", notes = "新增用户，需要json格式的字符串")
	@PostMapping("/user")
	public Result register(@RequestBody User user) {
		userService.saveOrUpdateUser(user);
		return Result.success(user);
	}

	@ApiOperation(value = "验证测试", notes = "添加jwt，后，测试指定接口是否需要验证")
	@ApiImplicitParams({
			//@ApiImplicitParam(name = "username", value = "用户名", dataType = "String", required = true, paramType = "query", defaultValue = "tom")
			@ApiImplicitParam(name = "token", value = "JWT", dataType = "String", required = true, paramType = "header", defaultValue = "aaa.bbb.ccc")
	})
	@GetMapping("/auth/test")
	public Result test(String username) {
		return Result.success("hello! " + username);
	}

	/***
	 * 注意，@PostMapping(value="/login",consumes = "application/x-www-form-urlencoded")来指定
	 * swagger中，把参数以什么形式进行传递 * 如果这里不指定，那么它或默认使用application/json *
	 * 一定要观察swagger中显示执行的CURL命令中指定的请求头信息
	 */
	@ApiOperation(value = "用户登陆",notes = "登陆后，返回jwt的token值")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "用户名", dataType = "String", required = true, paramType = "form", defaultValue = "tom"),
			@ApiImplicitParam(name = "password", value = "密码", dataType = "String", required = true, paramType = "form", defaultValue = "123456"), })
	@PostMapping(value="/login",consumes = "application/x-www-form-urlencoded")
	public Result login(@RequestParam("username") String username, @RequestParam("password") String password) {
		User user = userService.login(username, password);
		Map<String, Object> info = new HashMap<>();
		info.put("username", user.getUsername());
		info.put("realname", user.getRealname());
		String token = JwtUtil.sign(Long.toString(user.getId()), info);
		return Result.success("token: " + token);
	}
	
	@ApiOperation(value = "分页查询用户", notes = "提供当前页码和每页显示条数")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "currentPage", value = "当前页码", dataType = "int", required = true, paramType = "query", defaultValue = "0",example = "0"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示条数", dataType = "int", required = true, paramType = "query", defaultValue = "4",example = "0")
	})
	@GetMapping("/user/findAll")
	public Result findAll(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
		//返回完整分页信息（包括总条数，上一页下一页等。）
		Page<User> all = userService.getAll(currentPage, pageSize);
		//此处为了页面效果暂时只返回数据  ，可自行调整
		List<User> userList = all.getContent();
		return Result.success(userList);
	}
	
	@ApiOperation(value = "批量删除用户", notes = "提供多个id，删除用户")
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "ids", value = "批量id", dataType = "Long", paramType = "query",required = true)	})
	@GetMapping("/user/deleteUserInBatch")
	public Result deleteUserInBatch(@RequestParam("ids") @ApiParam(value = "id列表") List<Long> ids) {
		userService.deleteUserInBatch(ids);
		return Result.success();
	}
	
	@ApiOperation(value = "修改用户状态", notes = "提供id和status")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "id", dataType = "Long", required = true, paramType = "query"),
	@ApiImplicitParam(name = "status", value = "状态", dataType = "String", required = true, paramType = "query")	})
	@GetMapping("/user/updateUserStatus")
	public Result updateUserStatus(@RequestParam("id")Long id,@RequestParam("status") String status) {
		userService.updateUserStatus(id, status);
		return Result.success();
	}
}
