package Desafio3.service;

import Desafio3.model.Estoque;
import Desafio3.model.Produto;
import Desafio3.repository.EstoqueRepository;
import Desafio3.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Estoque> listarTodos() {
        return estoqueRepository.findAll();
    }

    public Optional<Estoque> buscarPorId(Long id) {
        return estoqueRepository.findById(id);
    }

    @Transactional
    public Estoque criar(Estoque estoque) {
        Estoque estoqueCriado = estoqueRepository.save(estoque);
        atualizarEstoqueDoProduto(estoque);
        return estoqueCriado;
    }

    @Transactional
    public Estoque atualizar(Long id, Estoque estoqueAtualizado) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estoque não encontrado com id: " + id));
        estoque.setQuantidade(estoqueAtualizado.getQuantidade());
        estoque.setTipoMovimentacao(estoqueAtualizado.getTipoMovimentacao());
        Estoque estoqueAtualizadoNoBanco = estoqueRepository.save(estoque);
        atualizarEstoqueDoProduto(estoqueAtualizadoNoBanco);
        return estoqueAtualizadoNoBanco;
    }

    @Transactional
    public void excluir(Long id) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estoque não encontrado com id: " + id));
        estoqueRepository.delete(estoque);
        atualizarEstoqueDoProduto(estoque);
    }

    private void atualizarEstoqueDoProduto(Estoque estoque) {
        Produto produto = estoque.getProduto();
        if (produto != null) {
            estoque.atualizarEstoque();
            produtoRepository.save(produto); 
        }
    }
}
