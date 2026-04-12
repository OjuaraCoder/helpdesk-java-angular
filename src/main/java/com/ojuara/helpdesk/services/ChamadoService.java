package com.ojuara.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojuara.helpdesk.enums.PrioridadeEnum;
import com.ojuara.helpdesk.enums.StatusEnum;
import com.ojuara.helpdesk.model.Chamado;
import com.ojuara.helpdesk.model.Cliente;
import com.ojuara.helpdesk.model.Tecnico;
import com.ojuara.helpdesk.model.dto.ChamadoDto;
import com.ojuara.helpdesk.repositories.ChamadoRepository;
import com.ojuara.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository repository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private TecnicoService tecnicoService;

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

    public Chamado create(ChamadoDto objDto) {
        Chamado obj = newChamado(objDto);
        return repository.save(obj);
    }

    public Chamado update(Integer id, ChamadoDto objDto) {
        objDto.setId(id);

        Chamado oldObj = findById(id);
        oldObj = newChamado(objDto);
        return repository.save(oldObj);
    }

    private Chamado newChamado(ChamadoDto objDto){
        Tecnico tecnico = tecnicoService.findById(objDto.getTecnico());
        Cliente cliente = clienteService.findById(objDto.getCliente());

        Chamado chamado = new Chamado();
        if(objDto.getId() != null){
            chamado.setId(objDto.getId());
        }

        if(objDto.getStatus().equals(2)){
            chamado.setDataFechamento(LocalDate.now());
        }

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(PrioridadeEnum.toEnum(objDto.getPrioridade()));
        chamado.setStatus(StatusEnum.toEnum(objDto.getStatus()));
        chamado.setTitulo(objDto.getTitulo());
        chamado.setObservacoes(objDto.getObservacoes());

        return chamado;

    }

}
