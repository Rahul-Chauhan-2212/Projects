package com.sbsi.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.sbsi.entity.Address;
import com.sbsi.entity.Category;
import com.sbsi.entity.Order;
import com.sbsi.entity.OrderProducts;
import com.sbsi.entity.Product;
import com.sbsi.entity.User;
import com.sbsi.service.OrderService;
import com.sbsi.service.OrderServiceTest;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

	@InjectMocks
	private OrderController orderController;

	@Mock
	private OrderService orderService;

	private static List<Order> orders;

	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void setUp() throws Exception {
		MockitoAnnotations.initMocks(OrderServiceTest.class);
		orders = new ArrayList<>();
		Address address = new Address();
		address.setAddressLine1("ABC");
		address.setAddressLine2("XYZ");
		address.setCity("DEF");
		address.setState("GHI");
		address.setCountry("JKL");
		address.setZipCode("123456");
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
		Order order1 = new Order();
		order1.setOrderNum(BigInteger.valueOf(30));
		order1.setCustomerName("Virat Kohli");
		order1.setCustomerEmail("virat@gmail.com");
		order1.setCustomerPhone("2638393");
		order1.setCustomerAddress(address);
		order1.setOrderDate(new Date(2022, 3, 22));
		OrderProducts order1Product1 = new OrderProducts();
		order1Product1.setProduct(product1);
		order1Product1.setProductQntty(BigInteger.valueOf(4));
		order1Product1.setSubTotal(
				product1.getPrice().multiply(BigDecimal.valueOf(order1Product1.getProductQntty().longValue())));
		order1Product1.setOrder(order1);
		OrderProducts order1Product2 = new OrderProducts();
		order1Product2.setProduct(product2);
		order1Product2.setProductQntty(BigInteger.valueOf(2));
		order1Product2.setSubTotal(
				product2.getPrice().multiply(BigDecimal.valueOf(order1Product2.getProductQntty().longValue())));
		order1Product2.setOrder(order1);
		order1.setAmount(order1.getOrderProducts().parallelStream()
				.map(c -> BigDecimal.valueOf(c.getProductQntty().longValue()).multiply(c.getProduct().getPrice()))
				.reduce(BigDecimal.ZERO, BigDecimal::add));
		Set<OrderProducts> order1Products1 = new HashSet<>();
		order1Products1.add(order1Product1);
		order1Products1.add(order1Product2);
		order1.setUser(user);
		Set<Order> userOrders = new HashSet<>();
		userOrders.add(order1);
		user.setOrders(userOrders);
		orders.add(order1);

	}

	@Test
	public void testShouldGetMaxOrderNum() {
		// given
		when(orderService.getMaxOrderNum())
				.thenReturn(orders.stream().map(o -> o.getOrderNum()).reduce(BigInteger.ZERO, BigInteger::max));
		// when
		BigInteger maxOrderNum = orderController.getMaxOrderNum();
		// then
		assertEquals(maxOrderNum, BigInteger.valueOf(30));
	}

	@Test
	public void testShouldGetAllOrders() {
		// given
		when(orderService.getAllOrders()).thenReturn(orders);
		// when
		List<Order> actualOrders = orderController.getAllOrders();
		// then
		assertEquals(actualOrders.size(), orders.size());
	}

	@Test
	public void testShouldGetAllOrdersByUserId() {
		// given
		String username = "rchauhan9102@gmail.com";
		when(orderService.getAllOrdersByUserId(username)).thenReturn(orders.stream()
				.filter(o -> o.getUser().getUsername().compareTo(username) == 0).collect(Collectors.toList()));
		// when
		List<Order> actualOrders = orderController.getAllOrdersByUserId(username);
		// then
		assertEquals(actualOrders.size(), 0);
	}

}
