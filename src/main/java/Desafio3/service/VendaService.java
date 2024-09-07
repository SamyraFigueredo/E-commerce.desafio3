package Desafio3.service;

import Desafio3.model.Venda;
import Desafio3.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Transactional
    public void criarVenda(Venda venda) {
        validarVenda(venda);
        venda.atualizarEstoque();
        vendaRepository.save(venda);
    }

    private void validarVenda(Venda venda) {
        if (venda.getUsuario() == null || venda.getUsuario().getId() == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo e deve ter um ID válido.");
        }

        if (venda.getItensVenda() == null || venda.getItensVenda().isEmpty()) {
            throw new IllegalArgumentException("A venda deve conter pelo menos um item.");
        }

        for (var item : venda.getItensVenda()) {
            item.validar();  // Certifique-se de que o método validar() exista no item

            if (item.getProduto() == null || item.getProduto().getId() == null) {
                throw new IllegalArgumentException("Cada item de venda deve ter um produto válido.");
            }

            if (item.getQuantidade() == null || item.getQuantidade() <= 0) {
                throw new IllegalArgumentException("A quantidade de cada item de venda deve ser maior que zero.");
            }

            if (item.getPrecoUnitario() == null || item.getPrecoUnitario() <= 0) {
                throw new IllegalArgumentException("O preço unitário de cada item de venda deve ser maior que zero.");
            }

            if (item.getQuantidade() > item.getProduto().getEstoque()) {
                throw new IllegalArgumentException("Estoque insuficiente para o produto: " + item.getProduto().getNome());
            }
        }
    }

    public Venda buscarVendaPorId(Long id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada com ID: " + id));
    }

    @Transactional
    public void deletarVenda(Long id) {
        Venda venda = buscarVendaPorId(id);
        vendaRepository.delete(venda);
    }

    @Transactional
    public Venda atualizarVenda(Long id, Venda vendaAtualizada) {
        Venda vendaExistente = buscarVendaPorId(id);
        vendaAtualizada.setId(id);
        validarVenda(vendaAtualizada);
        vendaAtualizada.atualizarEstoque();
        return vendaRepository.save(vendaAtualizada);
    }
}