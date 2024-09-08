package Desafio3.service;

import Desafio3.model.Produto;
import Desafio3.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    // Cria um novo produto com validação
    public Produto criarProduto(Produto produto) {
        produto.validar(); // Validação de estoque e preço
        return produtoRepository.save(produto);
    }

    // Atualiza um produto
    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();
            produto.setNome(produtoAtualizado.getNome());
            produto.setDescricao(produtoAtualizado.getDescricao());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setEstoque(produtoAtualizado.getEstoque());
            produto.setAtivo(produtoAtualizado.getAtivo());
            produto.validar(); // Validações ao atualizar
            return produtoRepository.save(produto);
        } else {
            throw new IllegalArgumentException("Produto não encontrado.");
        }
    }

    // Busca todos os produtos
    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll();
    }

    // Busca um produto por ID
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    // Inativa um produto
    public void inativarProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com id: " + id));
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }
}