package br.ufc.projetoiv.model;

import com.google.gson.annotations.SerializedName;

public class ResponseCadastro {
    @SerializedName("message")
    private String message;

    public ResponseCadastro(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseCadastro{" +
                "message='" + message + '\'' +
                '}';
    }
}
