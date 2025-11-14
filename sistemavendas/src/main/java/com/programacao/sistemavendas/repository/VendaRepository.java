package com.programacao.sistemavendas.repository;

import com.programacao.sistemavendas.model.Empresa;
import com.programacao.sistemavendas.model.Venda;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByEmpresa(Empresa empresa);

}
