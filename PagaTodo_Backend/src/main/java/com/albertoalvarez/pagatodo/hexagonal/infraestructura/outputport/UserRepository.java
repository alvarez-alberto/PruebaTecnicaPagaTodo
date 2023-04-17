package com.albertoalvarez.pagatodo.hexagonal.infraestructura.outputport;

import com.albertoalvarez.pagatodo.hexagonal.dominio.User;

public interface UserRepository {
    
    User findByUsername(String username);
}

