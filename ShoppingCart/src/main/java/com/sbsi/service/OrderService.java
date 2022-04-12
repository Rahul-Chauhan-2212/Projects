package com.sbsi.service;

import java.math.BigInteger;
import java.util.List;

import com.sbsi.entity.Order;

public interface OrderService {

	public void createNewOrder(Order order);

	public BigInteger getMaxOrderNum();

	public List<Order> getAllOrders();

	public List<Order> getAllOrdersByUserId(String username);

}
