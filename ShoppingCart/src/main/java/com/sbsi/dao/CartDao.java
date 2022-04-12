package com.sbsi.dao;

import java.math.BigDecimal;
import java.util.List;

import com.sbsi.entity.Cart;
import com.sbsi.entity.CartProducts;

public interface CartDao {

	public void addProductToCart(Cart cart);

	public void removeProductFromCart(Cart cart);

	public Integer getCartSize(String username);

	public Cart getCartByUsername(String username);

	public List<CartProducts> getCartProducts(String username);

	public BigDecimal getCartTotal(String username);

	public void updateProductQuantityInCart(Cart cart);

	public Cart deleteCartDetails(Cart cart);

}
