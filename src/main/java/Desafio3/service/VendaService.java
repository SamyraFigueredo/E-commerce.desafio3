package Desafio3.service;

import Desafio3.model.Estoque;
import Desafio3.model.Produto;
import Desafio3.model.Venda;
import Desafio3.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private EstoqueService estoqueService;

    public List<Venda> listarTodas() {
        return vendaRepository.findAll();
    }

    public Optional<Venda> encontrarPorId(Long id) {
        return vendaRepository.findById(id);
    }

    public List<Venda> listarPorUsuario(Long usuarioId) {
        return vendaRepository.findByUsuarioId(usuarioId);
    }

    public List<Venda> listarPorProduto(Long produtoId) {
        return vendaRepository.findByProdutoId(produtoId);
    }

    public Venda salvar(Venda venda) {
        Map<Produto, Integer> itens = venda.getItens();

        // Verifica o estoque de cada item da venda
        for (Map.Entry<Produto, Integer> entry : itens.entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();

            Estoque estoque = estoqueService.buscarPorProduto(produto.getId());
            if (estoque == null || !estoque.reduzirEstoque(quantidade)) {
                throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome());
            }
        }

        return vendaRepository.save(venda);
    }

    public void deletarPorId(Long id) {
        vendaRepository.deleteById(id);
    }
}
