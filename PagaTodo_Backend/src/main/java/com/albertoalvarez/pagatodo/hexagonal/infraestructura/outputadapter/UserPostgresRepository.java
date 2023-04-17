package com.albertoalvarez.pagatodo.hexagonal.infraestructura.outputadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.albertoalvarez.pagatodo.hexagonal.dominio.User;
import com.albertoalvarez.pagatodo.hexagonal.infraestructura.outputport.UserRepository;

@Repository
public class UserPostgresRepository implements UserRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User findByUsername(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUsername'");
    }
    
}
