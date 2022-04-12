package com.sbsi.dao;

import java.util.List;

import com.sbsi.entity.Category;

public interface CategoryDao {

	public String addCategory(Category category);

	public List<Category> getAllCategories();

	public Category findCatgeoryByCode(String ctgryCd);

}
