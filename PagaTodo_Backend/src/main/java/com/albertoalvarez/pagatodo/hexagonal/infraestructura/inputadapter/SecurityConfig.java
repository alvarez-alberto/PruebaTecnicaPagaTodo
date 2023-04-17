package com.albertoalvarez.pagatodo.hexagonal.infraestructura.inputadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.albertoalvarez.pagatodo.hexagonal.aplicacion.UsuarioDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UsuarioDetailsService usuarioDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/public").permitAll() // URLs públicas que no requieren autenticación
                .antMatchers("/admin").hasRole("ADMIN") // URLs que requieren rol de ADMIN
                .anyRequest().authenticated() // Cualquier otra URL requiere autenticación
                .and()
                .formLogin() // Configuración del formulario de login
                .loginPage("/login") // Página de login personalizada
                .permitAll()
                .and()
                .logout() // Configuración del logout
                .permitAll();
    }
}