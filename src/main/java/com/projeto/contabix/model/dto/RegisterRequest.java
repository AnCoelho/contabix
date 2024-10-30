package com.projeto.contabix.model.dto;

public class RegisterRequest {
    private String nome;
    private String cnpj;
    private String email;
    private String senha;
    private Long id_tipo_usuario;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public Long getId_tipo_usuario() {
        return id_tipo_usuario;
    }

    public void setId_tipo_usuario(Long id_tipo_usuario) {
        this.id_tipo_usuario = id_tipo_usuario;
    }
}
