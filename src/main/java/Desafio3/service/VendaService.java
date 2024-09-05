package Desafio3.service;

import Desafio3.model.ItemVenda;
import Desafio3.model.Venda;
import Desafio3.repository.ItemVendaRepository;
import Desafio3.repository.VendaRepository;
import jakarta.persistence.EntityNotFoundException;
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
        try {
            // Valida a venda
            venda.validar();
            // Verificar preços dos itens
            for (ItemVenda item : venda.getItensVenda()) {
                if (item.getPrecoUnitario() == null) {
                    throw new IllegalArgumentException("O preço unitário do item não pode ser nulo.");
                }
            }

            // Associar a venda com os itens antes de salvar
            for (ItemVenda item : venda.getItensVenda()) {
                item.setVenda(venda);
            }

            Venda vendaSalva = vendaRepository.save(venda);
            return vendaSalva;
        } catch (Exception e) {
            // Log o erro para depuração
            e.printStackTrace();
            throw e;
        }
    }


    public Venda obterVendaPorId(Long id) {
        return vendaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Venda não encontrada"));
    }

    public List<Venda> listarVendas() {
        return vendaRepository.findAll();
    }

    @Transactional
    public Venda atualizarVenda(Long id, Venda venda) {
        Venda vendaExistente = vendaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Venda não encontrada"));

        // Atualizar detalhes da Venda existente
        vendaExistente.setUsuario(venda.getUsuario());
        vendaExistente.setDataItemVenda(venda.getDataItemVenda());

        // Atualizar itens de venda
        vendaExistente.getItensVenda().clear();
        for (ItemVenda item : venda.getItensVenda()) {
            item.setVenda(vendaExistente);
            vendaExistente.getItensVenda().add(item);
        }

        return vendaRepository.save(vendaExistente);
    }

    @Transactional
    public void deletarVenda(Long id) {
        Venda venda = obterVendaPorId(id);
        vendaRepository.delete(venda);
    }
}
