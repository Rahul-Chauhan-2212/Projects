package com.sbsi.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.sbsi.entity.Category;
import com.sbsi.entity.Product;
import com.sbsi.service.ProductService;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

	@InjectMocks
	private ProductController productController;

	@Mock
	private ProductService productService;

	private List<Product> products;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		products = new ArrayList<>();
		Category category = new Category();
		category.setCtgryCd("MBL");
		category.setCtgry("Mobiles");
		Product product1 = new Product();
		product1.setPrdctCd("MBL1");
		product1.setPrdctName("Samsung Mobile");
		product1.setPrdctDesc("Samsung Mobile");
		product1.setAvlblQnnty(BigInteger.valueOf(20));
		product1.setPrice(BigDecimal.valueOf(47829.99));
		product1.setCategory(category);
		product1.setImage("djhdjdjd".getBytes());
		Product product2 = new Product();
		product2.setPrdctCd("MBL2");
		product2.setPrdctName("Apple Mobile");
		product2.setPrdctDesc("Apple Mobile");
		product2.setAvlblQnnty(BigInteger.valueOf(26));
		product2.setPrice(BigDecimal.valueOf(273932.99));
		product2.setCategory(category);
		product2.setImage("dyhdhd".getBytes());
		products.add(product1);
		products.add(product2);

	}

	@Test
	public void testShouldGetAllProducts() {
		// given
		when(productService.getAllProducts()).thenReturn(products);
		// when
		List<Product> actualProducts = productController.getAllProducts();
		// then
		assertEquals(actualProducts, products);
	}
}
