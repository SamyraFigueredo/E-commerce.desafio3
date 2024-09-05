package Desafio3.controller;

import Desafio3.model.ItemVenda;
import Desafio3.service.ItemVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item-vendas")
@Validated
public class ItemVendaController {

    @Autowired
    private ItemVendaService itemVendaService;

    @GetMapping
    public ResponseEntity<List<ItemVenda>> listarTodos() {
        List<ItemVenda> itemVendas = itemVendaService.listarTodos();
        return ResponseEntity.ok(itemVendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemVenda> encontrarPorId(@PathVariable Long id) {
        Optional<ItemVenda> itemVenda = itemVendaService.encontrarPorId(id);
        return itemVenda.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ItemVenda> criar(@RequestBody @Validated ItemVenda itemVenda) {
        try {
            ItemVenda novoItemVenda = itemVendaService.salvar(itemVenda);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoItemVenda);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemVenda> atualizar(@PathVariable Long id, @RequestBody @Validated ItemVenda itemVenda) {
        if (!itemVendaService.encontrarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        try {
            itemVenda.setId(id);
            ItemVenda itemVendaAtualizado = itemVendaService.salvar(itemVenda);
            return ResponseEntity.ok(itemVendaAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            itemVendaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
