package com.programacao.sistemavendas.controller;

import com.programacao.sistemavendas.exception.ProdutoInvalidoException;
import com.programacao.sistemavendas.model.Empresa;
import com.programacao.sistemavendas.service.EmpresaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    public ResponseEntity<Empresa> criarEmpresa(@RequestBody Empresa empresa) {
        try {
            Empresa empresaCriada = empresaService.criarEmpresa(empresa);
            return ResponseEntity.ok(empresaCriada);
        } catch (ProdutoInvalidoException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> listarEmpresas() {
        List<Empresa> empresas = empresaService.listarEmpresas();
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> buscarPorId(@PathVariable Long id) {
        var empresa = empresaService.buscarPorId(id);
        return empresa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
