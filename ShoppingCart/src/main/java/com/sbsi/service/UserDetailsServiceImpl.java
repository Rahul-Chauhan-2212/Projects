package com.sbsi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbsi.config.UserDetailsImpl;
import com.sbsi.dao.UserDetailsDao;
import com.sbsi.entity.User;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDetailsDao userDetailsDao;

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDetailsDao.findUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Could not find user");
		}
		return new UserDetailsImpl(user);
	}
}