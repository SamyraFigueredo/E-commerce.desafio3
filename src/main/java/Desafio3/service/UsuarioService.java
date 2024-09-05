package Desafio3.service;

import Desafio3.UsuarioNaoEncontradoException;
import Desafio3.model.Usuario;
import Desafio3.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para criar um novo usuário
    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Método para listar todos os usuários
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com id: " + id));
        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com id: " + id));
        usuarioRepository.delete(usuario);
    }

    public boolean autenticarUsuario(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return usuario.getSenha().equals(senha);
        }
        return false;
    }


    public void redefinirSenha(Long id, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com id: " + id));
        usuario.setSenha(novaSenha);
        usuarioRepository.save(usuario);
    }
}
