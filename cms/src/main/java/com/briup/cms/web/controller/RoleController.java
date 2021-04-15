package com.briup.cms.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.briup.cms.bean.Role;
import com.briup.cms.service.RoleService;
import com.briup.cms.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "角色管理模块")
public class RoleController {
	@Autowired
	private RoleService roleService;
	//新增或者更新
	@ApiOperation(value = "新增/更新角色",notes = "新增/更新角色，需要json格式的字符串")
	@PostMapping("/role")
	public Result saveOrUpdate(@RequestBody Role role) {
		roleService.saveOrUpdateRole(role);
		return Result.success(role);
	}
	//分页查询
	@ApiOperation(value = "查询角色", notes = "提供当前页码和每页显示条数")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "currentPage", value = "当前页码", dataType = "int", required = true, paramType = "query", defaultValue = "0",example = "0"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示条数", dataType = "int", required = true, paramType = "query", defaultValue = "4",example = "0")
	})
	@GetMapping("/role/findAll")
	public Result findAll(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
		//完成分页信息
		Page<Role> all = roleService.findAll(currentPage, pageSize);
		//仅仅数据
		return Result.success(all.getContent());
	}
	
	@ApiOperation(value = "删除角色", notes = "提供多个id,批量删除")
	@GetMapping("/user/deleteRoleInBatch")
	public Result deleteRoleInBatch(@RequestParam("ids") @ApiParam(value = "id列表") List<Long> ids) {
		roleService.deleteRoleInBatch(ids);
		return Result.success();
	}
}
