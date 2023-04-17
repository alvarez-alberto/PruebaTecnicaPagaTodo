package com.albertoalvarez.pagatodo.hexagonal.aplicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.albertoalvarez.pagatodo.hexagonal.dominio.User;


@Service
public class UsuarioDetailsService implements UserDetailsService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String sql = "SELECT * FROM users WHERE username = ?";
        List<Object> userList = jdbcTemplate.query(sql, (rs, rowNum) ->
                new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getBoolean("enabled"),
                        rs.getBoolean("account_non_expired"),
                        rs.getBoolean("credentials_non_expired"),
                        rs.getBoolean("account_non_locked"),
                        null
                ),
                username
        );
    
        if (userList.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
    
        User user = (User) userList.get(0);
        return user;
    }

    public void registrarUsuario(User newUser) {
        String sql = "INSERT INTO users (username, password, enabled, account_non_expired, credentials_non_expired, account_non_locked) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                newUser.getUsername(),
                new BCryptPasswordEncoder().encode(newUser.getPassword()),
                newUser.isEnabled(),
                newUser.isAccountNonExpired(),
                newUser.isCredentialsNonExpired(),
                newUser.isAccountNonLocked());
    }

    // Verificar si el usuario ya existe en la base de datos
    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count > 0;
    }
}