package com.sbsi.dao;

import java.util.List;

import com.sbsi.entity.Product;

public interface ProductDao {

	public String addProduct(Product product);

	public List<Product> getAllProducts();

	public Product findProductByCode(String code);

	public Product updateProduct(Product product);

}
