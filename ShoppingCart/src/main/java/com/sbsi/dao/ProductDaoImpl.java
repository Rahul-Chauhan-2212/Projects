package com.sbsi.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbsi.entity.Product;

@Service
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public String addProduct(Product product) {
		return (String) sessionFactory.getCurrentSession().save(product);

	}

	@Override
	public List<Product> getAllProducts() {
		return sessionFactory.getCurrentSession().createQuery("SELECT p FROM Product p", Product.class).getResultList();

	}

	@Override
	public Product findProductByCode(String code) {
		return sessionFactory.getCurrentSession().get(Product.class, code);
	}

	@Override
	public Product updateProduct(Product product) {
		sessionFactory.getCurrentSession().saveOrUpdate(product);
		return product;
	}

}
