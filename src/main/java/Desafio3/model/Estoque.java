package Desafio3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
    private Produto produto;

    @NotNull(message = "O tipo de movimentação é obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipoMovimentacao;

    @PrePersist
    public void prePersist() {
        this.dataMovimentacao = LocalDateTime.now();
    }

    public void atualizarEstoque() {
        if (tipoMovimentacao == TipoMovimentacao.ENTRADA) {
            produto.setEstoque(produto.getEstoque() + quantidade);
        } else if (tipoMovimentacao == TipoMovimentacao.SAIDA) {
            if (produto.getEstoque() < quantidade) {
                throw new IllegalArgumentException("Estoque insuficiente para a saída");
            }
            produto.setEstoque(produto.getEstoque() - quantidade);
        }
    }
}
