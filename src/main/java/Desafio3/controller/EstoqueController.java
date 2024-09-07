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

    // Criar ou atualizar movimentação de estoque
    @PostMapping
    public ResponseEntity<Estoque> criarOuAtualizarEstoque(@RequestBody Estoque estoque) {
        try {
            Estoque estoqueSalvo = estoqueService.criarOuAtualizarEstoque(estoque);
            return ResponseEntity.ok(estoqueSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Buscar movimentação de estoque por ID
    @GetMapping("/{id}")
    public ResponseEntity<Estoque> buscarPorId(@PathVariable Long id) {
        try {
            Estoque estoque = estoqueService.buscarPorId(id);
            return ResponseEntity.ok(estoque);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Remover movimentação de estoque por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPorId(@PathVariable Long id) {
        try {
            estoqueService.removerPorId(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
