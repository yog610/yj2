package com.briup.cms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.briup.cms.bean.Category;


public interface CategoryDao extends JpaRepository<Category, Long> {
	Category findByName(String name);
}
