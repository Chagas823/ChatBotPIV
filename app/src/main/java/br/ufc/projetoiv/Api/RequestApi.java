package br.ufc.projetoiv.Api;

import android.widget.Adapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import br.ufc.projetoiv.model.Token;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RequestApi {
    private OkHttpClient client;

    private static String url = "https://chat-bot-novo.vercel.app";
    private static String apiKey = "24691f4191mshd9b12305fed2685p125dd4jsndb4de3f92ade";


    private Request request;


    public RequestApi(String endpoint){
        client = new OkHttpClient();
        request = new Request.Builder().url(url+endpoint).get()
                .build();

    }
    public RequestApi(String endpoint, RequestBody requestBody){
        client = new OkHttpClient();

        request = new Request.Builder().url(url+endpoint)
                .post(requestBody).build();
    }
    public RequestApi(String endpoint, RequestBody requestBody, String token){
        client = new OkHttpClient();
        Gson gson = new Gson();
        String tokenString = JsonParser.parseString(token).getAsJsonObject().get("token").getAsString();

        request = new Request.Builder().url(url+endpoint)
                .addHeader("Authorization", "Bearer " + tokenString)
                .post(requestBody).build();
    }
    public RequestApi(String token, int reserva){
        client = new OkHttpClient();
        Gson gson = new Gson();
        String tokenString = JsonParser.parseString(token).getAsJsonObject().get("token").getAsString();

        request = new Request.Builder().url(url+"/reservas"+"/"+reserva)
                .addHeader("Authorization", "Bearer " + tokenString)
                .delete().build();
    }
    public RequestApi(String endpoint, String token){
        client = new OkHttpClient();
        Gson gson = new Gson();
        String tokenString = JsonParser.parseString(token).getAsJsonObject().get("token").getAsString();

        request = new Request.Builder().url(url+endpoint)
                .addHeader("Authorization", "Bearer " + tokenString)
                .get().build();
    }

    public OkHttpClient getClient() {
        return client;
    }




    public Request getRequest() {
        return request;
    }



}
