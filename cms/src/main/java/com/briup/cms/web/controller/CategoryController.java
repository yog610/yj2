package com.briup.cms.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.briup.cms.bean.Category;
import com.briup.cms.bean.Role;
import com.briup.cms.service.CategoryService;
import com.briup.cms.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "目录模块")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	//新增或者更新
		@ApiOperation(value = "新增/更新目录",notes = "新增/更新目录，需要json格式的字符串")
		@PostMapping("/category")
		public Result saveOrUpdateCategory(@RequestBody Category category) {
			categoryService.saveOrUpdateCategory(category);
			return Result.success(category);
		}
	/*
	 * 分页查询
	 */
	@ApiOperation(value = "查询目录",notes = "分页并且倒序查询" )
	@ApiImplicitParams({
		@ApiImplicitParam(name = "currentPage",value = "当前页码",dataType = "int",paramType = "query",required = true,defaultValue = "0"),
		@ApiImplicitParam(name = "pageSize",value = "每页显示数量",dataType = "int",paramType = "query",required = true,defaultValue = "4"),
	})
	@GetMapping("category/findAll")
	public Result findAllSortbyno(@RequestParam("currentPage") int currentPage,@RequestParam("pageSize") int pageSize) {
		Page<Category> categoryPage = categoryService.findAllSortbyno(currentPage, pageSize);
		return Result.success(categoryPage.getContent());
	}
	/*
	 * 批量删除分类
	 */
	@ApiOperation(value = "删除目录",notes = "提供多个id,批量删除")
	@GetMapping("category/deleteCategoryInBatch")
	public Result deleteCategoryInBatch(@RequestParam("ids" )@ApiParam(value = "id列表") List<Long>ids) {
		categoryService.deleteCategoryInBatch(ids);
		return Result.success();
	}
	/*
	 * 更新目录排序
	 */
	@ApiOperation(value = "更新分类排序",notes = "改变排序字段")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",value = "id",dataType = "Long",paramType = "query",required = true),
		@ApiImplicitParam(name = "no",value = "排序位置",dataType = "int",paramType = "query",required = true)
	})
	@GetMapping("category/updateCategoryNo")
	public Result updateCategoryNo(@RequestParam("id")Long id,@RequestParam("no") int no) {
		categoryService.updateCategoryNo(id, no);
		return Result.success();
	}
	
}
