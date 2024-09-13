package Desafio3.controller;

import Desafio3.model.Produto;
import Desafio3.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.encontrarPorId(id);
        return produto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/ativos")
    public List<Produto> listarAtivos() {
        return produtoService.listarAtivos();
    }

    @PostMapping
    public Produto criarProduto(@Valid @RequestBody Produto produto) {
        return produtoService.salvar(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @Valid @RequestBody Produto produto) {
        Optional<Produto> produtoExistente = produtoService.encontrarPorId(id);
        if (produtoExistente.isPresent()) {
            produto.setId(id);
            return ResponseEntity.ok(produtoService.salvar(produto));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Novo método PATCH para inativar produto
    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Void> inativarProduto(@PathVariable Long id) {
        Optional<Produto> produtoExistente = produtoService.encontrarPorId(id);
        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();
            produto.setStatus(Produto.Status.INATIVO);
            produtoService.salvar(produto);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
