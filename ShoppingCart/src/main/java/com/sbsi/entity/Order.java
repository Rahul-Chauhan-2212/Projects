package com.sbsi.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {

	private static final long serialVersionUID = -5025056939838465609L;

	@Id
	@Column(name = "ORDER_NUM", precision = 5, scale = 0)
	private BigInteger orderNum;

	@Column(name = "ORDER_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;

	@Column(name = "ORDER_AMOUNT", nullable = false, precision = 10, scale = 2)
	private BigDecimal amount;

	@Column(name = "CUSTOMER_NAME", nullable = false)
	private String customerName;

	@Embedded
	private Address customerAddress = new Address();

	@Column(name = "CUSTOMER_EMAIL", nullable = false)
	private String customerEmail;

	@Column(name = "CUSTOMER_PHONE", nullable = false)
	private String customerPhone;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_NAME", nullable = false)
	private User user;

	@OneToMany(mappedBy = "orderProductsId.order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<OrderProducts> orderProducts = new HashSet<>();

	public BigInteger getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(BigInteger orderNum) {
		this.orderNum = orderNum;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Address getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(Address customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<OrderProducts> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(Set<OrderProducts> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public void addOrderProducts(OrderProducts orderProducts) {
		this.orderProducts.add(orderProducts);
	}

	public void removeOrderProducts(OrderProducts orderProducts) {
		this.orderProducts.remove(orderProducts);
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderNum);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return orderNum == other.orderNum;
	}

}
