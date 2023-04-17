package com.albertoalvarez.pagatodo.hexagonal.infraestructura.inputadapter;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albertoalvarez.pagatodo.hexagonal.aplicacion.UsuarioDetailsService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {
    
    @Autowired
    private UsuarioDetailsService usuarioService;

    @GetMapping
    public String listarUsuarios() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(usuarioService.listarUsuarios());
    }
}