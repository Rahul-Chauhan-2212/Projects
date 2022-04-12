package com.sbsi.service;

import java.util.List;

import com.sbsi.entity.Category;

public interface CategoryService {

	public String addCategory(Category category);

	public List<Category> getAllCategories();

	public Category findCatgeoryByCode(String ctgryCd);

}
