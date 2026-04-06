package com.ojuara.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ojuara.helpdesk.enums.PerfilEnum;
import com.ojuara.helpdesk.enums.PrioridadeEnum;
import com.ojuara.helpdesk.enums.StatusEnum;
import com.ojuara.helpdesk.model.Chamado;
import com.ojuara.helpdesk.model.Cliente;
import com.ojuara.helpdesk.model.Tecnico;
import com.ojuara.helpdesk.repositories.ChamadoRepository;
import com.ojuara.helpdesk.repositories.ClienteRepository;
import com.ojuara.helpdesk.repositories.TecnicoRepository;

@Service
public class DBService {

    @Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ChamadoRepository chamadoRepository;
    
    public void instanciaDB(){
        Tecnico t1 = new Tecnico();
		t1.setNome("Ojuara");
		t1.setCpf("12345678900");
		t1.setEmail("teste@gmail.com");
		t1.setSenha("123");
		t1.addPerfil(PerfilEnum.ADMIN);

		Cliente c1 = new Cliente();
		c1.setNome("Fernandes");
		c1.setCpf("11223344556");
		c1.setEmail("cliente@gmail.com");
		c1.addPerfil(PerfilEnum.CLIENTE);
		c1.setSenha("123");

		tecnicoRepository.saveAll(Arrays.asList(t1));
		clienteRepository.saveAll(Arrays.asList(c1));

		Chamado ch = new Chamado();
		ch.setPrioridade(PrioridadeEnum.MEDIA);
		ch.setStatus(StatusEnum.ANDAMENTO);
		ch.setTitulo("Chamado - 01");
		ch.setObservacoes("Primeiro chamado");
		ch.setCliente(c1);
		ch.setTecnico(t1);
		chamadoRepository.saveAll(Arrays.asList(ch));
		
    }

}
