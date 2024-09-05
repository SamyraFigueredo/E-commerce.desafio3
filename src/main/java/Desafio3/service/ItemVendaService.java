package Desafio3.service;

import Desafio3.model.ItemVenda;
import Desafio3.repository.ItemVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemVendaService {

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    public List<ItemVenda> listarTodos() {
        return itemVendaRepository.findAll();
    }

    public Optional<ItemVenda> encontrarPorId(Long id) {
        return itemVendaRepository.findById(id);
    }

    @Transactional
    public ItemVenda salvar(ItemVenda itemVenda) {
        validarItemVenda(itemVenda);
        return itemVendaRepository.save(itemVenda);
    }

    @Transactional
    public void deletar(Long id) {
        if (!itemVendaRepository.existsById(id)) {
            throw new IllegalArgumentException("Item de venda com o ID fornecido não existe.");
        }
        itemVendaRepository.deleteById(id);
    }

    private void validarItemVenda(ItemVenda itemVenda) {
        itemVenda.validar();
    }
}
