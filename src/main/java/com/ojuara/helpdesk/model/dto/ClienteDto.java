package com.ojuara.helpdesk.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ojuara.helpdesk.enums.PerfilEnum;
import com.ojuara.helpdesk.model.Cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ClienteDto implements Serializable {

    private Integer id;
    @NotNull(message = "O campo nome é obrigatorio")
    @NotBlank(message = "O campo nome não pode conter espaços em branco")
    private String nome;
    @NotNull(message = "O campo cpf é obrigatorio")
    @NotBlank(message = "O campo cpf não pode conter espaços em branco")
    private String cpf;
    @NotNull(message = "O campo email é obrigatorio")
    @NotBlank(message = "O campo email não pode conter espaços em branco")
    private String email;
    @NotNull(message = "O campo senha é obrigatorio")
    @NotBlank(message = "O campo senha não pode conter espaços em branco")
    private String senha;
    
    private Set<Integer> perfis = new HashSet<>();

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCriacao = LocalDate.now();

    public ClienteDto(){
        super();
    }

    public ClienteDto(Cliente obj){
        super();
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.senha = obj.getSenha();
        this.dataCriacao = obj.getDataCriacao();
        this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<PerfilEnum> getPerfis() {
        return perfis.stream().map(x -> PerfilEnum.toEnum(x)).collect(Collectors.toSet());
    }

    public void addPerfil(PerfilEnum perfil) {
        this.perfis.add(perfil.getCodigo());
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    

}
