/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.models.services;


import com.example.demo.models.entity.Cliente;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author ayosu
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
     @Autowired
    private IUserService iUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          // Call Database to validate
         com.example.demo.models.entity.User  userModel= this.iUserRepository.findByName(username);
        if(userModel == null) {
            throw  new UsernameNotFoundException(username);
        }
        return new User(userModel.getName(), userModel.getPassword(), new ArrayList<>());
    }
    
}
