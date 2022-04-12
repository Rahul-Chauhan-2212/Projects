package com.sbsi.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sbsi.entity.User;

@Repository
public class UserDetailsDaoImpl implements UserDetailsDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User findUserByUsername(String username) {
		User user = sessionFactory.getCurrentSession().get(User.class, username);
		return user;
	}
}