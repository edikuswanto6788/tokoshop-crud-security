/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exam7.tokoshop.controller;

import com.exam7.tokoshop.entity.User;
import com.exam7.tokoshop.model.Login;
import com.exam7.tokoshop.model.Registration;
import com.exam7.tokoshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edi
 */
@RestController
@RequestMapping("/api")

public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    
    private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<String> processUserLogin(
            @RequestBody Login login) {
        //get user by username to query from user
        User user = userRepository.getUserByName(login.getUserName());
        if (user != null && user.getPassword().equals(login.getPassword())) {
            return ResponseEntity.ok("Login Berhasil");
        }
        return ResponseEntity.ok("Username dan Password Salah");

    }
    
    @PostMapping ("/register")
    public ResponseEntity<String> processRegistration(
            @RequestBody Registration reg){
        User user = new User();
        user.setName(reg.getUserName());
        user.setPassword(bcrypt.encode(reg.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Berhasil Registration");
    }
}
