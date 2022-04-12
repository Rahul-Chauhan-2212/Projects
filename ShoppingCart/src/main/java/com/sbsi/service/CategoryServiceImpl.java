package com.sbsi.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbsi.dao.CategoryDao;
import com.sbsi.entity.Category;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	@Transactional
	@Override
	public String addCategory(Category category) {
		return categoryDao.addCategory(category);
	}

	@Transactional
	@Override
	public List<Category> getAllCategories() {
		return categoryDao.getAllCategories();
	}

	@Transactional
	@Override
	public Category findCatgeoryByCode(String ctgryCd) {

		return categoryDao.findCatgeoryByCode(ctgryCd);
	}

}
