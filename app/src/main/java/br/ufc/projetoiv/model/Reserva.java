package br.ufc.projetoiv.model;

import com.google.gson.annotations.SerializedName;

public class Reserva {


    @SerializedName("id_reserva")
    private int id_reserva;
    @SerializedName("sala_id")
    private int sala_id;

    @SerializedName("data_reserva")
    private String data_reserva;
    @SerializedName("horario_inicio")
    private String horario_inicio;
    @SerializedName("horario_fim")
    private String horario_fim;
    @SerializedName("descricao")
    private String descricao;
    @SerializedName("tipo_sala")
    private String tipo_sala;
    @SerializedName("localizacao")
    private String localizacao;
    @SerializedName("capacidade")
    private int capacidade;

    public Reserva(int id_reserva, int sala_id, String data_reserva, String horario_inicio, String horario_fim, String descricao, String tipo_sala, String localizacao, int capacidade) {
        this.id_reserva = id_reserva;
        this.sala_id = sala_id;
        this.data_reserva = data_reserva;
        this.horario_inicio = horario_inicio;
        this.horario_fim = horario_fim;
        this.descricao = descricao;
        this.tipo_sala = tipo_sala;
        this.localizacao = localizacao;
        this.capacidade = capacidade;
    }

    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public int getSala_id() {
        return sala_id;
    }

    public void setSala_id(int sala_id) {
        this.sala_id = sala_id;
    }



    public String getData_reserva() {
        return data_reserva;
    }

    public void setData_reserva(String data_reserva) {
        this.data_reserva = data_reserva;
    }

    public String getHorario_inicio() {
        return horario_inicio;
    }

    public void setHorario_inicio(String horario_inicio) {
        this.horario_inicio = horario_inicio;
    }

    public String getHorario_fim() {
        return horario_fim;
    }

    public void setHorario_fim(String horario_fim) {
        this.horario_fim = horario_fim;
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

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id_reserva=" + id_reserva +
                ", sala_id=" + sala_id +
                ", data_reserva='" + data_reserva + '\'' +
                ", horario_inicio='" + horario_inicio + '\'' +
                ", horario_fim='" + horario_fim + '\'' +
                '}';
    }
}
