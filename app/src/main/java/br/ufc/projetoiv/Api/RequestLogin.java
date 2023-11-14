package br.ufc.projetoiv.Api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;

import br.ufc.projetoiv.model.Token;
import br.ufc.projetoiv.model.Usuario;
import br.ufc.projetoiv.view.Login;
import br.ufc.projetoiv.view.MainActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestLogin extends RequestApi{
    private String token;
    Context context;
    public RequestLogin(String endpoint, RequestBody requestBody, Context context) {
        super(endpoint, requestBody);
        this.context = context;
    }
    public RequestLogin login(Activity activity){
        getClient().newCall(getRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson = new Gson();
                            try{
                                token = response.peekBody(2048).string();
                                Token token1 = new Token(token);
                                Intent intent = new Intent(context, MainActivity.class);
                                context.startActivity(intent);

                            }catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
            }
        });
        return this;
    }
    public String getToken(){

        return token;
    }
}
