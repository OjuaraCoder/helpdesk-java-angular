package com.ojuara.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ojuara.helpdesk.model.Pessoa;
import com.ojuara.helpdesk.model.Cliente;
import com.ojuara.helpdesk.model.dto.ClienteDto;
import com.ojuara.helpdesk.repositories.PessoaRepository;
import com.ojuara.helpdesk.repositories.ClienteRepository;
import com.ojuara.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.ojuara.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

    public Cliente findById(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objecto não encontrado Id:" + id));
    }

    public List<Cliente> findAll() {
        List<Cliente> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFoundException("Nenhum cliente encontrado");
        }
        return list;
    }

    public Cliente create(ClienteDto clienteDto) {
        clienteDto.setId(null); //assegurando que é uma nova requisição
        clienteDto.setSenha(encoder.encode(clienteDto.getSenha()));
        validaCpfEmail(clienteDto);
        
        Cliente newObj = new Cliente(clienteDto);
        return repository.save(newObj);
    }

    public Cliente update(Integer id, ClienteDto obj) {
        obj.setId(id);

        Cliente oldObj = findById(id);
        validaCpfEmail(obj);
        obj.setSenha(encoder.encode(obj.getSenha()));
        oldObj = new Cliente(obj);
        return repository.save(oldObj);
    }

    private void validaCpfEmail(ClienteDto clienteDto) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(clienteDto.getCpf());

        if(obj.isPresent() && obj.get().getId() != clienteDto.getId()){
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
        }

        obj = pessoaRepository.findByEmail(clienteDto.getEmail());
        if(obj.isPresent() && obj.get().getId() != clienteDto.getId()){
            throw new DataIntegrityViolationException("Email já cadastrado no sistema");
        }
    }

    public void delete(Integer id) {
        Cliente obj = findById(id);
        if(obj.getChamados().size() > 0){
            throw new DataIntegrityViolationException("Cliente possui ordem de serviço, operação cancelada");
        }
        repository.delete(obj);

    }
}
