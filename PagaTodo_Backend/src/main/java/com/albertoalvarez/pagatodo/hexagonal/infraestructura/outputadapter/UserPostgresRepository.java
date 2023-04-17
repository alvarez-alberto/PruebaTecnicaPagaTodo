package com.albertoalvarez.pagatodo.hexagonal.infraestructura.outputadapter;

import com.albertoalvarez.pagatodo.hexagonal.dominio.User;
import com.albertoalvarez.pagatodo.hexagonal.infraestructura.outputport.UserRepository;

public class UserPostgresRepository implements UserRepository{

    @Override
    public User findByUsername(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUsername'");
    }
    
}
