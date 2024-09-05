package Desafio3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<ItemVenda> itensVenda;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataItemVenda;

    @PrePersist
    public void prePersist() {
        this.dataItemVenda = LocalDateTime.now();
    }

    public void validar() {
        if (usuario == null || usuario.getId() == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo e deve ter um ID válido.");
        }
        if (itensVenda == null || itensVenda.isEmpty()) {
            throw new IllegalArgumentException("A venda deve conter pelo menos um item.");
        }
        for (ItemVenda item : itensVenda) {
            if (item.getProduto() == null || item.getProduto().getId() == null) {
                throw new IllegalArgumentException("Cada item de venda deve ter um produto válido.");
            }
            if (item.getQuantidade() == null || item.getQuantidade() <= 0) {
                throw new IllegalArgumentException("A quantidade de cada item de venda deve ser maior que zero.");
            }
            if (item.getPrecoUnitario() == null || item.getPrecoUnitario() <= 0) {
                throw new IllegalArgumentException("O preço unitário de cada item de venda deve ser maior que zero.");
            }
        }
    }
}

