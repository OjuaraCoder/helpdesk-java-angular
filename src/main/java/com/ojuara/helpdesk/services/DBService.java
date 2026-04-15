package com.ojuara.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

	@Autowired
	private BCryptPasswordEncoder encoder;
    
    public void instanciaDB(){
        Tecnico t1 = new Tecnico();
		t1.setNome("Ojuara");
		t1.setCpf("59976957009");
		t1.setEmail("teste@gmail.com");
		t1.setSenha(encoder.encode("12345"));
		t1.addPerfil(PerfilEnum.TECNICO);

        Tecnico t2 = new Tecnico();
		t2.setNome("Clara");
		t2.setCpf("86512393007");
		t2.setEmail("clara@gmail.com");
		t2.setSenha(encoder.encode("45688"));
		t2.addPerfil(PerfilEnum.TECNICO);

        Tecnico t3 = new Tecnico();
		t3.setNome("Rodrigues");
		t3.setCpf("67194322064");
		t3.setEmail("rodrigues@gmail.com");
		t3.setSenha(encoder.encode("77788"));
		t3.addPerfil(PerfilEnum.TECNICO);

		Cliente c1 = new Cliente();
		c1.setNome("Fernandes");
		c1.setCpf("11223344556");
		c1.setEmail("cliente@gmail.com");
		c1.addPerfil(PerfilEnum.CLIENTE);
		c1.setSenha(encoder.encode("12355"));

        Cliente c2 = new Cliente();
		c2.setNome("Nira");
		c2.setCpf("63989322001");
		c2.setEmail("nira@gmail.com");
		c2.setSenha(encoder.encode("78910"));
		c2.addPerfil(PerfilEnum.CLIENTE);

		tecnicoRepository.saveAll(Arrays.asList(t1,t2,t3));
		clienteRepository.saveAll(Arrays.asList(c1,c2));

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
