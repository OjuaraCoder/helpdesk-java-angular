package com.ojuara.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ojuara.helpdesk.model.Pessoa;
import com.ojuara.helpdesk.model.Tecnico;
import com.ojuara.helpdesk.model.dto.TecnicoDto;
import com.ojuara.helpdesk.repositories.PessoaRepository;
import com.ojuara.helpdesk.repositories.TecnicoRepository;
import com.ojuara.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.ojuara.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

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
        tecnicoDto.setSenha(encoder.encode(tecnicoDto.getSenha()));
        validaCpfEmail(tecnicoDto);
        
        Tecnico newObj = new Tecnico(tecnicoDto);
        return repository.save(newObj);
    }

    public Tecnico update(Integer id, TecnicoDto obj) {
        obj.setId(id);

        Tecnico oldObj = findById(id);
        validaCpfEmail(obj);
        obj.setSenha(encoder.encode(obj.getSenha()));
        oldObj = new Tecnico(obj);
        return repository.save(oldObj);
    }

    private void validaCpfEmail(TecnicoDto tecnicoDto) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(tecnicoDto.getCpf());

        if(obj.isPresent() && obj.get().getId() != tecnicoDto.getId()){
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
        }

        obj = pessoaRepository.findByEmail(tecnicoDto.getEmail());
        if(obj.isPresent() && obj.get().getId() != tecnicoDto.getId()){
            throw new DataIntegrityViolationException("Email já cadastrado no sistema");
        }
    }

    public void delete(Integer id) {
        Tecnico obj = findById(id);
        if(obj.getChamados().size() > 0){
            throw new DataIntegrityViolationException("Tecnico possui ordem de serviço, operação cancelada");
        }
        repository.delete(obj);

    }
}
