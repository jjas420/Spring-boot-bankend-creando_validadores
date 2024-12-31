/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.demo.dao;

import com.example.demo.models.entity.Cliente;
import com.example.demo.models.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ayosu
 */
public interface UserDao extends CrudRepository<User,Long> {
 @Query(value = "SELECT * FROM user WHERE nombre = ?1", nativeQuery = true)
User  findByNombre(String nombre);
    
    
}
