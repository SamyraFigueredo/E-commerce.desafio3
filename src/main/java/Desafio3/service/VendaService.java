package Desafio3.service;

import Desafio3.model.Venda;
import Desafio3.model.ItemVenda;
import Desafio3.repository.VendaRepository;
import Desafio3.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Venda criarVenda(Venda venda) {
        venda.validar(); // Chama o método de validação da classe Venda

        // Atualiza o estoque de cada produto da venda
        for (ItemVenda item : venda.getItensVenda()) {
            item.getProduto().setEstoque(item.getProduto().getEstoque() - item.getQuantidade());
            produtoRepository.save(item.getProduto());
        }

        return vendaRepository.save(venda);
    }

    public Optional<Venda> buscarPorId(Long id) {
        return vendaRepository.findById(id);
    }

    public List<Venda> listarTodas() {
        return vendaRepository.findAll();
    }

    @Transactional
    public void deletarVenda(Long id) {
        Optional<Venda> venda = vendaRepository.findById(id);
        if (venda.isPresent()) {
            // Restaura o estoque dos produtos ao excluir a venda
            for (ItemVenda item : venda.get().getItensVenda()) {
                item.getProduto().setEstoque(item.getProduto().getEstoque() + item.getQuantidade());
                produtoRepository.save(item.getProduto());
            }
            vendaRepository.deleteById(id);
        }
    }
}
