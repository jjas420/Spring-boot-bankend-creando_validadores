/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controllers;

import com.example.demo.models.entity.Cliente;
import com.example.demo.models.entity.User;
import com.example.demo.models.services.IClenteService;
import com.example.demo.models.services.IUserService;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ayosu
 */

@RestController
@RequestMapping("/api")
public class ClienteRestController {

    @Autowired
    private IClenteService clienteService;
    
   

    @GetMapping("/clientes")
    public List<Cliente> index() {
        return clienteService.findAll();

    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();

        try {
            cliente = clienteService.findById(id);

        } catch (DataAccessException e) {
            response.put("mensaje", "error al realizar consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        if (cliente == null) {
            response.put("mensaje", "El cliente ID:".concat(id.toString().concat(" no existe en la base de datos¡¡")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);

    }

    @PostMapping("/clientes")
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {

        Cliente clienteNew = null;
        Map<String, Object> response = new HashMap<>();
       /* List<String> errors=  new ArrayList<>();
        
        for (FieldError err:result.getFieldErrors()){
            errors.add("campo '"+err.getField()+"'"+err.getDefaultMessage());
            
        }*/
       if(result.hasErrors()){
       List<String> errors=result.getFieldErrors().stream()
               .map(err-> "el campo '" +err.getField()+"'"+err.getDefaultMessage())
               .collect(Collectors.toList());
       
        response.put("errors", errors);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
       }
        try {
            clienteNew = clienteService.save(cliente);

        } catch (DataAccessException e) {
            response.put("mensaje", "error al realizar insertar en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("mensaje", "cliente creado con exito : ");
        response.put("cliente", clienteNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente,BindingResult result, @PathVariable Long id) {
        Cliente clienteActual = clienteService.findById(id);
        Cliente clienteUpdate = null;
        Map<String, Object> response = new HashMap<>();
        if(result.hasErrors()){
       List<String> errors=result.getFieldErrors().stream()
               .map(err-> "el campo '" +err.getField()+"'"+err.getDefaultMessage())
               .collect(Collectors.toList());
       
        response.put("errors", errors);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
       }
        if (clienteActual == null) {
            response.put("mensaje", "Eror: no se pudo editar, el cliente ID:".concat(id.toString().concat(" no existe en la base de datos¡¡")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setEmail(cliente.getEmail());
            clienteActual.setCreateAt(cliente.getCreateAt());

            clienteUpdate = clienteService.save(clienteActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "error al  actualizar en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        response.put("mensaje", "cliente actualizado con exito");
        response.put("cliente", clienteUpdate);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("/clientes/{id}")
    public  ResponseEntity<?>  delate(@PathVariable Long id) {
                Map<String, Object> response = new HashMap<>();

         try {
        clienteService.delete(id);
         } catch (DataAccessException e) {
            response.put("mensaje", "error al  actualizar en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
         response.put("mensaje", "el cliente eliminado con exito!!");
                     return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }
}
