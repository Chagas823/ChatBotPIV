package br.ufc.projetoiv.Api;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import br.ufc.projetoiv.adapter.MessageAdapter;
import br.ufc.projetoiv.model.Message;
import br.ufc.projetoiv.model.Reserva;
import br.ufc.projetoiv.model.ResponseCadastro;
import br.ufc.projetoiv.model.Sala;
import br.ufc.projetoiv.model.Token;
import br.ufc.projetoiv.service.AddMessageChat;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RequestReservas extends  RequestApi{
    private Context context;

    public RequestReservas(String endpoint, Context context) {
        super(endpoint, Token.getToken());
        this.context = context;
    }
    public RequestReservas(int reservas, Context context){
        super(Token.getToken(), reservas);
        this.context = context;

    }
    public RequestReservas verReservas(Activity activity,
                                      List<Message> messages,
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
                            String salas = response.peekBody(2048).string();
                            Reserva[] reservas = gson.fromJson(salas, Reserva[].class);

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

                            for (int i = 0; i < reservas.length;i++){
                                String descricao = "data: " + reservas[i].getData_reserva() + "\n"+
                                        "localização: " + reservas[i].getLocalizacao() + "\n"
                                        + "horario de inicio: " + reservas[i].getHorario_inicio()+ "\n"
                                + "horario de fim: " + reservas[i].getHorario_fim() + "\n"
                                        + "descrição: " + reservas[i].getDescricao() + "\n"
                                        + "codigo da reserva: " + reservas[i].getId_reserva() + "\n"
                                        ;
                                addMessageChat.addToChat(descricao,
                                        Message.SENT_BY_BOT

                                );
                            }

                        }catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        });
        return this;
    }

    public RequestReservas excluirReserva(Activity activity,
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
                            String mensagem = response.peekBody(2048).string();
                            ResponseCadastro reservas = gson.fromJson(mensagem, ResponseCadastro.class);

                            AddMessageChat addMessageChat = new AddMessageChat((Activity) context,
                                    messages,
                                    messageAdapter,
                                    recyclerView);
                            System.out.println(reservas);
                            addMessageChat.addToChat(reservas.getMessage(), Message.SENT_BY_BOT);
                            System.out.println(messageAdapter);
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
