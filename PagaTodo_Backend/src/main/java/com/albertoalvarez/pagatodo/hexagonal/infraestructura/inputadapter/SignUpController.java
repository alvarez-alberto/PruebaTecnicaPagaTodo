package com.albertoalvarez.pagatodo.hexagonal.infraestructura.inputadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.albertoalvarez.pagatodo.hexagonal.aplicacion.UsuarioDetailsService;
import com.albertoalvarez.pagatodo.hexagonal.dominio.User;

@Controller
public class SignUpController {

    @Autowired
    private UsuarioDetailsService usuarioDetailsService;

    @PostMapping("/signup")
    public String signUp( User signUpRequest, Model model, RedirectAttributes redirectAttributes) {
        
        String username = signUpRequest.getUsername();
        String password = signUpRequest.getPassword();
     

        if (usuarioDetailsService.userExists(signUpRequest.getUsername())) {
            model.addAttribute("error", "El nombre de usuario ya está en uso.");
            return "signup";
        }


        User newUser = new User(username, password, true, true, true, true, null);

        usuarioDetailsService.registrarUsuario(newUser);
        redirectAttributes.addFlashAttribute("success", "Registro exitoso");
        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

}