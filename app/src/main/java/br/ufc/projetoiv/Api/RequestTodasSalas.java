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
import br.ufc.projetoiv.model.Sala;
import br.ufc.projetoiv.model.Token;
import br.ufc.projetoiv.service.AddMessageChat;
import br.ufc.projetoiv.view.MainActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestTodasSalas extends RequestApi{

    private Context context;


    public RequestTodasSalas(String endpoint, Context context) {
        super(endpoint, Token.getToken());
        this.context = context;
    }
    public RequestTodasSalas verSalas(Activity activity,
                                      List<Message> messages,
                                      MessageAdapter messageAdapter,
                                      RecyclerView recyclerView){
        getClient().newCall(getRequest()).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        try{
                            String salas = response.peekBody(2048).string();
                            Sala[] sala = gson.fromJson(salas, Sala[].class);

                            AddMessageChat addMessageChat = new AddMessageChat((Activity) context,
                                    messages,
                                    messageAdapter,
                                    recyclerView);
                            if(salas.equals("[]")){
                                addMessageChat.addToChat("você não possui reservas atualmente",
                                        Message.SENT_BY_BOT

                                );
                            }
                            //Toast.makeText(context, salas, Toast.LENGTH_LONG).show();

                            for (int i = 0; i < sala.length;i++){
                                String descricao = "descrição: " + sala[i].getDescricao() + "\n"+
                                "localização: " + sala[i].getLocalizacao() + "\n"
                                        + "codigo: " + sala[i].getIdSala();
                                addMessageChat.addToChat(descricao,
                                        Message.SENT_BY_BOT

                                );
                            }


                            System.out.println(salas);

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
