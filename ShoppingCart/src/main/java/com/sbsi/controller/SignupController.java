package com.sbsi.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.sbsi.entity.Cart;
import com.sbsi.entity.Role;
import com.sbsi.entity.User;
import com.sbsi.service.UserService;

@Component
public class SignupController implements Serializable {

	private static final long serialVersionUID = 4631800474067512510L;

	private static final Logger LOGGER = LoggerFactory.getLogger(SignupController.class);

	private User user = new User();

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	private UserService userService;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String createUserAccount() {
		Role role = new Role();
		role.setRoleType("USER");
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		user.setEnabled(true);
		user.setFullName(user.getFirstName() + " " + user.getLastName());
		user.setPassword(encoder.encode(user.getPassword()));
		Cart cart = new Cart();
		user.setCart(cart);
		cart.setUser(user);
		cart.setTotalprice(BigDecimal.ZERO);
		try {
			userService.createUserAccount(user);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			FacesContext.getCurrentInstance().addMessage("signup",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "User Created Successfully!!! Please Log in", null));
			externalContext.getFlash().setKeepMessages(true);
			clearSignUpForm();
			externalContext.redirect(externalContext.encodeResourceURL("index.xhtml"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			FacesContext.getCurrentInstance().addMessage("signup",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Occured while creating an User", null));
		}
		return "";
	}

	public void clearSignUpForm() {
		user = new User();
	}

}
