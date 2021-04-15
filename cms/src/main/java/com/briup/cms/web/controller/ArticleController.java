package com.briup.cms.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.briup.cms.bean.Article;
import com.briup.cms.bean.User;
import com.briup.cms.service.ArticleService;
import com.briup.cms.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "文章模块")
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	
	@ApiOperation(value = "分页查询文章", notes = "提供当前页码和每页显示条数")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "currentPage", value = "当前页码", dataType = "int", required = true, paramType = "query", defaultValue = "0",example = "0"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示条数", dataType = "int", required = true, paramType = "query", defaultValue = "4",example = "0")
	})
	@GetMapping("/article/findAll")
	public Result findAll(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
		//返回完整分页信息（包括总条数，上一页下一页等。）
		Page<Article> all = articleService.findAll(currentPage, pageSize);
		//此处为了页面效果暂时只返回数据  ，可自行调整
		List<Article> articleList = all.getContent();
		return Result.success(articleList);
	}
	
	@ApiOperation(value = "批量删除文章", notes = "提供多个id，删除文章")
	@GetMapping("/article/deleteArticleInBatch")
	public Result deleteArticleInBatch(@RequestParam("ids") @ApiParam(value = "id列表") List<Long> ids) {
		articleService.deleteArticleInBatch(ids);
		return Result.success();
	}
	
	@ApiOperation(value = "修改文章状态", notes = "提供id和status")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "id", dataType = "Long", required = true, paramType = "query"),
	@ApiImplicitParam(name = "status", value = "状态", dataType = "String", required = true, paramType = "query")	})
	@GetMapping("/article/updateArticleStatus")
	public Result updateArticleStatus(@RequestParam("id")Long id,@RequestParam("status") String status) {
		articleService.updateArticleStatus(id, status);
		return Result.success();
	}
	@ApiOperation(value = "新增文章", notes = "新增文章，需要json格式的字符串")
	@PostMapping("/article")
	public Result saveOrUpdateArticle(@RequestBody Article article) {
		articleService.saveOrUpdateArticle(article);
		return Result.success(article);
	}
}
