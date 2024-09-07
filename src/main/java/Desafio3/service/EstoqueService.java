package Desafio3.service;

import Desafio3.model.Estoque;
import Desafio3.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoService produtoService;

    // Criar ou atualizar movimentação de estoque
    @Transactional
    public Estoque criarOuAtualizarEstoque(Estoque estoque) {
        // Atualizar o estoque do produto
        estoque.atualizarEstoque();
        // Salvar a movimentação de estoque
        return estoqueRepository.save(estoque);
    }

    // Buscar movimentação de estoque por ID
    public Estoque buscarPorId(Long id) {
        Optional<Estoque> estoque = estoqueRepository.findById(id);
        if (estoque.isEmpty()) {
            throw new IllegalArgumentException("Movimentação de estoque não encontrada.");
        }
        return estoque.get();
    }

    // Remover movimentação de estoque por ID
    @Transactional
    public void removerPorId(Long id) {
        estoqueRepository.deleteById(id);
    }
}
