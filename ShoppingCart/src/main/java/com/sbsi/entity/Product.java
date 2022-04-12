package com.sbsi.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT")
public class Product implements Serializable {

	private static final long serialVersionUID = 5027374784654535178L;

	@Id
	@Column(name = "P_CODE")
	private String prdctCd;

	@Column(name = "NAME", nullable = false)
	private String prdctName;

	@Column(name = "DESCRIPTION", nullable = false)
	private String prdctDesc;

	@Column(name = "AVLBL_QNTTY", nullable = false, precision = 5, scale = 0)
	private BigInteger avlblQnnty;

	@Column(name = "PRICE", nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@Lob
	@Column(name = "image", nullable = false)
	private byte[] image;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "C_CODE", nullable = false)
	private Category category;

	@OneToMany(mappedBy = "cartProductsId.product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<CartProducts> cartProducts = new HashSet<>();

	@OneToMany(mappedBy = "orderProductsId.product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<OrderProducts> orderProducts = new HashSet<>();

	public String getPrdctCd() {
		return prdctCd;
	}

	public void setPrdctCd(String prdctCd) {
		this.prdctCd = prdctCd;
	}

	public String getPrdctName() {
		return prdctName;
	}

	public void setPrdctName(String prdctName) {
		this.prdctName = prdctName;
	}

	public String getPrdctDesc() {
		return prdctDesc;
	}

	public void setPrdctDesc(String prdctDesc) {
		this.prdctDesc = prdctDesc;
	}

	public BigInteger getAvlblQnnty() {
		return avlblQnnty;
	}

	public void setAvlblQnnty(BigInteger avlblQnnty) {
		this.avlblQnnty = avlblQnnty;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

	public Set<OrderProducts> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(Set<OrderProducts> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public void addOrderProducts(OrderProducts orderProducts) {
		this.orderProducts.add(orderProducts);
	}

	@Override
	public int hashCode() {
		return Objects.hash(prdctCd);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(prdctCd, other.prdctCd);
	}

}
