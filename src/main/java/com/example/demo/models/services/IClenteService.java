/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.demo.models.services;

import com.example.demo.models.entity.Cliente;
import java.util.List;

/**
 *
 * @author ayosu
 */
public interface IClenteService {
    public List<Cliente> findAll();
    public Cliente findById(long id);
    public Cliente save(Cliente cliente);
    public void delete(long id);
    
    
}
