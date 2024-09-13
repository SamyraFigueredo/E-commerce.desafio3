package Desafio3.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vendas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ElementCollection
    @CollectionTable(name = "venda_itens", joinColumns = @JoinColumn(name = "venda_id"))
    @MapKeyJoinColumn(name = "produto_id")
    @Column(name = "quantidade")
    private Map<Produto, Integer> itens = new HashMap<>();

    private LocalDateTime dataVenda;

    public void adicionarItem(Produto produto, int quantidade) {
        this.itens.put(produto, quantidade);
    }

    public void removerItem(Produto produto) {
        this.itens.remove(produto);
    }
}
