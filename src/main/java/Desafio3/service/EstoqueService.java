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

    public Estoque criarMovimentacao(Estoque estoque) {
        estoque.atualizarEstoque(); // Atualiza o estoque com base na movimentação
        return estoqueRepository.save(estoque);
    }

    public Estoque buscarPorId(Long id) {
        return estoqueRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Movimentação não encontrada"));
    }

    public Estoque atualizarMovimentacao(Long id, Estoque estoqueAtualizado) {
        Estoque estoqueExistente = buscarPorId(id);
        estoqueAtualizado.setId(id);
        estoqueAtualizado.atualizarEstoque(); // Atualiza o estoque com base na movimentação
        return estoqueRepository.save(estoqueAtualizado);
    }

    public void excluirMovimentacao(Long id) {
        Estoque estoqueExistente = buscarPorId(id);
        estoqueRepository.delete(estoqueExistente);
    }
}
