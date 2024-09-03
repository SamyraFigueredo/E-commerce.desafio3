package Desafio3.model;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ItemVenda> itensVenda;


    @Column(nullable = false)
    private LocalDateTime dataItemVenda;

    @PrePersist
    public void prePersist() {
        this.dataItemVenda = LocalDateTime.now();
    }

    // Método para validar a venda antes de persistir
    public void validar() {
        if (itensVenda == null || itensVenda.isEmpty()) {
            throw new IllegalArgumentException("A venda deve conter pelo menos um item.");
        }
    }
}
