package Desafio3.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;


    @NotBlank
    @Column(nullable = false)
    private String nome;


    @NotBlank
    @Size(min = 8)
    @Column(nullable = false)
    private String senha;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;


    @OneToMany(mappedBy = "usuario")
    @JsonBackReference
    private List<Venda> vendas;

    // Enum que define os tipos possíveis de usuário
    public enum TipoUsuario {
        ADMIN, USER
    }
}
