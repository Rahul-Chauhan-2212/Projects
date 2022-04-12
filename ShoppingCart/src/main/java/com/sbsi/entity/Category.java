package com.sbsi.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT_CATEGORY")
public class Category implements Serializable {

	private static final long serialVersionUID = -7159760559662649738L;

	@Id
	@Column(name = "C_CODE")
	private String ctgryCd;

	@Column(name = "CATEGORY", nullable = false)
	private String ctgry;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "category")
	private Set<Product> products = new HashSet<>();

	public String getCtgryCd() {
		return ctgryCd;
	}

	public void setCtgryCd(String ctgryCd) {
		this.ctgryCd = ctgryCd;
	}

	public String getCtgry() {
		return ctgry;
	}

	public void setCtgry(String ctgry) {
		this.ctgry = ctgry;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ctgryCd);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(ctgryCd, other.ctgryCd);
	}

}
