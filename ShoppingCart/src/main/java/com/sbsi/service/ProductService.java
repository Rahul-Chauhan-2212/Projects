package com.sbsi.service;

import java.util.List;

import com.sbsi.entity.Product;

public interface ProductService {

	public String addProduct(Product product);

	public List<Product> getAllProducts();

	public Product findProductByCode(String code);

	public Product updateProduct(Product product);

}
