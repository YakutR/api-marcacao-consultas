package com.fiap.ecr.api_marcacao_consultas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fiap.ecr.api_marcacao_consultas.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByUsuarioId(Long usuarioId);
    
}
