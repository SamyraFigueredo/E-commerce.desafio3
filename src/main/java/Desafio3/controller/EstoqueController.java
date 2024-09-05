package Desafio3.controller;

import Desafio3.model.Produto;
import Desafio3.service.EstoqueService;
import Desafio3.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private ProdutoService produtoService;

    // Adicionar estoque a um produto
    @PostMapping("/adicionar/{produtoId}/{quantidade}")
    public ResponseEntity<String> adicionarEstoque(@PathVariable Long produtoId, @PathVariable int quantidade) {
        Produto produto = produtoService.buscarPorId(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
        estoqueService.adicionarEstoque(produto, quantidade);
        return ResponseEntity.ok("Estoque adicionado com sucesso.");
    }

    // Verificar a quantidade de estoque de um produto
    @GetMapping("/verificar/{produtoId}")
    public ResponseEntity<Integer> verificarQuantidade(@PathVariable Long produtoId) {
        Produto produto = produtoService.buscarPorId(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
        int quantidade = estoqueService.verificarQuantidade(produto);
        return ResponseEntity.ok(quantidade);
    }

    // Remover estoque de um produto
    @PostMapping("/remover/{produtoId}/{quantidade}")
    public ResponseEntity<String> removerEstoque(@PathVariable Long produtoId, @PathVariable int quantidade) {
        Produto produto = produtoService.buscarPorId(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
        estoqueService.removerEstoque(produto, quantidade);
        return ResponseEntity.ok("Estoque removido com sucesso.");
    }
}
