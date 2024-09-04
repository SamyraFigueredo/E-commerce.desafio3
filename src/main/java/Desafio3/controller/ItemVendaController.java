package Desafio3.controller;

import Desafio3.model.ItemVenda;
import Desafio3.service.ItemVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/item-vendas")
public class ItemVendaController {

    @Autowired
    private ItemVendaService itemVendaService;

    @PostMapping
    public ResponseEntity<ItemVenda> criarItemVenda(@RequestBody ItemVenda itemVenda) {
        try {
            ItemVenda novoItemVenda = itemVendaService.criarItemVenda(itemVenda);
            return new ResponseEntity<>(novoItemVenda, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemVenda> obterItemVenda(@PathVariable Long id) {
        try {
            ItemVenda itemVenda = itemVendaService.obterItemVendaPorId(id);
            return new ResponseEntity<>(itemVenda, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ItemVenda>> listarItensVenda() {
        List<ItemVenda> itensVenda = itemVendaService.listarItensVenda();
        return new ResponseEntity<>(itensVenda, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemVenda> atualizarItemVenda(@PathVariable Long id, @RequestBody ItemVenda itemVenda) {
        try {
            ItemVenda itemVendaAtualizado = itemVendaService.atualizarItemVenda(id, itemVenda);
            return new ResponseEntity<>(itemVendaAtualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletarItemVenda(@PathVariable Long id) {
        try {
            itemVendaService.deletarItemVenda(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
