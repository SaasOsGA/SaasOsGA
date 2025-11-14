package com.programacao.sistemavendas.controller;

import com.programacao.sistemavendas.exception.ProdutoInvalidoException;
import com.programacao.sistemavendas.model.Usuario;
import com.programacao.sistemavendas.model.Empresa;
import com.programacao.sistemavendas.service.UsuarioService;
import com.programacao.sistemavendas.service.EmpresaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;

    public UsuarioController(UsuarioService usuarioService, EmpresaService empresaService) {
        this.usuarioService = usuarioService;
        this.empresaService = empresaService;
    }

    @PostMapping("/{empresaId}")
    public ResponseEntity<Usuario> criarUsuario(@PathVariable Long empresaId, @RequestBody Usuario usuario) {
        Optional<Empresa> empresaOpt = empresaService.buscarPorId(empresaId);
        if (empresaOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        usuario.setEmpresa(empresaOpt.get());
        try {
            Usuario usuarioCriado = usuarioService.criarUsuario(usuario);
            return ResponseEntity.ok(usuarioCriado);
        } catch (ProdutoInvalidoException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{empresaId}")
    public ResponseEntity<List<Usuario>> listarUsuariosPorEmpresa(@PathVariable Long empresaId) {
        Optional<Empresa> empresaOpt = empresaService.buscarPorId(empresaId);
        if (empresaOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Usuario> usuarios = usuarioService.listarUsuariosPorEmpresa(empresaOpt.get());
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/detalhes/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        var usuario = usuarioService.buscarPorId(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
