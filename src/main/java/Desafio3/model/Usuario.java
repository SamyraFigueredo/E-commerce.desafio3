package Desafio3.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private String senha; // Armazenar senhas criptografadas

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;

    @OneToMany(mappedBy = "usuario")
    @JsonBackReference
    private List<Venda> vendas;

    public enum TipoUsuario {
        ADMIN, USER
    }

    // Método para criptografar e definir a senha
    public void setSenha(String senha) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.senha = passwordEncoder.encode(senha);
    }

    // Método para verificar senhas criptografadas (pode ser movido para um serviço separado)
    public static boolean autenticar(String email, String senha, List<Usuario> usuarios, BCryptPasswordEncoder passwordEncoder) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && passwordEncoder.matches(senha, usuario.getSenha())) {
                return usuario.getTipo() == TipoUsuario.ADMIN; // Retorna verdadeiro para ADMIN, falso para USER
            }
        }
        return false; // Retorna falso se não encontrar usuário correspondente
    }

    // Método adicional para redefinir a senha (pode ser implementado via token em serviço)
    public void resetarSenha(String novaSenha) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.senha = passwordEncoder.encode(novaSenha);
    }
}
