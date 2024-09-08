package Desafio3.controller;

import Desafio3.model.Venda;
import Desafio3.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping
    public ResponseEntity<Venda> criarVenda(@RequestBody Venda venda) {
        try {
            Venda novaVenda = vendaService.criarVenda(venda);
            return ResponseEntity.ok(novaVenda);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Retorna erro de validação
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarVendaPorId(@PathVariable Long id) {
        Optional<Venda> venda = vendaService.buscarPorId(id);
        return venda.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Venda>> listarTodas() {
        List<Venda> vendas = vendaService.listarTodas();
        return ResponseEntity.ok(vendas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVenda(@PathVariable Long id) {
        Optional<Venda> venda = vendaService.buscarPorId(id);
        if (venda.isPresent()) {
            vendaService.deletarVenda(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
