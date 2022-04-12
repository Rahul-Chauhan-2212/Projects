package com.sbsi.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.sbsi.dao.ProductDao;
import com.sbsi.entity.Category;
import com.sbsi.entity.Product;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

	@InjectMocks
	private ProductServiceImpl productService;

	@Mock
	private ProductDao productDao;

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
	public void testShouldAddProduct() {
		// given
		String expectedCode = "TBLT1";
		Category category = new Category();
		category.setCtgry("TABLETS");
		category.setCtgryCd("TBLT");
		Product product = new Product();
		product.setPrdctCd("TBLT1");
		product.setPrdctName("Apple Tablet");
		product.setPrdctDesc("Apple Tablet");
		product.setAvlblQnnty(BigInteger.valueOf(54));
		product.setPrice(BigDecimal.valueOf(34568.99));
		product.setCategory(category);
		product.setImage("fsrgdnbhj".getBytes());
		doAnswer(c -> {
			Product prdct = (Product) c.getArgument(0);
			products.add(prdct);
			return prdct.getPrdctCd();
		}).when(productDao).addProduct(product);
		// when
		String actualCode = productService.addProduct(product);
		// then
		assertEquals(actualCode, expectedCode);
		assertEquals(product, products.get(2));

	}

	@Test
	public void testShouldGetAllProducts() {
		// given
		when(productDao.getAllProducts()).thenReturn(products);
		// when
		List<Product> actualProducts = productService.getAllProducts();
		// then
		assertEquals(actualProducts, products);
	}

	@Test
	public void testShouldFindProductByCode() {
		// given
		String code = "MBL1";
		when(productDao.findProductByCode(code))
				.thenReturn(products.stream().filter(u -> u.getPrdctCd().compareTo(code) == 0).findFirst().get());
		// when
		Product actualProduct = productService.findProductByCode(code);
		// then
		assertEquals(actualProduct.getPrdctCd(), code);
	}

	@Test
	public void testShouldUpdateProduct() {
		// given
		Product product = products.get(0);
		product.setAvlblQnnty(BigInteger.valueOf(30));
		doAnswer((i) -> {
			products = products.stream().map(p -> {
				if (p.getPrdctCd().compareTo(((Product) i.getArgument(0)).getPrdctCd()) == 0) {
					p.setAvlblQnnty(BigInteger.valueOf(30));
				}
				return p;
			}).collect(Collectors.toList());
			return null;
		}).when(productDao).updateProduct(product);
		// when
		productService.updateProduct(product);
		// then
		assertEquals(product.getAvlblQnnty(), products.get(0).getAvlblQnnty());

	}

	@After
	public void destroy() {
		products.clear();
	}

}
