package com.programacao.sistemavendas.service;

import com.programacao.sistemavendas.exception.ProdutoInvalidoException;
import com.programacao.sistemavendas.model.Empresa;
import com.programacao.sistemavendas.repository.EmpresaRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public Empresa criarEmpresa(Empresa empresa) throws ProdutoInvalidoException {
        Optional<Empresa> existente = empresaRepository.findByNome(empresa.getNome());
        if (existente.isPresent()) {
            throw new ProdutoInvalidoException("Empresa com esse nome já existe.");
        }
        return empresaRepository.save(empresa);
    }

    public List<Empresa> listarEmpresas() {
        return empresaRepository.findAll();
    }

    public Optional<Empresa> buscarPorId(Long id) {
        return empresaRepository.findById(id);
    }
}
