package com.albertoalvarez.pagatodo.hexagonal.dominio;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class User {

    private String username;
    private String password;
    private boolean isActive;
    private List<String> roles;
    
}
