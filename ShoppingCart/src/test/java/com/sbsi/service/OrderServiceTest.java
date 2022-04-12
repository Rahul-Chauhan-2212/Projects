package com.sbsi.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
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
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.sbsi.dao.OrderDao;
import com.sbsi.entity.Address;
import com.sbsi.entity.Category;
import com.sbsi.entity.Order;
import com.sbsi.entity.OrderProducts;
import com.sbsi.entity.Product;
import com.sbsi.entity.User;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderServiceTest {

	@InjectMocks
	private OrderServiceImpl orderService;

	@Mock
	private OrderDao orderDao;

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

	@SuppressWarnings("deprecation")
	@Test
	public void test_1_ShouldcreateNewOrder() {
		// given
		Address address = new Address();
		address.setAddressLine1("ABC");
		address.setAddressLine2("XYZ");
		address.setCity("DEF");
		address.setState("GHI");
		address.setCountry("JKL");
		address.setZipCode("123456");
		User user = new User();
		user.setUsername("rchauhan9102@gmail.com");
		Category category = new Category();
		category.setCtgryCd("TBLT");
		category.setCtgry("Tablets");
		Product product1 = new Product();
		product1.setPrdctCd("TBLT1");
		product1.setPrdctName("Samsung Tablet");
		product1.setPrdctDesc("Samsung Tablet");
		product1.setAvlblQnnty(BigInteger.valueOf(56));
		product1.setPrice(BigDecimal.valueOf(23536.99));
		product1.setCategory(category);
		product1.setImage("dghdhd".getBytes());
		Product product2 = new Product();
		product2.setPrdctCd("TBLT2");
		product2.setPrdctName("Apple Tablet");
		product2.setPrdctDesc("Apple Tablet");
		product2.setAvlblQnnty(BigInteger.valueOf(25));
		product2.setPrice(BigDecimal.valueOf(35373.99));
		product2.setCategory(category);
		product2.setImage("shdhdhd".getBytes());
		Order order = new Order();
		order.setOrderNum(BigInteger.valueOf(35));
		order.setCustomerName("KL Rahul");
		order.setCustomerEmail("klrahul@gmail.com");
		order.setCustomerPhone("dhdjdjsgj");
		order.setCustomerAddress(address);
		order.setOrderDate(new Date(2022, 4, 25));
		OrderProducts order1Product1 = new OrderProducts();
		order1Product1.setProduct(product1);
		order1Product1.setProductQntty(BigInteger.valueOf(3));
		order1Product1.setSubTotal(
				product1.getPrice().multiply(BigDecimal.valueOf(order1Product1.getProductQntty().longValue())));
		order1Product1.setOrder(order);
		OrderProducts order1Product2 = new OrderProducts();
		order1Product2.setProduct(product2);
		order1Product2.setProductQntty(BigInteger.valueOf(4));
		order1Product2.setSubTotal(
				product2.getPrice().multiply(BigDecimal.valueOf(order1Product2.getProductQntty().longValue())));
		order1Product2.setOrder(order);
		order.setAmount(order.getOrderProducts().parallelStream()
				.map(c -> BigDecimal.valueOf(c.getProductQntty().longValue()).multiply(c.getProduct().getPrice()))
				.reduce(BigDecimal.ZERO, BigDecimal::add));
		Set<OrderProducts> order1Products1 = new HashSet<>();
		order1Products1.add(order1Product1);
		order1Products1.add(order1Product2);
		order.setUser(user);
		Set<Order> userOrders = new HashSet<>();
		userOrders.add(order);
		user.setOrders(userOrders);
		doAnswer(o -> {
			Order order1 = (Order) o.getArgument(0);
			orders.add(order1);
			return order1.getOrderNum();
		}).when(orderDao).createNewOrder(order);

		// when
		orderService.createNewOrder(order);
		// then
		assertEquals(2, orders.size());

	}

	@Test
	public void test_2_ShouldGetMaxOrderNum() {
		// given
		when(orderDao.getMaxOrderNum())
				.thenReturn(orders.stream().map(o -> o.getOrderNum()).reduce(BigInteger.ZERO, BigInteger::max));
		// when
		BigInteger maxOrderNum = orderService.getMaxOrderNum();
		// then
		assertEquals(maxOrderNum, BigInteger.valueOf(35));

	}

	@Test
	public void test_3_ShouldGetAllOrders() {
		// given
		when(orderDao.getAllOrders()).thenReturn(orders);
		// when
		List<Order> actualOrders = orderService.getAllOrders();
		// then
		assertEquals(actualOrders.size(), orders.size());

	}

	@Test
	public void test_4_ShouldGetAllOrdersByUserId() {
		// given
		String username = "rchauhan9102@gmail.com";
		when(orderDao.getAllOrdersByUserId(username)).thenReturn(orders.stream()
				.filter(o -> o.getUser().getUsername().compareTo(username) == 0).collect(Collectors.toList()));
		// when
		List<Order> actualOrders = orderService.getAllOrdersByUserId(username);
		// then
		assertEquals(actualOrders.size(), 1);

	}

}
