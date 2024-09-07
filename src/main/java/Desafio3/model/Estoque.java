package Desafio3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDateTime;

@Entity
@Table(name = "estoque")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 0, message = "A quantidade deve ser no mínimo 0")
    private Integer quantidade;

    @NotNull(message = "A data de movimentação é obrigatória")
    private LocalDateTime dataMovimentacao;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    @JsonBackReference
    private Produto produto;

    @NotNull(message = "O tipo de movimentação é obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipoMovimentacao;

    public enum TipoMovimentacao {
        ENTRADA,
        SAIDA
    }

    @PrePersist
    public void prePersist() {
        this.dataMovimentacao = LocalDateTime.now();
    }

    // Atualiza o estoque do produto com base na movimentação
    public void atualizarEstoque() {
        if (produto != null) {
            switch (tipoMovimentacao) {
                case ENTRADA:
                    produto.setEstoque(produto.getEstoque() + quantidade);
                    break;
                case SAIDA:
                    produto.setEstoque(produto.getEstoque() - quantidade);
                    if (produto.getEstoque() < 0) {
                        throw new IllegalArgumentException("Estoque não pode ser negativo após a saída.");
                    }
                    break;
            }
        }
    }
}
