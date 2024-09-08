package Desafio3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "item_venda")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "preco_unitario", nullable = false)
    @NotNull(message = "O preço unitário é obrigatório")
    @Min(value = 0, message = "O preço unitário deve ser positivo")
    private Double precoUnitario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    @NotNull(message = "O produto é obrigatório")
    private Produto produto;

    @Column(nullable = false)
    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser maior que zero")
    private Integer quantidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venda_id", nullable = false)
    @JsonIgnore
    private Venda venda;

    @Column(nullable = false)
    private LocalDateTime dataItemVenda;

    @PrePersist
    public void prePersist() {
        this.dataItemVenda = LocalDateTime.now();
    }

    // Método para validar o item de venda
    public void validar() {
        if (produto == null || produto.getId() == null) {
            throw new IllegalArgumentException("Produto inválido.");
        }
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        if (precoUnitario == null || precoUnitario <= 0) {
            throw new IllegalArgumentException("Preço unitário deve ser maior que zero.");
        }
    }
}
