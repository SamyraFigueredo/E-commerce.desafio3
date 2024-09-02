package Desafio3.service;

import Desafio3.model.ItemVenda;
import Desafio3.repository.ItemVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemVendaService {

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    public List<ItemVenda> listarItensVenda() {
        return itemVendaRepository.findAll();
    }

    public Optional<ItemVenda> buscarItemVendaPorId(Long id) {
        return itemVendaRepository.findById(id);
    }

    public ItemVenda salvarItemVenda(ItemVenda itemVenda) {
        return itemVendaRepository.save(itemVenda);
    }

    public void deletarItemVenda(Long id) {
        itemVendaRepository.deleteById(id);
    }
}
