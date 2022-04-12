package com.sbsi.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "CART_PRODUCTS")
@AssociationOverrides({ @AssociationOverride(name = "cartProductsId.cart", joinColumns = @JoinColumn(name = "CART_ID")),
		@AssociationOverride(name = "cartProductsId.product", joinColumns = @JoinColumn(name = "P_CODE")) })
public class CartProducts implements Serializable {

	private static final long serialVersionUID = 2505166703281105800L;

	@EmbeddedId
	private CartProductsId cartProductsId = new CartProductsId();

	@Column(name = "PRODUCT_QNTTY", nullable = false, precision = 5, scale = 0)
	private BigInteger productQntty;

	@Column(name = "SUB_TOTAL", nullable = false, precision = 10, scale = 2)
	private BigDecimal subTotal;

	public CartProductsId getCartProductsId() {
		return cartProductsId;
	}

	public void setCartProductsId(CartProductsId cartProductsId) {
		this.cartProductsId = cartProductsId;
	}

	@Transient
	public Cart getCart() {
		return getCartProductsId().getCart();
	}

	public void setCart(Cart cart) {
		getCartProductsId().setCart(cart);
	}

	@Transient
	public Product getProduct() {
		return getCartProductsId().getProduct();
	}

	public void setProduct(Product product) {
		getCartProductsId().setProduct(product);
	}

	public BigInteger getProductQntty() {
		return productQntty;
	}

	public void setProductQntty(BigInteger productQntty) {
		this.productQntty = productQntty;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cartProductsId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartProducts other = (CartProducts) obj;
		return Objects.equals(cartProductsId, other.cartProductsId);
	}

}