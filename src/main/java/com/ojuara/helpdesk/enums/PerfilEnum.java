package com.ojuara.helpdesk.enums;

public enum PerfilEnum {

    ADMIN(0,"ROLE_ADMIN"),
    CLIENTE(1,"ROLE_CLIENTE"),
    TECNICO(2,"ROLE_TECNICO");

    private int codigo;
    private String descricao;

    private PerfilEnum(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static PerfilEnum toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }

        for (PerfilEnum perfil : PerfilEnum.values()) {
            if (codigo.equals(perfil.getCodigo())) {
                return perfil;
            }
        }

        throw new IllegalArgumentException("Perfil inválido: " + codigo);
    }

}
