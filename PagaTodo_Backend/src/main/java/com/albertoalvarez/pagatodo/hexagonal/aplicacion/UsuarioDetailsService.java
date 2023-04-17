package com.albertoalvarez.pagatodo.hexagonal.aplicacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.albertoalvarez.pagatodo.hexagonal.dominio.User;
import com.albertoalvarez.pagatodo.hexagonal.infraestructura.outputadapter.UserPostgresRepository;
import com.albertoalvarez.pagatodo.hexagonal.infraestructura.outputport.UserRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User usuario = userRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return (UserDetails) User.builder()
            .username(usuario.getUsername())
            .password(usuario.getPassword())
            .isActive(usuario.isActive())
            .roles(usuario.getRoles())
            .build();
    }
}