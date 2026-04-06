package com.ojuara.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ojuara.helpdesk.model.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>{
    
}
