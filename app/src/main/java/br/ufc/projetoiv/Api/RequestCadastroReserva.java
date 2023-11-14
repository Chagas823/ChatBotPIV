package br.ufc.projetoiv.Api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import br.ufc.projetoiv.adapter.MessageAdapter;
import br.ufc.projetoiv.model.Message;
import br.ufc.projetoiv.model.ResponseCadastro;
import br.ufc.projetoiv.model.Token;
import br.ufc.projetoiv.service.AddMessageChat;
import br.ufc.projetoiv.view.MainActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestCadastroReserva extends RequestApi{
    private String token;
    private Context context;
    private String resposta;

    public RequestCadastroReserva(String endpoint, RequestBody requestBody, Context context) {
        super(endpoint, requestBody, Token.getToken());
        this.context = context;
    }

    public RequestCadastroReserva salvar(Activity activity, List<Message> messages,
                                         MessageAdapter messageAdapter,
                                         RecyclerView recyclerView){
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
                            resposta = response.peekBody(2048).string();
                            ResponseCadastro token1 = new ResponseCadastro(token);
                            gson.fromJson(resposta, ResponseCadastro.class);
                            Toast.makeText(context, "Reserva cadastrada", Toast.LENGTH_LONG).show();
                            AddMessageChat addMessageChat = new AddMessageChat((Activity) context,
                                    messages,
                                    messageAdapter,
                                    recyclerView);
                            addMessageChat.addToChat("reserva cadastrada", Message.SENT_BY_BOT);
                        }catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
        return this;
    }
}
