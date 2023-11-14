package br.ufc.projetoiv.model;

import com.google.gson.annotations.SerializedName;

public class Usuario {
    @SerializedName("id_user")
    private int id_user;
    @SerializedName("nome")
    private String nome;
    @SerializedName("email")
    private String email;
    @SerializedName("senha")
    private String senha;

    public Usuario(int id_user, String nome, String email, String senha) {
        this.id_user = id_user;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    @Override
    public String toString() {
        return "Usuario{" +
                "id_user=" + id_user +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
