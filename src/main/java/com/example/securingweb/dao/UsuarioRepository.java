package com.example.securingweb.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.securingweb.models.Usuario;


@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	// Para logarse
	public Usuario findByUsername(String username);
}