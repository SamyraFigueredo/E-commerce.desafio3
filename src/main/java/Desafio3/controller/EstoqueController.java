package Desafio3.controller;

import Desafio3.model.Estoque;
import Desafio3.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping("/{produtoId}")
    public ResponseEntity<Estoque> encontrarPorProdutoId(@PathVariable Long produtoId) {
        Estoque estoque = estoqueService.encontrarPorProdutoId(produtoId);
        return estoque != null ? ResponseEntity.ok(estoque) : ResponseEntity.notFound().build();
    }

    @PostMapping("/adicionar/{produtoId}")
    public ResponseEntity<Void> adicionarEstoque(@PathVariable Long produtoId, @RequestParam int quantidade) {
        estoqueService.adicionarEstoque(produtoId, quantidade);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reduzir/{produtoId}")
    public ResponseEntity<Void> reduzirEstoque(@PathVariable Long produtoId, @RequestParam int quantidade) {
        try {
            estoqueService.reduzirEstoque(produtoId, quantidade);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

