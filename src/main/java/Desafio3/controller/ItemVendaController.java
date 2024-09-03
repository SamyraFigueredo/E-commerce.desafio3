package Desafio3.controller;

import Desafio3.model.ItemVenda;
import Desafio3.service.ItemVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/itensvenda")
public class ItemVendaController {

    @Autowired
    private ItemVendaService itemVendaService;

    @PostMapping
    public ResponseEntity<ItemVenda> criarItemVenda(@RequestBody ItemVenda itemVenda) {
        try {
            ItemVenda novoItemVenda = itemVendaService.criarItemVenda(itemVenda);
            return ResponseEntity.ok(novoItemVenda);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemVenda> obterItemVendaPorId(@PathVariable Long id) {
        Optional<ItemVenda> itemVenda = itemVendaService.obterItemVendaPorId(id);
        return itemVenda.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItemVenda(@PathVariable Long id) {
        itemVendaService.deletarItemVenda(id);
        return ResponseEntity.noContent().build();
    }
}
