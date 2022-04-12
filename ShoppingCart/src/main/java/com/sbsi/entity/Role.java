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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ROLE")
public class Role implements Serializable {

	private static final long serialVersionUID = 8284610183455464379L;

	@Id
	@Column(name = "ROLE_TYPE")
	private String roleType;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.DETACH }, fetch = FetchType.LAZY, mappedBy = "roles")
	private Set<User> users = new HashSet<>();

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		return Objects.hash(roleType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return Objects.equals(roleType, other.roleType);
	}

}