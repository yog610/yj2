package com.briup.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.briup.cms.bean.Category;
import com.briup.cms.dao.CategoryDao;
import com.briup.cms.exception.ServiceException;
import com.briup.cms.service.CategoryService;
import com.briup.cms.util.ResultCode;
@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryDao categoryDao;
	@Override
	public Page<Category> findAll(Integer pageNum, Integer pageSize) throws ServiceException {
		if(pageNum<0 || pageSize<=0) {
			throw new ServiceException(ResultCode.PARAM_IS_INVALID);
		}
		return categoryDao.findAll(PageRequest.of(pageNum, pageSize));
	}

	@Override
	public void saveOrUpdateCategory(Category category) throws ServiceException {
		String name = category.getName();
		if(name == null || "".equals(name.trim())) {
			throw new ServiceException(ResultCode.PARAM_IS_BLANK);
		}
		Category categoryFromDB = categoryDao.findByName(name);
		if(categoryFromDB != null) {
			throw new ServiceException(ResultCode.DATA_EXISTED);
		}
		categoryDao.save(category);
	}

	@Override
	public void deleteCategoryInBatch(List<Long> ids) throws ServiceException {
		for (Long id : ids) {
			categoryDao.deleteById(id);
		}
	}

	@Override
	public Page<Category> findAllSortbyno(Integer pageNum, Integer pageSize) throws ServiceException {
		Pageable page = PageRequest.of(pageNum, pageSize, Sort.by(Direction.DESC, "no"));
		return categoryDao.findAll(page);
	}

	@Override
	public void updateCategoryNo(Long id, int no) throws ServiceException {
		if(id == null) {
			throw new ServiceException(ResultCode.PARAM_IS_INVALID);
		}
		Category category = categoryDao.getOne(id);
		category.setNo(no);
		categoryDao.save(category);
	}

}
