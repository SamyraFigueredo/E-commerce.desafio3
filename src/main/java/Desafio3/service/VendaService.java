package Desafio3.service;

import Desafio3.model.Venda;
import Desafio3.repository.VendaRepository;
import Desafio3.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Venda criarVenda(Venda venda) {
        venda.validar();

        // Validação e atualização de estoque dos produtos
        venda.getItensVenda().forEach(item -> {
            var produto = item.getProduto();
            if (produto.getEstoque() < item.getQuantidade()) {
                throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome());
            }
            produto.setEstoque(produto.getEstoque() - item.getQuantidade());
            produtoRepository.save(produto);
        });

        return vendaRepository.save(venda);
    }

    public List<Venda> listarVendas() {
        return vendaRepository.findAll();
    }

    public Optional<Venda> obterVendaPorId(Long id) {
        return vendaRepository.findById(id);
    }

    public void deletarVenda(Long id) {
        vendaRepository.deleteById(id);
    }
}
