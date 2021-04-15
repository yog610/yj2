package com.briup.cms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.briup.cms.bean.User;

public interface UserDao extends JpaRepository<User, Long> {

	public User findByUsername(String name);
}
