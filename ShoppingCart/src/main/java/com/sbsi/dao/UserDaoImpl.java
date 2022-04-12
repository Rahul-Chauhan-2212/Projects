package com.sbsi.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sbsi.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean createUserAccount(User user) {
		Serializable id = sessionFactory.getCurrentSession().save(user);
		LOGGER.info("User created with id--> " + id);
		return true;
	}

	@Override
	public List<User> getAllUsers() {
		return sessionFactory.getCurrentSession().createQuery("SELECT u FROM User u", User.class).getResultList();
	}

	@Override
	public void assignAdminRole(User u) {
		sessionFactory.getCurrentSession().saveOrUpdate(u);
	}

	@Override
	public void disableUser(User u) {
		sessionFactory.getCurrentSession().saveOrUpdate(u);
	}

	@Override
	public void deleteUser(User u) {
		sessionFactory.getCurrentSession().delete(u);
	}

}
