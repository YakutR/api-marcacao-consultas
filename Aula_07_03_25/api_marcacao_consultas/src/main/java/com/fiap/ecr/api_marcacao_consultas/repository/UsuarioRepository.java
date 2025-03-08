package com.fiap.ecr.api_marcacao_consultas.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fiap.ecr.api_marcacao_consultas.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    
}
