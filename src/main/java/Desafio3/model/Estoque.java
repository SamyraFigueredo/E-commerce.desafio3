package Desafio3.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "estoques")
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    private int quantidadeDisponivel;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<Venda> vendas;

    public int getTotalVendas() {
        return vendas != null ? vendas.size() : 0;
    }

    public void adicionarEstoque(int quantidade) {
        this.quantidadeDisponivel += quantidade;
    }

    public boolean reduzirEstoque(int quantidade) {
        if (quantidadeDisponivel >= quantidade) {
            this.quantidadeDisponivel -= quantidade;
            return true;
        }
        return false;
    }
}
