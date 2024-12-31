/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.models.services;

import com.example.demo.dao.IclienteDao;
import com.example.demo.dao.UserDao;
import com.example.demo.models.entity.Cliente;
import com.example.demo.models.entity.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ayosu
 */
@Service
public class UserServiceIMplement implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(long id) {
        return userDao.findById(id).orElse(null);
    }
    @Override
    @Transactional
    public User save(User user) {
        return userDao.save(user);
        
    }
    
    @Override
    public void delete(long id) {
        userDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByName(String name) {
        return userDao.findByNombre(name);

    }
}
