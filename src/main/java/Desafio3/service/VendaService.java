package Desafio3.service;

import Desafio3.model.Venda;
import Desafio3.repository.VendaRepository;
import Desafio3.model.Produto;
import Desafio3.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    // Criar uma nova venda
    @Transactional
    public Venda criarVenda(Venda venda) {
        validarVenda(venda); // Valida a venda antes de persistir
        venda.atualizarEstoque(); // Atualiza o estoque dos produtos

        // Salva a venda no banco de dados
        return vendaRepository.save(venda);
    }

    // Buscar uma venda por ID
    public Venda buscarVendaPorId(Long id) {
        Optional<Venda> venda = vendaRepository.findById(id);
        if (venda.isEmpty()) {
            throw new RuntimeException("Venda não encontrada com ID: " + id);
        }
        return venda.get();
    }

    // Listar todas as vendas
    public Iterable<Venda> listarVendas() {
        return vendaRepository.findAll();
    }

    // Atualizar uma venda existente
    @Transactional
    public Venda atualizarVenda(Long id, Venda vendaAtualizada) {
        Venda vendaExistente = buscarVendaPorId(id);
        vendaAtualizada.setId(vendaExistente.getId());
        validarVenda(vendaAtualizada); // Valida a venda antes de atualizar
        vendaAtualizada.atualizarEstoque(); // Atualiza o estoque dos produtos
        return vendaRepository.save(vendaAtualizada);
    }

    // Deletar uma venda
    @Transactional
    public void deletarVenda(Long id) {
        Venda venda = buscarVendaPorId(id);
        vendaRepository.delete(venda);
    }

    // Valida a venda
    private void validarVenda(Venda venda) {
        if (venda.getUsuario() == null || venda.getUsuario().getId() == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo e deve ter um ID válido.");
        }

        if (venda.getProdutos() == null || venda.getProdutos().isEmpty()) {
            throw new IllegalArgumentException("A venda deve conter pelo menos um produto.");
        }

        for (Map.Entry<Produto, Integer> entry : venda.getProdutos().entrySet()) {
            Produto produto = entry.getKey();
            Integer quantidade = entry.getValue();

            if (produto == null || produto.getId() == null) {
                throw new IllegalArgumentException("Cada produto de venda deve ser válido.");
            }

            if (quantidade == null || quantidade <= 0) {
                throw new IllegalArgumentException("A quantidade de cada produto deve ser maior que zero.");
            }

            if (quantidade > produto.getEstoque()) {
                throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome());
            }
        }
    }
}
