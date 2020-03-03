package com.example.securingweb.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.securingweb.dao.UsuarioRepository;
import com.example.securingweb.models.Usuario;

@Controller
public class SignUpController {
	
	@Autowired
	UsuarioRepository userRepo;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	// Presenta formulario de registro, preparado para alta usuario
	@GetMapping("/signup")
	public String altaUsuario(Usuario usuario) {
		return "register";
	}
	
	// Procesa formulario alta usuario
	@PostMapping("/adduser")
	public String saveUser(@Valid Usuario usuario, BindingResult result) {
		if (result.hasErrors()) {
			System.out.println("\n\n\n\nError en alta usuario.\n\n");
			return "redirect:/login?novalid";
		} else {
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			usuario.setAuthority("ROLE_USER");
			userRepo.save(usuario);
			return "redirect:/hello";
		}
	}
}
