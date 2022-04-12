package com.sbsi.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbsi.dao.ProductDao;
import com.sbsi.entity.Product;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Transactional
	@Override
	public String addProduct(Product product) {
		return productDao.addProduct(product);
	}

	@Transactional
	@Override
	public List<Product> getAllProducts() {
		return productDao.getAllProducts();
	}

	@Transactional
	@Override
	public Product findProductByCode(String code) {
		return productDao.findProductByCode(code);
	}

	@Transactional
	@Override
	public Product updateProduct(Product product) {
		return productDao.updateProduct(product);

	}

}
