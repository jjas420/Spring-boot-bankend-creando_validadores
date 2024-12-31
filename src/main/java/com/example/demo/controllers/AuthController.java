/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controllers;

import com.example.demo.dto.AuthRequestDto;
import com.example.demo.dto.AuthResponseDto;
import com.example.demo.models.entity.User;
import com.example.demo.models.services.IUserService;
import com.example.demo.models.services.JwtUtilService;
import java.util.List;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ayosu
 */
@Controller
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private IUserService userRepository;
    @Autowired
    private JwtUtilService jwtUtilService;
    
    
     @Autowired
    private IUserService userservice;

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthRequestDto authRequestDto) {
        try {

            //1. Gestion authenticationManager
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequestDto.getUser(), authRequestDto.getPassword()
            ));

            //2. Validar el usuario en la bd
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequestDto.getUser());
            User userModel = userRepository.findByName(authRequestDto.getUser());

            //3. Generar token
            String jwt = this.jwtUtilService.generateToken(userDetails, userModel.getRole());
            String refreshToken = this.jwtUtilService.generateRefreshToken(userDetails, userModel.getRole());

            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setToken(jwt);
            authResponseDto.setRefreshToken(refreshToken);

            return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error Authetication:::" + e.getMessage());
        }

    }
    @PostMapping("/refresh")
    public ResponseEntity<?> auth(@RequestBody Map<String, String>  request) {
        String refreshToken = request.get("refreshToken");
        try {
            String username = jwtUtilService.extractUsername(refreshToken);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            User userModel = userRepository.findByName(username);

            if(jwtUtilService.validateToken(refreshToken, userDetails)) {
                String newJwt = jwtUtilService.generateToken(userDetails, userModel.getRole());
                String newRefreshToken = jwtUtilService.generateRefreshToken(userDetails, userModel.getRole());

                AuthResponseDto authResponseDto = new AuthResponseDto();
                authResponseDto.setToken(newJwt);
                authResponseDto.setRefreshToken(newRefreshToken);

                return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
            }else {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
            }


        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error refresh token:::" + e.getMessage());
        }

    }
    
    
    
    
    
    

}
