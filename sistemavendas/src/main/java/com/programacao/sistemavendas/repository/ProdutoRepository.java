package com.programacao.sistemavendas.repository;

import com.programacao.sistemavendas.model.Empresa;
import com.programacao.sistemavendas.model.Produto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByEmpresa(Empresa empresa);
    Optional<Produto> findByCodigoAndEmpresa(String codigo, Empresa empresa);
}
