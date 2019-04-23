package com.ashwani.shooping.service;

import com.ashwani.shopping.model.Role;
import com.ashwani.shopping.model.User;
import com.ashwani.shopping.repository.RoleRepository;
import com.ashwani.shopping.repository.UserRepository;
import com.ashwani.shopping.service.impl.UserServiceImp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class UserServiceImpTest {

    private UserServiceImp userServiceImp;

    private UserRepository mockUserRepository;

    private RoleRepository mockRoleRepository;

    private BCryptPasswordEncoder mockPasswordEncoder;

    @Before
    public void setup(){
        mockUserRepository = Mockito.mock(UserRepository.class);
        mockRoleRepository = Mockito.mock(RoleRepository.class);
        mockPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        userServiceImp = new UserServiceImp(mockUserRepository, mockRoleRepository, mockPasswordEncoder);
    }

    @Test
    public void findByUsername_shouldReturn_user() {

        User mockUser = new User();
        mockUser.setUsername("ashwani");
        Optional<User> optionalMockUser = Optional.of(mockUser);

        Mockito.when(mockUserRepository.findByUsername("ashwani")).thenReturn(optionalMockUser);

        String testUserName = "ashwani";
        Optional<User> actualUser =  userServiceImp.findByUsername(testUserName);

        //Check of we did not get null
        Assert.assertNotNull(actualUser);
        Assert.assertNotNull(actualUser.get());
        User user = actualUser.get();
        //check if we got the user that we wanted
        Assert.assertEquals(testUserName, user.getUsername());
        Mockito.verify(mockUserRepository, Mockito.times(1)).findByUsername(mockUser.getUsername());
    }

    @Test
    public void findByEmail_shouldReturn_user() {

        User mockUser = new User();
        mockUser.setEmail("ashwani@gmail.com");
        Optional<User> optionalMockUser = Optional.of(mockUser);

        Mockito.when(mockUserRepository.findByEmail("ashwani@gmail.com")).thenReturn(optionalMockUser);

        String testEmail = "ashwani@gmail.com";
        Optional<User> actualUser =  userServiceImp.findByEmail(testEmail);

        //Check of we did not get null
        Assert.assertNotNull(actualUser);
        Assert.assertNotNull(actualUser.get());
        User user = actualUser.get();
        //check if we got the user that we wanted
        Assert.assertEquals(testEmail, user.getEmail());
        Mockito.verify(mockUserRepository, Mockito.times(1)).findByEmail(mockUser.getEmail());
    }

    @Test
    public void saveUser_should_saveUser() {

        User mockUser = new User();
        mockUser.setUsername("ashwani");
        mockUser.setEmail("ashwani@gmail.com");
        mockUser.setPassword("testPassword");

        Mockito.when(mockRoleRepository.findByRole("ROLE_USER")).thenReturn(new Role());
        Mockito.when(mockUserRepository.saveAndFlush(mockUser)).thenReturn(mockUser);
        Mockito.when(mockPasswordEncoder.encode(Mockito.anyString())).thenReturn("encryptedPassword");

        User actualUser = userServiceImp.saveUser(mockUser);

        Assert.assertEquals("ashwani@gmail.com", actualUser.getEmail());
        Assert.assertEquals("ashwani", actualUser.getUsername());
        Mockito.verify(mockUserRepository, Mockito.times(1)).saveAndFlush(mockUser);
        Mockito.verify(mockRoleRepository, Mockito.times(1)).findByRole("ROLE_USER");
        Mockito.verify(mockPasswordEncoder, Mockito.times(1)).encode("testPassword");
    }
}
