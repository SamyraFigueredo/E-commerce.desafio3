package Desafio3.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Username é obrigatório")
    private String username;

    @Email(message = "Email deve ser válido")
    @NotEmpty(message = "Email é obrigatório")
    private String email;

    @NotEmpty(message = "Senha é obrigatória")
    private String senha;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Transient // Campo não persistido no banco
    private String autenticado;

    public String getAutenticado() {
        return autenticado != null && autenticado.equalsIgnoreCase("sim") ? "sim" : null;
    }

    public enum Role {
        USER, ADMIN
    }
}

