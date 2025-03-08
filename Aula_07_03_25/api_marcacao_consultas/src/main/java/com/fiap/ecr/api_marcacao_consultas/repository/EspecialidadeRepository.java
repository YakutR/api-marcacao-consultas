package com.fiap.ecr.api_marcacao_consultas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fiap.ecr.api_marcacao_consultas.model.Especialidade;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {

    
}
