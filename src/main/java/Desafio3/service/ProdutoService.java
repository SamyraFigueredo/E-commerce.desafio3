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

    public Produto criarProduto(Produto produto) {
        produto.validar();
        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> obterProdutoPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            Produto produtoExistente = produtoOptional.get();
            produtoExistente.setNome(produtoAtualizado.getNome());
            produtoExistente.setDescricao(produtoAtualizado.getDescricao());
            produtoExistente.setPreco(produtoAtualizado.getPreco());
            produtoExistente.setEstoque(produtoAtualizado.getEstoque());
            produtoExistente.setAtivo(produtoAtualizado.getAtivo());
            produtoExistente.validar();

            return produtoRepository.save(produtoExistente);
        } else {
            throw new RuntimeException("Produto não encontrado");
        }
    }

    public void deletarProduto(Long id) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            if (produto.getItensVenda().isEmpty()) {
                produtoRepository.deleteById(id);
            } else {
                throw new RuntimeException("Produto já foi vendido e não pode ser deletado");
            }
        } else {
            throw new RuntimeException("Produto não encontrado");
        }
    }

    public void inativarProduto(Long id) {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            produto.setAtivo(false);
            produtoRepository.save(produto);
        } else {
            throw new RuntimeException("Produto não encontrado");
        }
    }
}
