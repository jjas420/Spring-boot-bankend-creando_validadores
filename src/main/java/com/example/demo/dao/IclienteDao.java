/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.demo.dao;

import com.example.demo.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ayosu
 */

public interface IclienteDao extends CrudRepository<Cliente,Long>  {
     
    
    
}
