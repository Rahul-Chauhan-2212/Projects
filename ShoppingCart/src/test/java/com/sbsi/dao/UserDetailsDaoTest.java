package com.sbsi.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.sbsi.entity.Role;
import com.sbsi.entity.User;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsDaoTest {

	@InjectMocks
	private UserDetailsDaoImpl userDetailsDao;

	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Session session;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
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
		given(session.get(User.class, username)).willReturn(expectedUser);
		// when
		User actualUser = userDetailsDao.findUserByUsername(username);
		// then
		assertEquals(expectedUser, actualUser);

	}

}
