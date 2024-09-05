package Desafio3.service;

import Desafio3.model.Estoque;
import Desafio3.model.Produto;
import Desafio3.repository.EstoqueRepository;
import Desafio3.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    // Registrar uma movimentação de estoque
    public Estoque registrarMovimentacao(Estoque estoque) {
        Produto produto = estoque.getProduto();
        estoque.atualizarEstoque();  // Atualiza o estoque do produto com base no tipo de movimentação
        produtoService.atualizarProduto(produto);  // Atualiza o produto no banco de dados
        return estoqueRepository.save(estoque);  // Salva a movimentação de estoque
    }

    // Listar todas as movimentações de estoque
    public List<Estoque> listarMovimentacoes() {
        return estoqueRepository.findAll();
    }

    // Buscar movimentação de estoque por ID
    public Optional<Estoque> buscarMovimentacaoPorId(Long id) {
        return estoqueRepository.findById(id);
    }

    // Adicionar estoque a um produto
    public void adicionarEstoque(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade a ser adicionada deve ser maior que zero.");
        }

        produto.setEstoque(produto.getEstoque() + quantidade);
        produtoRepository.save(produto);

        // Registrar a movimentação no histórico de estoque
        Estoque movimentacao = new Estoque();
        movimentacao.setProduto(produto);
        movimentacao.setQuantidade(quantidade);
        movimentacao.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
        registrarMovimentacao(movimentacao);
    }

    // Verificar a quantidade de estoque disponível de um produto
    public int verificarQuantidade(Produto produto) {
        return produto.getEstoque();
    }

    // Remover estoque de um produto
    public void removerEstoque(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade a ser removida deve ser maior que zero.");
        }

        if (produto.getEstoque() < quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente para a remoção.");
        }

        produto.setEstoque(produto.getEstoque() - quantidade);
        produtoRepository.save(produto);

        // Registrar a movimentação no histórico de estoque
        Estoque movimentacao = new Estoque();
        movimentacao.setProduto(produto);
        movimentacao.setQuantidade(quantidade);
        movimentacao.setTipoMovimentacao(TipoMovimentacao.SAIDA);
        registrarMovimentacao(movimentacao);
    }
}
