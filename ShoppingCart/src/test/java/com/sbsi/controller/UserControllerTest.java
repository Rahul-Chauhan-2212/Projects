package com.sbsi.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.sbsi.entity.Role;
import com.sbsi.entity.User;
import com.sbsi.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

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
	public void testShouldGetAllUsers() {
		// given
		when(userService.getAllUsers()).thenReturn(users);
		// when
		List<User> actualUsers = userController.getAllUsers();
		// then
		assertEquals(actualUsers, users);

	}

}
