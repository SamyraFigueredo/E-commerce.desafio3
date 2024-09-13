package Desafio3.service;

import Desafio3.model.Produto;
import Desafio3.model.Venda;
import Desafio3.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    private VendaService vendaService;

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> encontrarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public List<Produto> encontrarPorNome(String nome) {
        return produtoRepository.findByNomeContaining(nome);
    }

    public List<Produto> listarAtivos() {
        return produtoRepository.findByStatus(Produto.Status.ATIVO);
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void deletarOuInativarProduto(Long id) {
        Produto produto = encontrarPorId(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        List<Venda> vendas = vendaService.listarPorProduto(produto.getId());

        if (!vendas.isEmpty()) {
            produto.setStatus(Produto.Status.INATIVO);
            salvar(produto);  // Inativa o produto se houver vendas
        } else {
            produtoRepository.deleteById(id);  // Deleta o produto se não houver vendas
        }
    }
}