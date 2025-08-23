@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ... outros métodos

    public Usuario autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        return usuario;
    }

    // NOVO: Método público para buscar usuário por email
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // NOVO CÓDIGO - Adicionar no final da classe UsuarioService

    /**
     * Altera a senha de um usuário específico (apenas admin)
     */
    public Usuario alterarSenha(Long usuarioId, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        // Criptografa a nova senha
        String senhaCriptografada = passwordEncoder.encode(novaSenha);
        usuario.setSenha(senhaCriptografada);
        
        return usuarioRepository.save(usuario);
    }

}