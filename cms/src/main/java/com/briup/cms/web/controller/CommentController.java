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
import com.briup.cms.bean.Comment;
import com.briup.cms.service.CommentService;
import com.briup.cms.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags = "评论模块")
public class CommentController {
	@Autowired
	private CommentService commentService;

	@ApiOperation(value = "新增评论", notes = "新增文章，需要json格式的字符串")
	@PostMapping("/comment")
	public Result saveOrUpdateComment(@RequestBody Comment comment) {
		commentService.saveOrUpdateComment(comment);
		return Result.success(comment);
	}

	@ApiOperation(value = "批量删除评论", notes = "提供多个id，删除评论")
	@GetMapping("/commnet/deleteCommentInBatch")
	public Result deleteCommentInBatch(@RequestParam("ids") @ApiParam(value = "id列表") List<Long> ids) {
		commentService.deleteCommentInBatch(ids);
		return Result.success();
	}

	@ApiOperation(value = "分页查询评论", notes = "提供当前页码和每页显示条数以及所在文章")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "articleId", value = "当前文章id", dataType = "Long", required = true, paramType = "query"),
			@ApiImplicitParam(name = "currentPage", value = "当前页码", dataType = "int", required = true, paramType = "query", defaultValue = "0", example = "0"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示条数", dataType = "int", required = true, paramType = "query", defaultValue = "4", example = "0") })
	@GetMapping("/comment/findAllByArticleId")
	public Result findAllByArticleId(@RequestParam("articleId") Long articleId, @RequestParam("currentPage") int currentPage,
			@RequestParam("pageSize") int pageSize) {
		// 返回完整分页信息（包括总条数，上一页下一页等。）
		Page<Comment> all = commentService.findAllByArticleId(articleId, currentPage, pageSize);
		// 此处为了页面效果暂时只返回数据 ，可自行调整
		List<Comment> commentList = all.getContent();
		return Result.success(commentList);
	}
}
