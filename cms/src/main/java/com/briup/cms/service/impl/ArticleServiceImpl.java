package com.briup.cms.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.briup.cms.bean.Article;
import com.briup.cms.bean.Comment;
import com.briup.cms.dao.ArticleDao;
import com.briup.cms.exception.ServiceException;
import com.briup.cms.service.ArticleService;
import com.briup.cms.util.ResultCode;
@Service
public class ArticleServiceImpl implements ArticleService{
	@Autowired
	private ArticleDao articleDao;
	@Autowired 
	private ArticleService articleService;

	@Override
	public void saveOrUpdateArticle(Article article) throws ServiceException {
		String title = article.getTitle();
		if(title == null || "".equals(title.trim())) {
			throw new ServiceException(ResultCode.PARAM_IS_BLANK);
		}
		articleDao.save(article);
	}

	@Override
	public Page<Article> findAll(Integer pageNum, Integer pageSize) throws ServiceException {
		return articleDao.findAll(PageRequest.of(pageNum, pageSize));
	}

	@Override
	public void deleteArticleInBatch(List<Long> ids) throws ServiceException {
		for (Long id : ids) {
			articleDao.deleteById(id);
		}
	}

	@Override
	public void updateArticleStatus(Long id, String status) throws ServiceException {
		Article article = articleDao.getOne(id);
		article.setStatus(status);
		articleDao.save(article);
	}

	@Override
	public Page<Comment> findAllCommentsByArticleId(Long articleId, int pageNum, int pageSize) throws ServiceException {
		return articleService.findAllCommentsByArticleId(articleId, pageNum, pageSize);
		
	}

}
