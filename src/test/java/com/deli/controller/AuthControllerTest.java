package com.deli.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deli.repository.UserRepository;

public class AuthControllerTest {

    @Autowired
    AuthController authController;

    @Autowired
    UserRepository userRepository;
    
    @Test
    void testLoginUser() {

    }

    @Test
    void testRegisterUser() {
    }
}
