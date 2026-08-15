package com.ebac.modulo40.controller;

import com.ebac.modulo40.controller.ResponseWrapper;
import com.ebac.modulo40.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseWrapper<Boolean> login(@RequestParam String username, @RequestParam String password) {
        boolean valido = loginService.validarCredenciales(username, password);
        return new ResponseWrapper<>(valido, valido ? "Login exitoso" : "Credenciales inválidas", null);
    }
}