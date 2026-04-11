package com.ojuara.helpdesk.services;

import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojuara.helpdesk.model.Chamado;
import com.ojuara.helpdesk.repositories.ChamadoRepository;
import com.ojuara.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository repository;

    public Chamado findById(Integer id){
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objecto não encontrado Id:" + id));
    }

    public List<Chamado> findAll() {
        List<Chamado> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFoundException("Nenhum chamado encontrado");
        }
        return list;
    }

}
