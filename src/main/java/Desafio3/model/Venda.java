package Desafio3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "venda")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataVenda;

    @ElementCollection
    @CollectionTable(name = "produto_venda", joinColumns = @JoinColumn(name = "venda_id"))
    @MapKeyJoinColumn(name = "produto_id")
    @Column(name = "quantidade")
    private Map<Produto, Integer> produtos = new HashMap<>();

    @PrePersist
    public void prePersist() {
        this.dataVenda = LocalDateTime.now();
    }

    // Método de validação
    public void validar() {
        if (usuario == null || usuario.getId() == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo e deve ter um ID válido.");
        }
        if (produtos == null || produtos.isEmpty()) {
            throw new IllegalArgumentException("A venda deve conter pelo menos um produto.");
        }
        for (Map.Entry<Produto, Integer> entry : produtos.entrySet()) {
            Produto produto = entry.getKey();
            Integer quantidade = entry.getValue();
            if (produto == null || produto.getId() == null) {
                throw new IllegalArgumentException("Cada produto de venda deve ser válido.");
            }
            if (quantidade == null || quantidade <= 0) {
                throw new IllegalArgumentException("A quantidade de cada produto deve ser maior que zero.");
            }
            if (quantidade > produto.getEstoque()) {
                throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome());
            }
        }
    }

    // Atualizar o estoque dos produtos após a venda
    public void atualizarEstoque() {
        for (Map.Entry<Produto, Integer> entry : produtos.entrySet()) {
            Produto produto = entry.getKey();
            Integer quantidade = entry.getValue();
            if (produto != null) {
                if (produto.getEstoque() < quantidade) {
                    throw new IllegalArgumentException("Estoque insuficiente para o produto: " + produto.getNome());
                }
                produto.setEstoque(produto.getEstoque() - quantidade);
            }
        }
    }
}
