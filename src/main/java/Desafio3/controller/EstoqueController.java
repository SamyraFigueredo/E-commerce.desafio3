package Desafio3.controller;

import Desafio3.model.Estoque;
import Desafio3.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @PostMapping
    public ResponseEntity<Estoque> criarMovimentacao(@RequestBody Estoque estoque) {
        try {
            Estoque novaMovimentacao = estoqueService.criarMovimentacao(estoque);
            return new ResponseEntity<>(novaMovimentacao, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estoque> buscarPorId(@PathVariable Long id) {
        try {
            Estoque estoque = estoqueService.buscarPorId(id);
            return new ResponseEntity<>(estoque, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estoque> atualizarMovimentacao(@PathVariable Long id, @RequestBody Estoque estoqueAtualizado) {
        try {
            Estoque estoque = estoqueService.atualizarMovimentacao(id, estoqueAtualizado);
            return new ResponseEntity<>(estoque, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirMovimentacao(@PathVariable Long id) {
        try {
            estoqueService.excluirMovimentacao(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
