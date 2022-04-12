package com.sbsi.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sbsi.entity.Cart;
import com.sbsi.entity.CartProducts;

@Repository
public class CartDaoImpl implements CartDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addProductToCart(Cart cart) {
		sessionFactory.getCurrentSession().saveOrUpdate(cart);
	}

	@Override
	public void removeProductFromCart(Cart cart) {
		sessionFactory.getCurrentSession().merge(cart);
	}

	@Override
	public Integer getCartSize(String username) {
		Cart cart = getCartByUsername(username);
		if (Optional.ofNullable(cart).isPresent()) {
			return cart.getCartProducts().size();
		}
		return 0;
	}

	@Override
	public Cart getCartByUsername(String username) {
		return sessionFactory.getCurrentSession().get(Cart.class, username);
	}

	@Override
	public List<CartProducts> getCartProducts(String username) {
		Cart cart = getCartByUsername(username);
		return cart.getCartProducts().stream().collect(Collectors.toList());
	}

	@Override
	public BigDecimal getCartTotal(String username) {
		Cart cart = getCartByUsername(username);
		return cart.getTotalprice();
	}

	@Override
	public void updateProductQuantityInCart(Cart cart) {
		sessionFactory.getCurrentSession().merge(cart);
	}

	@Override
	public Cart deleteCartDetails(Cart cart) {
		return (Cart) sessionFactory.getCurrentSession().merge(cart);
	}

}
