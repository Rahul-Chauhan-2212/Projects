package com.sbsi.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderDaoTest {

	@InjectMocks
	private OrderDaoImpl orderDAO;

	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Session session;

	@Mock
	CriteriaBuilder builder;

	@Mock
	CriteriaQuery<BigInteger> integerQuery;

	@Mock
	CriteriaQuery<Order> orderQuery;

	@Mock
	Root<Order> root;

	@Mock
	Query<BigInteger> maxQuery;

	@Mock
	Query<Order> orderByUsernameQuery;

	private static List<Order> orders;

	@SuppressWarnings("deprecation")
	@BeforeClass
	public static void setUp() throws Exception {
		MockitoAnnotations.initMocks(OrderDaoTest.class);
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
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		doAnswer(o -> {
			Order order1 = (Order) o.getArgument(0);
			orders.add(order1);
			return order1.getOrderNum();
		}).when(session).save(any(Order.class));

		// when
		orderDAO.createNewOrder(order);
		// then
		assertEquals(2, orders.size());

	}

	@Test
	public void test_2_ShouldGetMaxOrderNum() {
		// given
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		when(session.getCriteriaBuilder()).thenReturn(builder);
		when(builder.createQuery(BigInteger.class)).thenReturn(integerQuery);
		when(integerQuery.from(Order.class)).thenReturn(root);
		when(session.createQuery(integerQuery)).thenReturn(maxQuery);
		when(maxQuery.getSingleResult())
				.thenReturn(orders.stream().map(o -> o.getOrderNum()).reduce(BigInteger.ZERO, BigInteger::max));

		// when
		BigInteger maxOrderNum = orderDAO.getMaxOrderNum();
		// then
		assertEquals(maxOrderNum, BigInteger.valueOf(35));

	}

	@Test
	public void test_3_ShouldGetAllOrders() {
		// given
		@SuppressWarnings("unchecked")
		Query<Order> query = mock(Query.class);
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		when(session.createQuery("SELECT o FROM Order o ORDER BY o.orderNum", Order.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(orders);
		// when
		List<Order> actualOrders = orderDAO.getAllOrders();
		// then
		assertEquals(actualOrders.size(), orders.size());

	}

	@SuppressWarnings("unchecked")
	@Test
	public void test_4_ShouldGetAllOrdersByUserId() {
		// given
		String username = "rchauhan9102@gmail.com";
		Path<Object> user = mock(Path.class);
		Path<Object> userName = mock(Path.class);
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		when(session.getCriteriaBuilder()).thenReturn(builder);
		when(builder.createQuery(Order.class)).thenReturn(orderQuery);
		when(orderQuery.from(Order.class)).thenReturn(root);
		when(session.createQuery(orderQuery)).thenReturn(orderByUsernameQuery);
		when(orderQuery.select(root)).thenReturn(orderQuery);
		when(root.get("user")).thenReturn(user);
		when(user.get("username")).thenReturn(userName);
		when(orderByUsernameQuery.getResultList()).thenReturn(orders.stream()
				.filter(o -> o.getUser().getUsername().compareTo(username) == 0).collect(Collectors.toList()));
		// when
		List<Order> actualOrders = orderDAO.getAllOrdersByUserId(username);
		// then
		assertEquals(actualOrders.size(), 1);

	}

}
