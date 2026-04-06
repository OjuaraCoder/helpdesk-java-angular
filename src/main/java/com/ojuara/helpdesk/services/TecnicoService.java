package com.ojuara.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojuara.helpdesk.model.Tecnico;
import com.ojuara.helpdesk.model.dto.TecnicoDto;
import com.ojuara.helpdesk.repositories.TecnicoRepository;
import com.ojuara.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;

    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objecto não encontrado Id:" + id));
    }

    public List<Tecnico> findAll() {
        List<Tecnico> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFoundException("Nenhum técnico encontrado");
        }
        return list;
    }

    public Tecnico create(TecnicoDto tecnicoDto) {
        tecnicoDto.setId(null); //assegurando que é uma nova requisição
        Tecnico newObj = new Tecnico(tecnicoDto);
        return repository.save(newObj);

    }

}
