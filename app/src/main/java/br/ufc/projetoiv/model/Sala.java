package br.ufc.projetoiv.model;

import com.google.gson.annotations.SerializedName;

public class Sala {
    @SerializedName("id_sala")
    private int idSala;
    @SerializedName("capacidade")
    private int capacidade;
    @SerializedName("localizacao")
    private String localizacao;
    @SerializedName("descricao")
    private String descricao;
    @SerializedName("tipo_sala")
    private String tipo_sala;

    public Sala(int idSala, int capacidade, String localizacao, String descricao, String tipo_sala) {
        this.idSala = idSala;
        this.capacidade = capacidade;
        this.localizacao = localizacao;
        this.descricao = descricao;
        this.tipo_sala = tipo_sala;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo_sala() {
        return tipo_sala;
    }

    public void setTipo_sala(String tipo_sala) {
        this.tipo_sala = tipo_sala;
    }
}
