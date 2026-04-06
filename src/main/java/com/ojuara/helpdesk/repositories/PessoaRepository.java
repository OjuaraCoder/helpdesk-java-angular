package com.ojuara.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ojuara.helpdesk.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{
    
}
