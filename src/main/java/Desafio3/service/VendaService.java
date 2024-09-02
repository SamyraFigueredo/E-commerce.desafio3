package Desafio3.service;

import Desafio3.model.ItemVenda;
import Desafio3.model.Venda;
import Desafio3.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    public Venda criarVenda(Venda venda) {
        validarVenda(venda);
        return vendaRepository.save(venda);
    }

    private void validarVenda(Venda venda) {
        // Validar cada item da venda para garantir que todos os campos necessários estão preenchidos
        for (ItemVenda item : venda.getItensVenda()) {
            if (item.getPrecoUnitario() == null || item.getProduto() == null || item.getQuantidade() == null) {
                throw new RuntimeException("Todos os campos do item de venda devem ser preenchidos.");
            }
        }
    }

    public Optional<Venda> buscarVendaPorId(Long id) {
        return vendaRepository.findById(id);
    }

    public Venda atualizarVenda(Long id, Venda vendaAtualizada) {
        if (vendaRepository.existsById(id)) {
            vendaAtualizada.setId(id);
            return vendaRepository.save(vendaAtualizada);
        } else {
            throw new RuntimeException("Venda não encontrada");
        }
    }

    public void deletarVenda(Long id) {
        if (vendaRepository.existsById(id)) {
            vendaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Venda não encontrada");
        }
    }

    public Iterable<Venda> listarVendas() {
        return vendaRepository.findAll();
    }
}
