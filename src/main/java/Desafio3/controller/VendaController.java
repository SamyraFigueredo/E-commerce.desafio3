package Desafio3.controller;

import Desafio3.model.Venda;
import Desafio3.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping
    public ResponseEntity<?> criarVenda(@RequestBody Venda venda) {
        try {
            vendaService.criarVenda(venda);
            return ResponseEntity.ok("Venda criada com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarVendaPorId(@PathVariable Long id) {
        try {
            Venda venda = vendaService.buscarVendaPorId(id);
            return new ResponseEntity<>(venda, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarVenda(@PathVariable Long id, @RequestBody Venda venda) {
        try {
            Venda vendaAtualizada = vendaService.atualizarVenda(id, venda);
            return new ResponseEntity<>(vendaAtualizada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVenda(@PathVariable Long id) {
        try {
            vendaService.deletarVenda(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}