package com.briup.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.briup.cms.bean.Role;
import com.briup.cms.dao.RoleDao;
import com.briup.cms.exception.ServiceException;
import com.briup.cms.service.RoleService;
import com.briup.cms.util.ResultCode;
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleDao roleDao;
	@Override
	public Page<Role> findAll(Integer pageNum, Integer pageSize) throws ServiceException {
		if(pageNum <0 || pageSize <0) {
			throw new ServiceException(ResultCode.PARAM_IS_INVALID);
		}
		return roleDao.findAll(PageRequest.of(pageNum, pageSize));
	}

	@Override
	public void saveOrUpdateRole(Role role) throws ServiceException {
		if(role.getName() == null || "".equals(role.getName().trim())) {
			throw new ServiceException(ResultCode.PARAM_IS_INVALID);
		}
		Role roleFromDB = roleDao.findByName(role.getName());
		if(roleFromDB != null) {
			throw new ServiceException(ResultCode.DATA_EXISTED);
		}
		roleDao.save(role);
	}

	@Override
	public void deleteRoleInBatch(List<Long> ids) throws ServiceException {
		if(ids == null || ids.size()<0) {
			throw new ServiceException(ResultCode.PARAM_IS_INVALID);
		}
		for (Long id : ids) {
			roleDao.deleteById(id);
		}
	}

}
