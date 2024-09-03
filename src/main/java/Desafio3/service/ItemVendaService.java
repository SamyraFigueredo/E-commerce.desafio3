package Desafio3.service;

import Desafio3.model.ItemVenda;
import Desafio3.model.Produto;
import Desafio3.repository.ItemVendaRepository;
import Desafio3.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemVendaService {

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public ItemVenda criarItemVenda(ItemVenda itemVenda) {
        Produto produto = itemVenda.getProduto();

        if (produto.getEstoque() < itemVenda.getQuantidade()) {
            throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome());
        }

        itemVenda.setPrecoUnitario(produto.getPreco());
        produto.setEstoque(produto.getEstoque() - itemVenda.getQuantidade());
        produtoRepository.save(produto);

        return itemVendaRepository.save(itemVenda);
    }

    public Optional<ItemVenda> obterItemVendaPorId(Long id) {
        return itemVendaRepository.findById(id);
    }

    public void deletarItemVenda(Long id) {
        itemVendaRepository.deleteById(id);
    }
}
