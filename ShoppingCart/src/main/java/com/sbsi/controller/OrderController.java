package com.sbsi.controller;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbsi.entity.Cart;
import com.sbsi.entity.Order;
import com.sbsi.entity.OrderProducts;
import com.sbsi.service.CartService;
import com.sbsi.service.OrderService;

@Component
public class OrderController implements Serializable {

	private static final long serialVersionUID = 1681465219145473019L;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private CartService cartService;

	private Order order = new Order();

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String createOrder(String username) {
		LOGGER.info("Going to create Order");
		Cart cart = cartService.getCartByUsername(username);
		this.order.setUser(cart.getUser());
		this.order.setOrderDate(new Date());
		this.order.setOrderNum(getMaxOrderNum().add(BigInteger.ONE));
		this.order.setAmount(cart.getTotalprice());
		this.order.setOrderProducts(copyCartProductsToOrderProducts(this.order, cart));
		try {
			orderService.createNewOrder(order);
			LOGGER.info("Order Created Successfully.");
			cartService.deleteCartDetails(cart);
			LOGGER.info("Details Remove from Cart successfully.");
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastOrder", order);
			clearOrderCheckoutForm();
		} catch (Exception e) {
			LOGGER.error("Error occured while creating an order." + e.getMessage());
			return "checkout";
		}
		return "orderConfirm.xhtml?faces-redirect=true";

	}

	public Set<OrderProducts> copyCartProductsToOrderProducts(Order order, Cart cart) {
		Set<OrderProducts> orderProducts = cart.getCartProducts().stream().map(cp -> {
			OrderProducts op = new OrderProducts();
			op.setProductQntty(cp.getProductQntty());
			op.setSubTotal(cp.getSubTotal());
			op.setProduct(cp.getProduct());
			op.setOrder(order);
			return op;
		}).collect(Collectors.toSet());
		return orderProducts;

	}

	public BigInteger getMaxOrderNum() {
		return orderService.getMaxOrderNum();
	}

	public String getOrderListPage() {
		return "orderList.xhtml?faces-redirect=true";
	}

	public List<Order> getAllOrders() {
		return orderService.getAllOrders();
	}

	public List<Order> getAllOrdersByUserId(String username) {
		return orderService.getAllOrdersByUserId(username);
	}

	public void clearOrderCheckoutForm() {
		order = new Order();
	}

}