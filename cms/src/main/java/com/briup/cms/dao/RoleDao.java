package com.briup.cms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.briup.cms.bean.Role;

public interface RoleDao extends JpaRepository<Role, Long>{
	public Role findByName(String name);
}
