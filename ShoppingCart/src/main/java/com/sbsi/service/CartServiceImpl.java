package com.sbsi.service;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbsi.dao.CartDao;
import com.sbsi.entity.Cart;
import com.sbsi.entity.CartProducts;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartDao cartDao;

	@Transactional
	@Override
	public void addProductToCart(Cart cart) {
		cartDao.addProductToCart(cart);
	}

	@Transactional
	@Override
	public void removeProductFromCart(Cart cart) {
		cartDao.removeProductFromCart(cart);
	}

	@Transactional
	@Override
	public Integer getCartSize(String username) {
		return cartDao.getCartSize(username);
	}

	@Transactional
	@Override
	public Cart getCartByUsername(String username) {
		return cartDao.getCartByUsername(username);
	}

	@Transactional
	@Override
	public List<CartProducts> getCartProducts(String username) {
		return cartDao.getCartProducts(username);
	}

	@Transactional
	@Override
	public BigDecimal getCartTotal(String username) {
		return cartDao.getCartTotal(username);
	}

	@Transactional
	@Override
	public void updateProductQuantityInCart(Cart cart) {
		cartDao.updateProductQuantityInCart(cart);
	}

	@Transactional
	@Override
	public Cart deleteCartDetails(Cart cart) {
		cart.setTotalprice(BigDecimal.ZERO);
		cart.getCartProducts().clear();
		return cartDao.deleteCartDetails(cart);
	}

}
