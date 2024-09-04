package Desafio3.service;

import Desafio3.model.ItemVenda;
import Desafio3.repository.ItemVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemVendaService {

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    @Transactional
    public ItemVenda criarItemVenda(ItemVenda itemVenda) {
        return itemVendaRepository.save(itemVenda);
    }

    public ItemVenda obterItemVendaPorId(Long id) {
        return itemVendaRepository.findById(id).orElseThrow(() -> new RuntimeException("Item de venda não encontrado"));
    }

    public List<ItemVenda> listarItensVenda() {
        return itemVendaRepository.findAll();
    }

    @Transactional
    public ItemVenda atualizarItemVenda(Long id, ItemVenda itemVendaAtualizado) {
        ItemVenda itemVendaExistente = obterItemVendaPorId(id);
        itemVendaExistente.setPrecoUnitario(itemVendaAtualizado.getPrecoUnitario());
        itemVendaExistente.setProduto(itemVendaAtualizado.getProduto());
        itemVendaExistente.setQuantidade(itemVendaAtualizado.getQuantidade());
        itemVendaExistente.setVenda(itemVendaAtualizado.getVenda());
        // Atualize outros campos conforme necessário
        return itemVendaRepository.save(itemVendaExistente);
    }

    @Transactional
    public void deletarItemVenda(Long id) {
        ItemVenda itemVenda = obterItemVendaPorId(id);
        itemVendaRepository.delete(itemVenda);
    }
}
