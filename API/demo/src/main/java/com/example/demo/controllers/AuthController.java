package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.JWTResponseDTO;
import com.example.demo.DTO.LoginDTO;
import com.example.demo.DTO.RegisterDTO;
import com.example.demo.common.ApiResponse;
import com.example.demo.config.JWTGenerator;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserRepository userRepo;
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JWTGenerator jwtGenerator;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterDTO dto) {
        try {
            if (userService.IsNameExist(dto)) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "User have been registered"),
                        HttpStatus.BAD_REQUEST);
            }
            userService.register(dto);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "created successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTResponseDTO> signin(@RequestBody LoginDTO dto) {
        try {
            Authentication auth = authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            dto.getName(),
                            dto.getPassword()));
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(auth);
            String token = jwtGenerator.GeneratedToken(auth);
            return new ResponseEntity<>(new JWTResponseDTO(token), HttpStatus.OK);
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }

    }
}
