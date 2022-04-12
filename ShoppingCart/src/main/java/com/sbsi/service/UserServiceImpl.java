package com.sbsi.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbsi.dao.UserDao;
import com.sbsi.entity.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDoa;

	@Transactional
	@Override
	public boolean createUserAccount(User user) {
		return userDoa.createUserAccount(user);
	}

	@Transactional
	@Override
	public List<User> getAllUsers() {
		return userDoa.getAllUsers();
	}

	@Transactional
	@Override
	public void assignAdminRole(User u) {
		userDoa.assignAdminRole(u);
	}

	@Transactional
	@Override
	public void disableUser(User u) {
		userDoa.disableUser(u);
	}

	@Transactional
	@Override
	public void deleteUser(User u) {
		userDoa.deleteUser(u);
	}

}
