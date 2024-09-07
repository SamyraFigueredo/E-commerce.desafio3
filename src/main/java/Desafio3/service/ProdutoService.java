package Desafio3.service;

import Desafio3.model.Produto;
import Desafio3.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    // Criar um novo produto
    @Transactional
    public Produto criarProduto(Produto produto) {
        produto.validar(); // Valida o produto antes de persistir
        return produtoRepository.save(produto);
    }

    // Buscar um produto por ID
    public Produto buscarProdutoPorId(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isEmpty()) {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }
        return produto.get();
    }

    // Listar todos os produtos
    public Iterable<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    // Atualizar um produto existente
    @Transactional
    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        Produto produtoExistente = buscarProdutoPorId(id);
        produtoAtualizado.setId(produtoExistente.getId());
        produtoAtualizado.validar(); // Valida o produto antes de atualizar
        return produtoRepository.save(produtoAtualizado);
    }

    // Inativar um produto
    @Transactional
    public void inativar(Long id) {
        Produto produto = buscarProdutoPorId(id); // Verifica se o produto existe
        if (!produto.getAtivo()) {
            throw new IllegalArgumentException("Produto já está inativo.");
        }
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }

    // Deletar um produto
    @Transactional
    public void deletarProduto(Long id) {
        Produto produto = buscarProdutoPorId(id);
        produtoRepository.delete(produto);
    }
}
