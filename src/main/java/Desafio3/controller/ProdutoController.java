package Desafio3.controller;

import Desafio3.model.Produto;
import Desafio3.service.ProdutoService;
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

    // Criação de um novo produto
    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        Produto novoProduto = produtoService.criarProduto(produto);
        return ResponseEntity.ok(novoProduto);
    }

    // Atualização de um produto existente
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        Produto produto = produtoService.atualizarProduto(id, produtoAtualizado);
        return ResponseEntity.ok(produto);
    }

    // Listagem de todos os produtos
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodosProdutos() {
        List<Produto> produtos = produtoService.listarTodosProdutos();
        return ResponseEntity.ok(produtos);
    }

    // Busca de um produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.buscarPorId(id);
        return produto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Inativação de um produto
    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Void> inativarProduto(@PathVariable Long id) {
        produtoService.inativarProduto(id);
        return ResponseEntity.noContent().build();
    }
}
