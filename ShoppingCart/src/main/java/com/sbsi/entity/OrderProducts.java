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
@Table(name = "ORDER_PRODUCTS")
@AssociationOverrides({
		@AssociationOverride(name = "orderProductsId.order", joinColumns = @JoinColumn(name = "ORDER_NUM")),
		@AssociationOverride(name = "orderProductsId.product", joinColumns = @JoinColumn(name = "P_CODE")) })
public class OrderProducts implements Serializable {

	private static final long serialVersionUID = 67947922546263717L;

	@EmbeddedId
	private OrderProductsId orderProductsId = new OrderProductsId();

	@Column(name = "PRODUCT_QNTTY", nullable = false, precision = 5, scale = 0)
	private BigInteger productQntty;

	@Column(name = "SUB_TOTAL", nullable = false, precision = 10, scale = 2)
	private BigDecimal subTotal;

	public OrderProductsId getOrderProductsId() {
		return orderProductsId;
	}

	public void setOrderProductsId(OrderProductsId orderProductsId) {
		this.orderProductsId = orderProductsId;
	}

	@Transient
	public Order getOrder() {
		return getOrderProductsId().getOrder();
	}

	public void setOrder(Order order) {
		getOrderProductsId().setOrder(order);
	}

	@Transient
	public Product getProduct() {
		return getOrderProductsId().getProduct();
	}

	public void setProduct(Product product) {
		getOrderProductsId().setProduct(product);
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
		return Objects.hash(orderProductsId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderProducts other = (OrderProducts) obj;
		return Objects.equals(orderProductsId, other.orderProductsId);
	}

}