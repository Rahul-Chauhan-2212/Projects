package com.sbsi.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class CartProductsId implements Serializable {

	private static final long serialVersionUID = 7795655420567860813L;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Cart cart;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Product product;

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cart.getCartId(), product.getPrdctCd());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartProductsId other = (CartProductsId) obj;
		return Objects.equals(cart.getCartId(), other.cart.getCartId())
				&& Objects.equals(product.getPrdctCd(), other.product.getPrdctCd());
	}

}