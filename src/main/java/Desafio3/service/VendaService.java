package Desafio3.service;

import Desafio3.model.Venda;
import Desafio3.repository.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    // Método para listar todas as vendas
    public List<Venda> listarVendas() {
        return vendaRepository.findAll();
    }

    @Transactional
    public Venda criarVenda(Venda venda) {
        venda.validar(); // Valida os dados da venda
        venda.atualizarEstoque(); // Atualiza o estoque
        return vendaRepository.save(venda);
    }

    public Venda buscarVendaPorId(Long id) {
        return vendaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Venda não encontrada com ID: " + id));
    }

    public void deletarVenda(Long id) {
        Venda venda = buscarVendaPorId(id);
        vendaRepository.delete(venda);
    }

    public Venda atualizarVenda(Long id, Venda vendaAtualizada) {
        Venda venda = buscarVendaPorId(id);
        venda.setItensVenda(vendaAtualizada.getItensVenda());
        venda.setUsuario(vendaAtualizada.getUsuario());
        venda.validar(); // Valida os dados da venda
        venda.atualizarEstoque(); // Atualiza o estoque
        return vendaRepository.save(venda);
    }
}
