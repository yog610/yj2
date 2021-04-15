package com.briup.cms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.briup.cms.bean.Article;


public interface ArticleDao extends JpaRepository<Article, Long>{

}
