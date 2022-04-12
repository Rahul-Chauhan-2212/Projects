package com.sbsi.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.sbsi.entity.Cart;
import com.sbsi.entity.CartProducts;
import com.sbsi.entity.Category;
import com.sbsi.entity.Product;
import com.sbsi.entity.User;
import com.sbsi.service.CartService;

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {

	@InjectMocks
	private CartController cartController;

	@Mock
	private CartService cartService;

	private Cart cart;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		cart = new Cart();
		User user = new User();
		user.setUsername("rchauhan1401@gmail.com");
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
		CartProducts cartProduct1 = new CartProducts();
		cartProduct1.setProduct(product1);
		cartProduct1.setProductQntty(BigInteger.valueOf(4));
		cartProduct1.setSubTotal(
				product1.getPrice().multiply(BigDecimal.valueOf(cartProduct1.getProductQntty().longValue())));
		cartProduct1.setCart(cart);
		CartProducts cartProduct2 = new CartProducts();
		cartProduct2.setProduct(product2);
		cartProduct2.setProductQntty(BigInteger.valueOf(2));
		cartProduct2.setSubTotal(
				product2.getPrice().multiply(BigDecimal.valueOf(cartProduct2.getProductQntty().longValue())));
		cartProduct2.setCart(cart);
		Set<CartProducts> cartProducts = new HashSet<>();
		cartProducts.add(cartProduct1);
		cartProducts.add(cartProduct2);
		cart.setCartId("rchauhan1401@gmail.com");
		cart.setCartProducts(cartProducts);
		user.setCart(cart);
		cart.setUser(user);
		cart.setTotalprice(
				cart.getCartProducts().stream().map(a -> a.getSubTotal()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
	}

	@Test
	public void testShouldGetCartSize() {
		// given
		String username = "rchauhan1401@gmail.com";
		when(cartService.getCartSize(username)).thenReturn(cart.getCartProducts().size());
		// when
		Integer actualCartSize = cartController.getCartSize(username);
		// then
		assertEquals(cart.getCartProducts().size(), actualCartSize.intValue());
	}

	@Test
	public void testShouldGetCartByUsername() {
		// given
		String username = "rchauhan1401@gmail.com";
		when(cartService.getCartByUsername(username)).thenReturn(cart);
		// when
		Cart actualCart = cartController.getCartByUsername(username);
		// then
		assertEquals(cart, actualCart);
	}

	@Test
	public void testShoulGetCartProducts() {
		// given
		String username = "rchauhan1401@gmail.com";
		when(cartService.getCartProducts(username))
				.thenReturn(cart.getCartProducts().stream().collect(Collectors.toList()));
		// when
		List<CartProducts> actualCartProducts = cartController.getCartProducts(username);
		// then
		assertEquals(actualCartProducts.size(), cart.getCartProducts().size());
	}

	@Test
	public void testShouldGetCartTotal() {
		// given
		String username = "rchauhan1401@gmail.com";
		when(cartService.getCartTotal(username)).thenReturn(cart.getTotalprice());
		// when
		Double actualTotal = cartController.getCartTotal(username);
		// then
		assertEquals(actualTotal.doubleValue(), cart.getTotalprice().doubleValue(), 0);
	}

}
