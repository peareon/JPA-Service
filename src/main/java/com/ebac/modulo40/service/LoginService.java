package com.ebac.modulo40.service;

import com.ebac.modulo40.dto.Admin;
import com.ebac.modulo40.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    AdminRepository adminRepository;

    public boolean validarCredenciales(String username, String password) {
        Optional<Admin> credencial = adminRepository.findByUsername(username);
        return credencial.map(admin -> admin.getPassword().equals(password)).orElse(false);
    }
}