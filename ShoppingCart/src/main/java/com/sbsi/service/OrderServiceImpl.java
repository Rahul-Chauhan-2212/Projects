package com.sbsi.service;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbsi.dao.OrderDao;
import com.sbsi.entity.Order;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Transactional
	@Override
	public void createNewOrder(Order order) {
		orderDao.createNewOrder(order);
	}

	@Transactional
	@Override
	public BigInteger getMaxOrderNum() {
		return orderDao.getMaxOrderNum();
	}

	@Transactional
	@Override
	public List<Order> getAllOrders() {
		return orderDao.getAllOrders();
	}

	@Transactional
	@Override
	public List<Order> getAllOrdersByUserId(String username) {
		return orderDao.getAllOrdersByUserId(username);
	}

}
