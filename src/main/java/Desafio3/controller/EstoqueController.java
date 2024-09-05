package Desafio3.controller;

import Desafio3.model.Estoque;
import Desafio3.service.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estoques")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping
    public ResponseEntity<List<Estoque>> listarTodos() {
        List<Estoque> estoques = estoqueService.listarTodos();
        return ResponseEntity.ok(estoques);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estoque> buscarPorId(@PathVariable Long id) {
        Optional<Estoque> estoque = estoqueService.buscarPorId(id);
        return estoque.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Estoque> criar(@Valid @RequestBody Estoque estoque) {
        Estoque estoqueCriado = estoqueService.criar(estoque);
        return ResponseEntity.status(HttpStatus.CREATED).body(estoqueCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estoque> atualizar(@PathVariable Long id, @Valid @RequestBody Estoque estoque) {
        try {
            Estoque estoqueAtualizado = estoqueService.atualizar(id, estoque);
            return ResponseEntity.ok(estoqueAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            estoqueService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
