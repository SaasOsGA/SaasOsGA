package com.programacao.sistemavendas.service;

import com.programacao.sistemavendas.exception.ProdutoInvalidoException;
import com.programacao.sistemavendas.model.Empresa;
import com.programacao.sistemavendas.model.Produto;
import com.programacao.sistemavendas.repository.ProdutoRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto cadastrarProduto(Produto produto, Empresa empresa) throws ProdutoInvalidoException {
        Optional<Produto> produtoExistente = produtoRepository.findByCodigoAndEmpresa(produto.getCodigo(), empresa);
        if (produtoExistente.isPresent()) {
            throw new ProdutoInvalidoException("Código de produto já existente na empresa.");
        }
        produto.setEmpresa(empresa);
        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos(Empresa empresa) {
        return produtoRepository.findByEmpresa(empresa);
    }

    public Optional<Produto> buscarProdutoPorCodigo(String codigo, Empresa empresa) {
        return produtoRepository.findByCodigoAndEmpresa(codigo, empresa);
    }

    public void deletarProduto(Long id, Empresa empresa) throws ProdutoInvalidoException {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoInvalidoException("Produto não encontrado."));

        if (!produto.getEmpresa().equals(empresa)) {
            throw new ProdutoInvalidoException("Produto não pertence à empresa.");
        }

        produtoRepository.delete(produto);
    }

    public Produto atualizarProduto(Produto produtoAtualizado, Empresa empresa) throws ProdutoInvalidoException {
        Produto produto = produtoRepository.findById(produtoAtualizado.getId())
                .orElseThrow(() -> new ProdutoInvalidoException("Produto não encontrado."));

        if (!produto.getEmpresa().equals(empresa)) {
            throw new ProdutoInvalidoException("Produto não pertence à empresa.");
        }

        // Update fields
        produto.setNome(produtoAtualizado.getNome());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setEstoqueDisponivel(produtoAtualizado.getEstoqueDisponivel());
        // etc...
        return produtoRepository.save(produto);
    }
}
