package com.programacao.sistemavendas.controller;

import com.programacao.sistemavendas.exception.ProdutoInvalidoException;
import com.programacao.sistemavendas.model.Empresa;
import com.programacao.sistemavendas.model.Usuario;
import com.programacao.sistemavendas.model.Venda;
import com.programacao.sistemavendas.service.EmpresaService;
import com.programacao.sistemavendas.service.UsuarioService;
import com.programacao.sistemavendas.service.VendaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;
    private final EmpresaService empresaService;
    private final UsuarioService usuarioService;

    public VendaController(VendaService vendaService, EmpresaService empresaService, UsuarioService usuarioService) {
        this.vendaService = vendaService;
        this.empresaService = empresaService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<?> criarVenda(@RequestBody Venda venda,
                                        @RequestParam Long empresaId,
                                        @RequestParam Long usuarioId) {
        Optional<Empresa> empresaOpt = empresaService.buscarPorId(empresaId);
        if (empresaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Empresa não encontrada.");
        }

        Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(usuarioId);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        try {
            Venda vendaSalva = vendaService.salvarVenda(venda, usuarioOpt.get(), empresaOpt.get());
            return ResponseEntity.ok(vendaSalva);
        } catch (ProdutoInvalidoException | com.programacao.sistemavendas.exception.EstoqueInsuficienteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarVendas(@RequestParam Long empresaId) {
        Optional<Empresa> empresaOpt = empresaService.buscarPorId(empresaId);
        if (empresaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Empresa não encontrada.");
        }

        List<Venda> vendas = vendaService.listarVendasPorEmpresa(empresaOpt.get());
        return ResponseEntity.ok(vendas);
    }
}
