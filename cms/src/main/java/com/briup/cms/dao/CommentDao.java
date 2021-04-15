package com.briup.cms.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.briup.cms.bean.Comment;


public interface CommentDao extends JpaRepository<Comment, Long> {
	
	Page<Comment> findByArticleId(Long id,Pageable pageable);
}
