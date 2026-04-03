package com.ojuara.helpdesk.enums;

public enum PrioridadeEnum {

    BAIXA(0,"BAIXA"),
    MEDIA(1,"MEDIA"),
    ALTA(2,"ALTA");

    private int codigo;
    private String descricao;

    private PrioridadeEnum(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static PrioridadeEnum toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }

        for (PrioridadeEnum prioridade : PrioridadeEnum.values()) {
            if (codigo.equals(prioridade.getCodigo())) {
                return prioridade;
            }
        }

        throw new IllegalArgumentException("Prioridade inválida: " + codigo);
    }


}
