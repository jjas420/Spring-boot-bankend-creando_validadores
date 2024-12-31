/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.demo.models.services;

import com.example.demo.models.entity.User;
import java.util.List;

/**
 *
 * @author ayosu
 */
public interface IUserService {
       public List<User> findAll();
    public User findById(long id);
    public User save(User user);
    public void delete(long id);
      public User findByName(String name);
}
