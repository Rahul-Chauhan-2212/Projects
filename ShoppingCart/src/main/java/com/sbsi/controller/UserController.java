package com.sbsi.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.NonUniqueObjectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbsi.entity.Role;
import com.sbsi.entity.User;
import com.sbsi.service.UserService;

@Component
public class UserController implements Serializable {
	private static final long serialVersionUID = -8663770040731825924L;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	public String getUserListPage() {
		return "users.xhtml?faces-redirect=true";
	}

	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	public String assignAdminRole(User u) {
		Role role = new Role();
		role.setRoleType("ADMIN");
		u.getRoles().add(role);
		try {
			userService.assignAdminRole(u);
			LOGGER.info("User Assigned Admin Role");
		} catch (NonUniqueObjectException e) {
			FacesContext.getCurrentInstance().addMessage("assignAdminRole",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Already an Admin User", null));
			LOGGER.error("Error Occured while assigning admin role to user " + e.getMessage());

		}
		return "users.xhtml";
	}

	public String disableUser(User u) {
		if (!u.isEnabled()) {
			FacesContext.getCurrentInstance().addMessage("disableUser",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "User is already disabled", null));
			return "users.xhtml";
		}
		u.setEnabled(false);
		userService.disableUser(u);
		return "users.xhtml";
	}

	public String deleteUser(User u) {
		try {
			userService.deleteUser(u);
			LOGGER.info("User is successfully deleted-->" + u.getUsername());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("deleteUser",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Occured while deleting the user", null));
			LOGGER.error("Some error occured while deleting an user");
		}
		return "users.xhtml";
	}

}
