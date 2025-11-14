package com.programacao.sistemavendas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EstoqueInsuficienteException extends RuntimeException {
    private String codigoProduto;
    private int quantidadeDisponivel;
    private int quantidadeSolicitada;

    public EstoqueInsuficienteException(String codigoProduto, int quantidadeDisponivel, int quantidadeSolicitada) {
        super(String.format("Estoque insuficiente para o produto %s. Disponível: %d, Solicitado: %d",
                codigoProduto, quantidadeDisponivel, quantidadeSolicitada));
        this.codigoProduto = codigoProduto;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.quantidadeSolicitada = quantidadeSolicitada;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public int getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }
}
