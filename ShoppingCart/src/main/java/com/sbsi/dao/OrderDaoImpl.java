package com.sbsi.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sbsi.entity.Order;

@Repository
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void createNewOrder(Order order) {
		sessionFactory.getCurrentSession().save(order);
	}

	@Override
	public BigInteger getMaxOrderNum() {
		BigInteger max = null;
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<BigInteger> query = builder.createQuery(BigInteger.class);
		Root<Order> root = query.from(Order.class);
		query.select(builder.max(root.get("orderNum")));
		max = (BigInteger) sessionFactory.getCurrentSession().createQuery(query).getSingleResult();
		return max;
	}

	@Override
	public List<Order> getAllOrders() {
		return sessionFactory.getCurrentSession().createQuery("SELECT o FROM Order o ORDER BY o.orderNum", Order.class)
				.getResultList();
	}

	@Override
	public List<Order> getAllOrdersByUserId(String username) {
		CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Order> query = builder.createQuery(Order.class);
		Root<Order> root = query.from(Order.class);
		query.select(root).where(builder.equal(root.get("user").get("username"), username));
		return sessionFactory.getCurrentSession().createQuery(query).getResultList();

	}

}
