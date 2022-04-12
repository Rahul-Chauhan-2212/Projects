package com.sbsi.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class OrderProductsId implements Serializable {

	private static final long serialVersionUID = -3859350623527888888L;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Order order;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Product product;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		return Objects.hash(order.getOrderNum(), product.getPrdctCd());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderProductsId other = (OrderProductsId) obj;
		return Objects.equals(order.getOrderNum(), other.order.getOrderNum())
				&& Objects.equals(product.getPrdctCd(), other.product.getPrdctCd());
	}

}