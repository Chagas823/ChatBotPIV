package br.ufc.projetoiv.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public  class Token {
    @SerializedName("token")
    private static String token;

    public Token(String token) {
        this.token = token;
    }

    public static String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public String toString() {
        return "token:" + this.getToken();
    }
}
