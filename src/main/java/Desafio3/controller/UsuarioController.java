package Desafio3.controller;

import Desafio3.UsuarioNaoEncontradoException;
import Desafio3.model.Usuario;
import Desafio3.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.criarUsuario(usuario);
            return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.buscarUsuarioPorId(id);
        return usuario.map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);
            return new ResponseEntity<>(usuarioAtualizado, HttpStatus.OK);
        } catch (UsuarioNaoEncontradoException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deletarUsuario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UsuarioNaoEncontradoException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/autenticar")
    public ResponseEntity<Void> autenticarUsuario(@RequestParam String email, @RequestParam String senha) {
        boolean autenticado = usuarioService.autenticarUsuario(email, senha);
        if (autenticado) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/{id}/resetar-senha")
    public ResponseEntity<Void> redefinirSenha(@PathVariable Long id, @RequestParam String novaSenha) {
        try {
            usuarioService.redefinirSenha(id, novaSenha);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UsuarioNaoEncontradoException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
