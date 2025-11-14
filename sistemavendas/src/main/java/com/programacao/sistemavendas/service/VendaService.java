package com.programacao.sistemavendas.service;

import com.programacao.sistemavendas.exception.EstoqueInsuficienteException;
import com.programacao.sistemavendas.exception.ProdutoInvalidoException;
import com.programacao.sistemavendas.model.Empresa;
import com.programacao.sistemavendas.model.Usuario;
import com.programacao.sistemavendas.model.Venda;
import com.programacao.sistemavendas.model.Produto;
import com.programacao.sistemavendas.repository.VendaRepository;
import com.programacao.sistemavendas.repository.ProdutoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;

    public VendaService(VendaRepository vendaRepository, ProdutoRepository produtoRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public Venda salvarVenda(Venda venda, Usuario vendedor, Empresa empresa) throws ProdutoInvalidoException, EstoqueInsuficienteException {
        // Validar se o produto existe na empresa do vendedor
        Produto produto = produtoRepository.findByCodigoAndEmpresa(venda.getCodigoProduto(), empresa)
                .orElseThrow(() -> new ProdutoInvalidoException("Produto não encontrado na empresa do vendedor"));

        // Verificar estoque suficiente na empresa
        if (produto.getEstoqueDisponivel() < venda.getQuantidade()) {
            throw new EstoqueInsuficienteException(
                    "Estoque insuficiente para venda",
                    produto.getEstoqueDisponivel(),
                    venda.getQuantidade()
            );
        }


        // Atualizar estoque
        produto.reduzirEstoque(venda.getQuantidade());
        produtoRepository.save(produto);

        // Completar dados da venda
        venda.setValorUnitario(produto.getPreco());
        venda.setValorTotal(produto.getPreco() * venda.getQuantidade());
        venda.setNomeProduto(produto.getNome());
        venda.setEmpresa(empresa);
        venda.setVendedor(vendedor);

        return vendaRepository.save(venda);
    }

    public List<Venda> listarVendasPorEmpresa(Empresa empresa) {
        return vendaRepository.findByEmpresa(empresa);
    }
}
