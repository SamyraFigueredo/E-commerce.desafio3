package Desafio3.controller;

import Desafio3.model.Usuario;
import Desafio3.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.criarUsuario(usuario);
        return ResponseEntity.ok(novoUsuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obterUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.obterUsuarioPorId(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Método para resetar a senha do usuário
    @PostMapping("/{id}/reset-senha")
    public ResponseEntity<String> resetarSenha(@PathVariable Long id, @RequestBody PasswordResetRequest request) {
        boolean sucesso = usuarioService.resetarSenha(id, request.getNovaSenha());
        if (sucesso) {
            return ResponseEntity.ok("Senha alterada com sucesso!");
        } else {
            return ResponseEntity.badRequest().body("Usuário não encontrado ou erro ao alterar a senha!");
        }
    }

    public static class PasswordResetRequest {
        private String novaSenha;

        public String getNovaSenha() {
            return novaSenha;
        }

        public void setNovaSenha(String novaSenha) {
            this.novaSenha = novaSenha;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
