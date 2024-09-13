package Desafio3.controller;

import Desafio3.model.Venda;
import Desafio3.service.VendaService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Venda> criarVenda(@Valid @RequestBody Venda venda) {
        if (venda.getItens().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            Venda novaVenda = vendaService.salvar(venda);
            return ResponseEntity.ok(novaVenda);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);  // Lida com erros como estoque insuficiente
        }
    }

    @GetMapping
    public List<Venda> listarTodas() {
        return vendaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> encontrarPorId(@PathVariable Long id) {
        Optional<Venda> venda = vendaService.encontrarPorId(id);
        return venda.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Venda> listarPorUsuario(@PathVariable Long usuarioId) {
        return vendaService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/produto/{produtoId}")
    public List<Venda> listarPorProduto(@PathVariable Long produtoId) {
        return vendaService.listarPorProduto(produtoId);
    }

    // Método para deletar venda por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        vendaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
