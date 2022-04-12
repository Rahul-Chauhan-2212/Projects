package com.sbsi.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sbsi.entity.Category;

@Repository
public class CategoryDaoImpl implements CategoryDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public String addCategory(Category category) {
		return (String) sessionFactory.getCurrentSession().save(category);
	}

	@Override
	public List<Category> getAllCategories() {
		return sessionFactory.getCurrentSession().createQuery("SELECT c FROM Category c", Category.class)
				.getResultList();
	}

	@Override
	public Category findCatgeoryByCode(String ctgryCd) {
		return sessionFactory.getCurrentSession().get(Category.class, ctgryCd);
	}

}
