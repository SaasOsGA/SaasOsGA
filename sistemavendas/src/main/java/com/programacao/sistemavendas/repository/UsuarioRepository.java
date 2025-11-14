package com.programacao.sistemavendas.repository;

import com.programacao.sistemavendas.model.Usuario;
import com.programacao.sistemavendas.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByEmpresa(Empresa empresa);
    Optional<Usuario> findByEmail(String email);
}
