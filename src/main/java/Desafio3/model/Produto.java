package Desafio3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do produto é obrigatório")
    private String nome;

    private String descricao;

    @NotNull(message = "O preço do produto é obrigatório")
    @Min(value = 0, message = "O preço deve ser positivo")
    private Double preco;

    @NotNull(message = "O estoque do produto é obrigatório")
    @Min(value = 0, message = "O estoque deve ser no mínimo 0")
    private Integer estoque;

    @NotNull(message = "O status ativo do produto é obrigatório")
    private Boolean ativo;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Estoque> movimentacoesEstoque;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<Venda> vendas;

    public void validar() {
        if (estoque < 0) {
            throw new IllegalArgumentException("O estoque não pode ser negativo");
        }
        if (preco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo");
        }
    }
}
