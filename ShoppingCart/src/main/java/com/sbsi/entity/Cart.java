package com.sbsi.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CART")
public class Cart implements Serializable {

	private static final long serialVersionUID = 1440879950762419105L;

	@Id
	@Column(name = "CART_ID")
	private String cartId;

	@OneToOne
	@MapsId
	@JoinColumn(name = "CART_ID")
	private User user;

	@OneToMany(mappedBy = "cartProductsId.cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<CartProducts> cartProducts = new HashSet<>();

	@Column(name = "TOTAL_PRICE", precision = 10, scale = 2)
	private BigDecimal totalprice;

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<CartProducts> getCartProducts() {
		return cartProducts;
	}

	public void setCartProducts(Set<CartProducts> cartProducts) {
		this.cartProducts = cartProducts;
	}

	public void addCartProducts(CartProducts cartProducts) {
		this.cartProducts.add(cartProducts);
	}

	public void removeCartProducts(CartProducts cartProducts) {
		this.cartProducts.remove(cartProducts);
	}

	public BigDecimal getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(BigDecimal totalprice) {
		this.totalprice = totalprice;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cartId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		return Objects.equals(cartId, other.cartId);
	}

}
