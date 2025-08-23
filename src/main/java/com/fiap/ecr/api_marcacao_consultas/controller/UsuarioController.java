@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final JwtTokenProvider jwtTokenProvider;

    // ... outros endpoints

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Usuario usuario = usuarioService.autenticar(loginRequest.getEmail(), loginRequest.getSenha());
            String token = jwtTokenProvider.gerarToken(usuario.getEmail());
            return ResponseEntity.ok().body(Map.of("token", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

    // NOVO: Endpoint para buscar usuário atual baseado no JWT
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        try {
            // Remove "Bearer " do header
            String token = authHeader.substring(7);
            
            // Extrai o email do token
            String email = jwtTokenProvider.obterEmailDoToken(token);
            
            // Busca o usuário pelo email
            Usuario usuario = usuarioService.buscarPorEmail(email);
            
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
    }

    // NOVO CÓDIGO - Adicionar no final da classe UsuarioController

    /**
     * Endpoint para admin alterar senha de qualquer usuário
     */
    @PutMapping("/{id}/senha")
    public ResponseEntity<?> alterarSenhaUsuario(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            String novaSenha = request.get("novaSenha");
            if (novaSenha == null || novaSenha.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nova senha é obrigatória");
            }
            
            Usuario usuario = usuarioService.alterarSenha(id, novaSenha);
            return ResponseEntity.ok().body(Map.of("message", "Senha alterada com sucesso", "usuario", usuario.getNome()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}

