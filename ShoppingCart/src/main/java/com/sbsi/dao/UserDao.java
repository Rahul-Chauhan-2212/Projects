package com.sbsi.dao;

import java.util.List;

import com.sbsi.entity.User;

public interface UserDao {

	public boolean createUserAccount(User user);

	public List<User> getAllUsers();

	public void assignAdminRole(User u);

	public void disableUser(User u);

	public void deleteUser(User u);

}
