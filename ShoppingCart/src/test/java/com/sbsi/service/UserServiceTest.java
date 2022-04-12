package com.sbsi.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.sbsi.dao.UserDao;
import com.sbsi.entity.Role;
import com.sbsi.entity.User;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserServiceImpl userservice;

	@Mock
	private UserDao userDao;

	private List<User> users;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		users = new ArrayList<>();
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		Role role1 = new Role();
		role1.setRoleType("USER");
		Role role2 = new Role();
		role2.setRoleType("ADMIN");
		user1.setUsername("klrahul@gmail.com");
		user1.setFirstName("KL");
		user1.setLastName("Rahul");
		user1.setFullName(user1.getFirstName() + " " + user1.getLastName());
		user1.setEnabled(true);
		user1.setContactNo("7777777777");
		Set<Role> roles1 = new HashSet<>();
		roles1.add(role1);
		user1.setRoles(roles1);
		user2.setUsername("rohitsharma@gmail.com");
		user2.setFirstName("Rohit");
		user2.setLastName("Sharma");
		user2.setFullName(user2.getFirstName() + " " + user2.getLastName());
		user2.setEnabled(true);
		user2.setContactNo("652838292");
		Set<Role> roles2 = new HashSet<>();
		roles2.add(role2);
		user2.setRoles(roles2);
		user3.setUsername("viratkohli@gmail.com");
		user3.setFirstName("Virat");
		user3.setLastName("Kohli");
		user3.setFullName(user3.getFirstName() + " " + user3.getLastName());
		user3.setEnabled(true);
		user3.setContactNo("2628272927");
		Set<Role> roles3 = new HashSet<>();
		roles3.add(role1);
		roles3.add(role2);
		user3.setRoles(roles3);
		users.add(user1);
		users.add(user2);
		users.add(user2);
	}

	@Test
	public void testShouldCreateUserAccount() {
		// given
		User expectedUser = new User();
		expectedUser.setUsername("rchauhan1401@gmail.com");
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
		given(userDao.createUserAccount(expectedUser)).willReturn(users.add(expectedUser));
		// when
		boolean actualResult = userservice.createUserAccount(expectedUser);
		// then
		assertEquals(true, actualResult);
		assertEquals(expectedUser, users.get(3));
	}

	@Test
	public void testShouldGetAllUsers() {
		// given
		when(userDao.getAllUsers()).thenReturn(users);
		// when
		List<User> actualUsers = userservice.getAllUsers();
		// then
		assertEquals(actualUsers, users);
	}

	@Test
	public void testShouldAssignAdminRoleToUser() {
		// given
		String expectedRole = "ADMIN";
		User user = users.get(0);
		Role role = new Role();
		role.setRoleType("ADMIN");
		user.getRoles().add(role);
		doAnswer((i) -> {
			users = users.stream().map(u -> {
				if (u.getUsername().compareTo(((User) i.getArgument(0)).getUsername()) == 0) {
					u.getRoles().add(role);
				}
				return u;
			}).collect(Collectors.toList());
			return null;
		}).when(userDao).assignAdminRole(any(User.class));
		// when
		userservice.assignAdminRole(user);
		// then
		Role actualRole = users.get(0).getRoles().stream().filter(r -> r.getRoleType().compareTo("ADMIN") == 0)
				.findFirst().get();
		assertEquals(expectedRole, actualRole.getRoleType());
	}

	@Test
	public void testShouldDisableUser() {
		// given
		User user = users.get(0);
		user.setEnabled(false);
		doAnswer((i) -> {
			users = users.stream().map(u -> {
				if (u.getUsername().compareTo(((User) i.getArgument(0)).getUsername()) == 0) {
					u.setEnabled(false);
				}
				return u;
			}).collect(Collectors.toList());
			return null;
		}).when(userDao).disableUser(any(User.class));
		// when
		userservice.disableUser(user);
		// then
		assertEquals(user.isEnabled(), users.get(0).isEnabled());

	}

	@Test
	public void testShouldDeleteUser() {
		// given
		User user = users.get(0);
		doAnswer((i) -> {
			users = users.stream().filter(u -> u.getUsername().compareTo(((User) i.getArgument(0)).getUsername()) != 0)
					.collect(Collectors.toList());
			return null;
		}).when(userDao).deleteUser(any(User.class));
		// when
		userservice.deleteUser(user);
		// then
		assertEquals(2, users.size());

	}

	@After
	public void destroy() {
		users.clear();
	}

}
