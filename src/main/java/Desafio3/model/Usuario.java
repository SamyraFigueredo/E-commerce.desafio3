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

    public enum TipoUsuario {
        ADMIN, USER
    }

    // autenticar o usuário
    public static int autenticar(String email, String senha, List<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                return usuario.getTipo() == TipoUsuario.ADMIN ? 1 : 0; // 1 para ADMIN, 0 para USER
            }
        }
        return -1; // Retorna -1 se não encontrar usuário correspondente
    }
}
