package com.ojuara.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ojuara.helpdesk.model.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{
    
}
