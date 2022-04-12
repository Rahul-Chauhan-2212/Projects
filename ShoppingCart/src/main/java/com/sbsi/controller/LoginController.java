package com.sbsi.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.sbsi.config.UserDetailsImpl;
import com.sbsi.entity.Role;
import com.sbsi.entity.User;

@Component
public class LoginController implements Serializable {

	private static final long serialVersionUID = 3141228343778908999L;

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	private User user = new User();

	@Autowired
	private AuthenticationManager authenticationManager;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String validateUserLogin() {
		FacesMessage message = null;
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(this.user.getUsername(), this.user.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetailsImpl) {
				user = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
						.getUser();
			}
			user.setPassword(null);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", user.getUsername());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
			clearLoginForm();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
				LOGGER.info("Login Successfull!!!");
				return "admin";
			}
			LOGGER.info("Login Successfull!!!");
			return "user";
		} catch (UsernameNotFoundException e) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username not found", null);
		} catch (BadCredentialsException e) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong Password", null);
		} catch (DisabledException e) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User is disabled", null);
		} catch (AuthenticationException e) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to authenticate", null);
		}
		if (message != null) {
			FacesContext.getCurrentInstance().validationFailed();
			FacesContext.getCurrentInstance().addMessage("loginForm", message);
		}
		return "";
	}

	public String logout() {
		SecurityContextHolder.clearContext();
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.getSessionMap().clear();
		externalContext.invalidateSession();
		try {
			externalContext.redirect(externalContext.encodeResourceURL("index.xhtml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public boolean hasRole(String role) {
		User user1 = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		for (Role r : user1.getRoles()) {
			if (r.getRoleType().compareTo(role) == 0) {
				return true;
			}
		}
		return false;
	}

	public void clearLoginForm() {
		user = new User();
	}

}
