package com.briup.cms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.briup.cms.bean.User;
import com.briup.cms.dao.UserDao;
import com.briup.cms.exception.ServiceException;
import com.briup.cms.service.UserService;
import com.briup.cms.util.ResultCode;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public Page<User> getAll(Integer pageNum, Integer pageSize) throws ServiceException {
		return userDao.findAll(PageRequest.of(pageNum, pageSize));
	}

	@Override
	public void saveOrUpdateUser(User user) throws ServiceException {
		String username = user.getUsername();
		if (username == null || "".equals(username.trim())) {
			throw new ServiceException(ResultCode.USER_NOT_EMPTY);
		}
		User userFromDB = userDao.findByUsername(user.getUsername());
		if (userFromDB != null) {
			throw new ServiceException(ResultCode.USER_HAS_EXISTED);
		}
		// 保存用户
		userDao.save(user);
	}

	@Override
	public void deleteUserInBatch(List<Long> ids) throws ServiceException {
		if(ids == null) {
			throw new ServiceException(ResultCode.PARAM_IS_INVALID);
		}
		for (Long id : ids) {
			userDao.deleteById(id);
		}
	}

	@Override
	public void updateUserStatus(Long id, String status) throws ServiceException {
		User user = userDao.getOne(id);
		if(user == null) {
			throw new ServiceException(ResultCode.PARAM_IS_INVALID);
		}
		user.setStatus(status);
		userDao.save(user);
	}

	@Override
	public User login(String username, String password) throws ServiceException {
		User user = findUserByUsername(username);
		if (user == null || !user.getPassword().equals(password)) {
			throw new ServiceException(ResultCode.USER_LOGIN_ERROR);
		}
		return user;
	}

	@Override
	public User findUserByUsername(String username) throws ServiceException {
		return userDao.findByUsername(username);
	}

}
