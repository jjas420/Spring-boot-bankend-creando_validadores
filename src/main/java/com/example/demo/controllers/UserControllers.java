/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controllers;

import com.example.demo.models.entity.Cliente;
import com.example.demo.models.entity.User;
import com.example.demo.models.services.IUserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ayosu
 */
@RestController
@RequestMapping("/api")
public class UserControllers {
     @Autowired
    private IUserService userservice;
     
         @Autowired
    private BCryptPasswordEncoder passwordEncoder;
     
     @GetMapping("/users")
    public List<User> listausuarios() {
        return userservice.findAll();

    }
    
    
     @GetMapping("/users/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        User user = null;
        Map<String, Object> response = new HashMap<>();

        try {
            user = userservice.findById(id);

        } catch (DataAccessException e) {
            response.put("mensaje", "error al realizar consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        if (user == null) {
            response.put("mensaje", "El cliente ID:".concat(id.toString().concat(" no existe en la base de datos¡¡")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<User>(user, HttpStatus.OK);

    }
    
    
     @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User user) {
  
        
        User UserNew = null;
        Map<String, Object> response = new HashMap<>();
        

        try {
              String claveHashed = passwordEncoder.encode(user.getPassword());
            user.setPassword(claveHashed); // Actualizar la contraseña con el hash

            // Guardar el usuario con la contraseña hasheada
            UserNew = userservice.save(user);

        } catch (DataAccessException e) {
            response.put("mensaje", "error al realizar insertar en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("mensaje", "usuario creado con exito");
        response.put("cliente", UserNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id) {
        User userActual = userservice.findById(id);
        User userUpdate = null;
        Map<String, Object> response = new HashMap<>();
        if (userActual == null) {
            response.put("mensaje", "Eror: no se pudo editar, el cliente ID:".concat(id.toString().concat(" no existe en la base de datos¡¡")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            userActual.setName(user.getName());
               String claveHashed = passwordEncoder.encode(user.getPassword());

            userActual.setPassword(claveHashed);
            userActual.setRole(user.getRole());

            userUpdate = userservice.save(userActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "error al  actualizar en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("mensaje", "cliente actualizado con exito");
        response.put("cliente", userUpdate);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }
    
      @DeleteMapping("/users/{id}")
    public  ResponseEntity<?>  delate(@PathVariable Long id) {
                Map<String, Object> response = new HashMap<>();

         try {
        userservice.delete(id);
         } catch (DataAccessException e) {
            response.put("mensaje", "error al  actualizar en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
         response.put("mensaje", "el cliente eliminado con exito!!");
                     return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }
    
    
    
    
}
