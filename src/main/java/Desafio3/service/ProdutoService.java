package Desafio3.service;

import Desafio3.model.Produto;
import Desafio3.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    @Transactional
    public Produto criar(Produto produto) {
        produto.validar();
        produto.setAtivo(true); // Define o produto como ativo ao criar
        return produtoRepository.save(produto);
    }

    @Transactional
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com id: " + id));
        produto.setNome(produtoAtualizado.getNome());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setEstoque(produtoAtualizado.getEstoque());
        produto.setAtivo(produtoAtualizado.getAtivo());
        produto.validar(); // Valida o produto atualizado
        return produtoRepository.save(produto);
    }

    @Transactional
    public void excluir(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com id: " + id));

        // Verifica se o produto está associado a alguma venda
        if (!produto.getItensVenda().isEmpty()) {
            throw new IllegalStateException("O produto não pode ser excluído porque está associado a vendas.");
        }

        produtoRepository.delete(produto);
    }

    @Transactional
    public void inativar(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com id: " + id));
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }
}
