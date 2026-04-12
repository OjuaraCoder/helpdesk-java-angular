package com.ojuara.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ojuara.helpdesk.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
    
}
