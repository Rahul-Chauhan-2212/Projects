package com.sbsi.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
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

import com.sbsi.dao.CartDao;
import com.sbsi.entity.Cart;
import com.sbsi.entity.CartProducts;
import com.sbsi.entity.Category;
import com.sbsi.entity.Product;
import com.sbsi.entity.User;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {

	@InjectMocks
	private CartServiceImpl cartService;

	@Mock
	private CartDao cartDao;

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
	public void testShouldAddProductToCart() {
		// given
		Category category = new Category();
		category.setCtgryCd("MBL");
		category.setCtgry("Mobiles");
		Product product = new Product();
		product.setPrdctCd("MBL3");
		product.setPrdctName("Realme Mobile");
		product.setPrdctDesc("Realme Mobile");
		product.setAvlblQnnty(BigInteger.valueOf(30));
		product.setPrice(BigDecimal.valueOf(26288.56));
		product.setCategory(category);
		product.setImage("sdhgdsh".getBytes());
		CartProducts cartProduct = new CartProducts();
		cartProduct.setProduct(product);
		cartProduct.setProductQntty(BigInteger.ONE);
		cartProduct.setSubTotal(BigDecimal.valueOf(26288.56));
		doAnswer(a -> {
			Cart cart1 = (Cart) a.getArgument(0);
			cartProduct.setCart(cart1);
			cart1.addCartProducts(cartProduct);
			return cart1;
		}).when(cartDao).addProductToCart(any(Cart.class));

		// when
		cartService.addProductToCart(cart);
		// then
		assertEquals(3, cart.getCartProducts().size());

	}

	@Test
	public void testShouldRemoveProductFromCart() {
		// given
		doAnswer(a -> {
			Cart cart1 = (Cart) a.getArgument(0);
			cart1.setCartProducts(cart1.getCartProducts().stream()
					.filter(cp -> cp.getProduct().getPrdctCd().compareTo("MBL1") != 0).collect(Collectors.toSet()));
			return cart1;
		}).when(cartDao).removeProductFromCart(any(Cart.class));
		// when
		cartService.removeProductFromCart(cart);
		// then
		assertEquals(1, cart.getCartProducts().size());

	}

	@Test
	public void testShouldGetCartSize() {
		// given
		String username = "rchauhan1401@gmail.com";
		when(cartDao.getCartSize(username)).thenReturn(cart.getCartProducts().size());
		// when
		Integer actualCartSize = cartService.getCartSize(username);
		// then
		assertEquals(cart.getCartProducts().size(), actualCartSize.intValue());

	}

	@Test
	public void testShouldGetCartByUsername() {
		// given
		String username = "rchauhan1401@gmail.com";
		when(cartDao.getCartByUsername(username)).thenReturn(cart);
		// when
		Cart actualCart = cartService.getCartByUsername(username);
		// then
		assertEquals(cart, actualCart);
	}

	@Test
	public void testShouldGetCartProducts() {
		// given
		String username = "rchauhan1401@gmail.com";
		when(cartDao.getCartProducts(username))
				.thenReturn(cart.getCartProducts().stream().collect(Collectors.toList()));
		// when
		List<CartProducts> actualCartProducts = cartService.getCartProducts(username);
		// then
		assertEquals(actualCartProducts.size(), cart.getCartProducts().size());

	}

	@Test
	public void testShouldGetCartTotal() {
		// given
		String username = "rchauhan1401@gmail.com";
		when(cartDao.getCartTotal(username)).thenReturn(cart.getTotalprice());
		// when
		BigDecimal actualTotal = cartService.getCartTotal(username);
		// then
		assertEquals(actualTotal, cart.getTotalprice());

	}

	@Test
	public void testShouldUpdateProductQuantityInCart() {
		// given
		doAnswer(a -> {
			Cart cart1 = (Cart) a.getArgument(0);
			cart1.setCartProducts(cart1.getCartProducts().stream().map(cp -> {
				if (cp.getProduct().getPrdctCd().compareTo("MBL1") == 0) {
					cp.setProductQntty(BigInteger.TEN);
				}
				return cp;
			}).collect(Collectors.toSet()));
			return cart1;
		}).when(cartDao).updateProductQuantityInCart(any(Cart.class));

		// when
		cartService.updateProductQuantityInCart(cart);
		// then
		assertEquals(BigInteger.TEN, cart.getCartProducts().stream()
				.filter(cp -> cp.getProduct().getPrdctCd().compareTo("MBL1") == 0).findFirst().get().getProductQntty());

	}

	@Test
	public void testShouldDeleteCartDetails() {
		// given
		doAnswer(a -> {
			Cart cart1 = (Cart) a.getArgument(0);
			cart1.getCartProducts().clear();
			cart1.setTotalprice(BigDecimal.ZERO);
			return cart1;
		}).when(cartDao).deleteCartDetails(any(Cart.class));
		// when
		Cart actualCart = cartService.deleteCartDetails(cart);
		// then
		assertEquals(actualCart.getCartProducts().size(), cart.getCartProducts().size());
		assertEquals(0, cart.getCartProducts().size());
	}

}
