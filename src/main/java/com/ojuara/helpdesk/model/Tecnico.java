package com.ojuara.helpdesk.model;

import java.util.ArrayList;
import java.util.List;

import com.ojuara.helpdesk.enums.PerfilEnum;

public class Tecnico extends Pessoa {

    private List<Chamado> chamados = new ArrayList<>();

    public Tecnico() {
        super();
        addPerfil(PerfilEnum.TECNICO);
    }

    public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        addPerfil(PerfilEnum.TECNICO);
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }
}
