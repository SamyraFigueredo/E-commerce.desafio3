package Desafio3.service;

import Desafio3.model.Usuario;
import Desafio3.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Usuario criarUsuario(Usuario usuario) {
        // Hash da senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public int autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario.getTipo() == Usuario.TipoUsuario.ADMIN ? 1 : 0; // 1 para ADMIN, 0 para USER
        }
        return -1; // Retorna -1 se não encontrar usuário correspondente
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obterUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuarioExistente = usuarioOptional.get();
            usuarioExistente.setNome(usuarioAtualizado.getNome());
            usuarioExistente.setEmail(usuarioAtualizado.getEmail());
            usuarioExistente.setTipo(usuarioAtualizado.getTipo());

            // Atualizar a senha se ela for alterada
            if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
                usuarioExistente.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
            }

            return usuarioRepository.save(usuarioExistente);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    public boolean resetarSenha(Long id, String novaSenha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setSenha(passwordEncoder.encode(novaSenha)); // Encriptando a nova senha
            usuarioRepository.save(usuario);
            return true;
        } else {
            return false;
        }
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
