package com.sbsi.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import com.sbsi.dao.UserDetailsDao;
import com.sbsi.entity.Role;
import com.sbsi.entity.User;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceTest {

	@InjectMocks
	private UserDetailsServiceImpl userDetailsService;

	@Mock
	private UserDetailsDao userDetailsDao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testShouldFindUserByName() {
		// given
		String username = "rahul";
		User expectedUser = new User();
		expectedUser.setUsername(username);
		expectedUser.setFirstName("Rahul");
		expectedUser.setLastName("Chauhan");
		expectedUser.setFullName(expectedUser.getFirstName() + " " + expectedUser.getLastName());
		expectedUser.setEnabled(true);
		expectedUser.setContactNo("7037099102");
		Set<Role> roles = new HashSet<>();
		Role role = new Role();
		role.setRoleType("USER");
		roles.add(role);
		expectedUser.setRoles(roles);
		given(userDetailsDao.findUserByUsername(username)).willReturn(expectedUser);
		// when
		UserDetails actualUserDetails = userDetailsService.loadUserByUsername(username);
		// then
		assertEquals(expectedUser.getUsername(), actualUserDetails.getUsername());

	}

}
