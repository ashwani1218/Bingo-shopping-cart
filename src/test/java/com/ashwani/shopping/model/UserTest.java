package com.ashwani.shopping.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

	User user;
	
	@BeforeEach
	void setUp() throws Exception {
		user = new User();
	
	}
	

	@Test
	void testGetId() {
		Long id=1L;
		user.setId(id);
		assertEquals(id, user.getId());
	}

	@Test
	void testGetPassword() {
		String password="Pass";
		user.setPassword(password);
		assertEquals(password,user.getPassword());
	}

	@Test
	void testGetUsername() {
		String userName="Username";
		user.setUsername(userName);
		assertEquals(userName, user.getUsername());
	}
}
