package com.briup.cms.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.briup.cms.bean.Role;
import com.briup.cms.exception.ServiceException;

public interface RoleService {
	//分页获取所有角色信息
	Page<Role> findAll(Integer pageNum,Integer pageSize)throws ServiceException;
	//新增角色信息或者更新角色信息
	void saveOrUpdateRole(Role role)throws ServiceException;
	//批量删除角色信息
	void deleteRoleInBatch(List<Long> ids)throws ServiceException;
}
