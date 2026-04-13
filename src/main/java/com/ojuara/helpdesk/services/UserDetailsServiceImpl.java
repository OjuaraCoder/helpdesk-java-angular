package com.ojuara.helpdesk.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ojuara.helpdesk.model.Pessoa;
import com.ojuara.helpdesk.repositories.PessoaRepository;
import com.ojuara.helpdesk.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PessoaRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Pessoa> pessoa = repository.findByEmail(email);
        if(pessoa.isPresent()){
            return new UserSS(pessoa.get().getId(),
                              pessoa.get().getEmail(),
                              pessoa.get().getSenha(),
                              pessoa.get().getPerfis());
        }
        throw new UsernameNotFoundException("Email não encontrado: " + email);
    }

}
