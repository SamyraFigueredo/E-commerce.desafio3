package Desafio3.service;

import Desafio3.model.ItemVenda;
import Desafio3.model.Venda;
import Desafio3.repository.ItemVendaRepository;
import Desafio3.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    public Venda criarVenda(Venda venda) {
        // Valida a venda
        venda.validar();

        // Verificar preços dos itens
        for (ItemVenda item : venda.getItensVenda()) {
            if (item.getPrecoUnitario() == null) {
                throw new IllegalArgumentException("O preço unitário do item não pode ser nulo.");
            }
        }

        // Persiste a venda e os itens
        venda.getItensVenda().forEach(item -> item.setVenda(venda));
        return vendaRepository.save(venda);
    }

    public Venda salvarVenda(Venda venda) {
        venda.validar();
        return vendaRepository.save(venda);
    }

    public Venda obterVendaPorId(Long id) {
        return vendaRepository.findById(id).orElseThrow(() -> new RuntimeException("Venda não encontrada"));
    }

    public List<Venda> listarVendas() {
        return vendaRepository.findAll();
    }

    @Transactional
    public Venda atualizarVenda(Long id, Venda vendaAtualizada) {
        Venda vendaExistente = obterVendaPorId(id);
        vendaExistente.setItensVenda(vendaAtualizada.getItensVenda());
        vendaExistente.setUsuario(vendaAtualizada.getUsuario());
        // Atualize outros campos conforme necessário
        return vendaRepository.save(vendaExistente);
    }

    @Transactional
    public void deletarVenda(Long id) {
        Venda venda = obterVendaPorId(id);
        vendaRepository.delete(venda);
    }
}
