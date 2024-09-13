package Desafio3.service;

import Desafio3.model.Estoque;
import Desafio3.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    public Estoque buscarPorProduto(Long produtoId) {
        return estoqueRepository.findByProdutoId(produtoId);
    }

    public Estoque encontrarPorProdutoId(Long produtoId) {
        return estoqueRepository.findByProdutoId(produtoId);
    }

    public Estoque salvar(Estoque estoque) {
        return estoqueRepository.save(estoque);
    }

    public Optional<Estoque> encontrarPorId(Long id) {
        return estoqueRepository.findById(id);
    }

    public void adicionarEstoque(Long produtoId, int quantidade) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId);
        estoque.adicionarEstoque(quantidade);
        estoqueRepository.save(estoque);
    }

    public void reduzirEstoque(Long produtoId, int quantidade) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId);
        if (estoque.reduzirEstoque(quantidade)) {
            estoqueRepository.save(estoque);
        } else {
            throw new IllegalArgumentException("Estoque insuficiente.");
        }
    }

    public void deletarPorId(Long id) {
        estoqueRepository.deleteById(id);
    }
}
