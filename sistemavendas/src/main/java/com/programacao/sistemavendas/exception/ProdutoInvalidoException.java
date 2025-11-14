package com.programacao.sistemavendas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProdutoInvalidoException extends RuntimeException {
    public ProdutoInvalidoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoInvalidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
