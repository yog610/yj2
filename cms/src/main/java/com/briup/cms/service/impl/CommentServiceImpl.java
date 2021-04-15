package com.briup.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.briup.cms.bean.Comment;
import com.briup.cms.dao.CommentDao;
import com.briup.cms.exception.ServiceException;
import com.briup.cms.service.CommentService;
/*
 * 暂未处理相关异常
 */
@Service
public class CommentServiceImpl implements CommentService{
	@Autowired
	private CommentDao commentDao;
	@Override
	public Page<Comment> findAll(Integer pageNum, Integer pageSize) throws ServiceException {
		return commentDao.findAll(PageRequest.of(pageNum, pageSize));
	}

	@Override
	public void saveOrUpdateComment(Comment comment) throws ServiceException {
		commentDao.save(comment);
	}

	@Override
	public void deleteCommentInBatch(List<Long> ids) throws ServiceException {
		for (Long id : ids) {
			commentDao.deleteById(id);
		}
	}

	@Override
	public Page<Comment> findAllByArticleId(Long articleId, Integer pageNum, Integer pageSize) throws ServiceException {
		return commentDao.findByArticleId(articleId, PageRequest.of(pageNum, pageSize));
	}

}
