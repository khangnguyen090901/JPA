package com.example.demo.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.RegisterDTO;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    RoleRepository roleRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    public User register(RegisterDTO register) {
        User user = new User();
        user.setName(register.getName());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        Role roles = roleRepo.findByName("USER").get();
        user.setRole(Collections.singletonList(roles));

        return userRepo.save(user);
    }

    public boolean IsNameExist(RegisterDTO dto) {
        return userRepo.findByName(dto.getName()).isPresent();
    }

}
