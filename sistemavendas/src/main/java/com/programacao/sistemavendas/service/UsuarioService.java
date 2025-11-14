package com.programacao.sistemavendas.service;

import com.programacao.sistemavendas.exception.ProdutoInvalidoException;
import com.programacao.sistemavendas.model.Usuario;
import com.programacao.sistemavendas.model.Empresa;
import com.programacao.sistemavendas.repository.UsuarioRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criarUsuario(Usuario usuario) throws ProdutoInvalidoException {
        Optional<Usuario> existente = usuarioRepository.findByEmail(usuario.getEmail());
        if (existente.isPresent()) {
            throw new ProdutoInvalidoException("Email já cadastrado.");
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuariosPorEmpresa(Empresa empresa) {
        return usuarioRepository.findByEmpresa(empresa);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
}
