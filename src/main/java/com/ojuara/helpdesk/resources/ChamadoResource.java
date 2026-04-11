package com.ojuara.helpdesk.resources;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ojuara.helpdesk.model.Chamado;
import com.ojuara.helpdesk.model.dto.ChamadoDto;
import com.ojuara.helpdesk.services.ChamadoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoResource {
    
    @Autowired
    private ChamadoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ChamadoDto> findById(@PathVariable Integer id) {
        Chamado obj = service.findById(id);
        return ResponseEntity.ok().body(new ChamadoDto(obj));

    }

    @GetMapping
    public ResponseEntity<List<ChamadoDto>> findAll(){
        List<Chamado> list = service.findAll();
        List<ChamadoDto> listDto = list.stream().map(obj -> new ChamadoDto(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }
    

}
