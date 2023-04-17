package com.albertoalvarez.pagatodo.hexagonal.infraestructura.inputadapter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        // Aquí puedes agregar la lógica que necesites para obtener los datos del usuario logueado
        // y pasarlos al modelo para ser utilizados en la vista
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);
        
        return "index"; // Nombre de la vista Thymeleaf que deseas mostrar, en este caso "index.html"
    }
}