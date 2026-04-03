package com.ojuara.helpdesk.enums;

public enum StatusEnum {

    ABERTO(0,"ABERTO"),
    ANDAMENTO(1,"ANDAMENTO"),
    ENCERRADO(2,"ENCERRADO");

    private int codigo;
    private String descricao;

    private StatusEnum(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusEnum toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }

        for (StatusEnum status : StatusEnum.values()) {
            if (codigo.equals(status.getCodigo())) {
                return status;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + codigo);
    }


}
