package com.programacao.sistemavendas.controller;

import com.programacao.sistemavendas.exception.ProdutoInvalidoException;
import com.programacao.sistemavendas.model.Empresa;
import com.programacao.sistemavendas.model.Produto;
import com.programacao.sistemavendas.service.EmpresaService;
import com.programacao.sistemavendas.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final EmpresaService empresaService;

    public ProdutoController(ProdutoService produtoService, EmpresaService empresaService) {
        this.produtoService = produtoService;
        this.empresaService = empresaService;
    }

    @PostMapping
    public ResponseEntity<?> criarProduto(@RequestBody Produto produto,
                                          @RequestParam Long empresaId) {
        Optional<Empresa> empresaOpt = empresaService.buscarPorId(empresaId);
        if (empresaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Empresa não encontrada.");
        }

        try {
            Produto produtoCriado = produtoService.cadastrarProduto(produto, empresaOpt.get());
            return ResponseEntity.ok(produtoCriado);
        } catch (ProdutoInvalidoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarProdutos(@RequestParam Long empresaId) {
        Optional<Empresa> empresaOpt = empresaService.buscarPorId(empresaId);
        if (empresaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Empresa não encontrada.");
        }

        List<Produto> produtos = produtoService.listarProdutos(empresaOpt.get());
        return ResponseEntity.ok(produtos);
    }
}
