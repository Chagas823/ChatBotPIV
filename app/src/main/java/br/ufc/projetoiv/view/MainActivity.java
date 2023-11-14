package br.ufc.projetoiv.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.ufc.projetoiv.Api.RequestCadastroReserva;
import br.ufc.projetoiv.Api.RequestReservas;
import br.ufc.projetoiv.Api.RequestTodasSalas;
import br.ufc.projetoiv.R;
import br.ufc.projetoiv.model.Message;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufc.projetoiv.adapter.MessageAdapter;
import br.ufc.projetoiv.model.Token;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEditText;
    ImageButton sendButton;
    List<Message> messageList;
    MessageAdapter messageAdapter;

    int contextoConversa = 1;
    //1 ver menu
    //2 cadastrar reserva
    //3 excluir reserva

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageList = new ArrayList<>();



        //views
        recyclerView = findViewById(R.id.recycler_view);
        welcomeTextView = findViewById(R.id.welcome_text);
        messageEditText = findViewById(R.id.message_edit_text);
        sendButton = findViewById(R.id.send_btn);

        //setup recycler view
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        addToChat("ola, seja bem vindo 🤖", Message.SENT_BY_BOT);


        if(contextoConversa == 1){
            menu();
        }


        Toast.makeText(this, Token.getToken(), Toast.LENGTH_LONG).show();


        sendButton.setOnClickListener((view) -> {
            String question = messageEditText.getText().toString().trim();
            addToChat(question, Message.SENT_BY_ME);
            messageEditText.setText("");
            welcomeTextView.setVisibility(View.GONE);

            verifyResponse(question.toCharArray()[0]);
        });
    }
    void addToChat(String message, String sentBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message, sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });

    }
    void verifyResponse(char choice){
        long time = 1000;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(contextoConversa == 1 ){
            switch (choice){
                case '1':
                    addToChat("qual sala ?", Message.SENT_BY_BOT );
                    contextoConversa = 2;
                    addToChat("caso deseje sair digite 0", Message.SENT_BY_BOT);
                    cadastroSala();
                    break;
                case '2':
                    verMinhasReservas();

                    menuButton();
                    break;
                case '3':
                    verMinhasReservas();
                    addToChat("digite o numero da reserva que você deseja excluir", Message.SENT_BY_BOT);
                    excluirReserva();
                    break;
                default:
                    addToChat("opção digitada incorretametne", Message.SENT_BY_BOT);
                    contextoConversa = 1;
                    menu();
                    break;
            }
        }else if(contextoConversa == 2){
            switch (choice){
                case '1':

                    contextoConversa = 2;
                    verMinhasReservas();
                    break;
                case '2':

                    break;
            }
        }
    }
    void menu(){
        addToChat("digite o que vc quer \n1 - cadastrar reserva" +
                "\n2 - listar todas as minhas reservas" +
                "\n3 - excluir reserva" +
                "\n4 - atualizar reserva", Message.SENT_BY_BOT);
    }
    void verTodasSalas(){


        RequestTodasSalas requestTodasSalas = new RequestTodasSalas("/salas", this);
        requestTodasSalas.verSalas(this, messageList, messageAdapter, recyclerView);
    }
    void verMinhasReservas(){


        RequestReservas reservas = new RequestReservas("/reservas-usuario", this);
        reservas.verReservas(this, messageList, messageAdapter, recyclerView);
    }
    void cadastroSala(){

        addToChat("digite o dia desejado", Message.SENT_BY_BOT);

        sendButton.setOnClickListener((view)->{
            String dia = messageEditText.getText().toString().trim();

            boolean ehNumero = (dia != null && dia.matches("[0-9]+"));


            if(ehNumero == false){

                addToChat("por favor digite apenas numeros", Message.SENT_BY_BOT);
                cadastroSala();
                return;
            }

            if (usuarioVerificaSaida(dia)) return;
            addToChat(dia, Message.SENT_BY_ME);
            addToChat("digite o mês desejado", Message.SENT_BY_BOT);
            messageEditText.setText("");

            conversaMes(dia);

        });

    }
    void conversaMes(String dia){
        sendButton.setOnClickListener((view1 -> {

            String mes = messageEditText.getText().toString().trim();

            boolean ehNumero = (mes != null && mes.matches("[0-9]+"));


            if(ehNumero == false){

                addToChat("por favor digite apenas numeros", Message.SENT_BY_BOT);
                conversaMes(dia);
                return;
            }


            if (usuarioVerificaSaida(mes)) return;
            addToChat(mes, Message.SENT_BY_ME);
            addToChat("digite o horário de inicio ex: 10:11", Message.SENT_BY_BOT);
            messageEditText.setText("");
            conversaHorarioInicio(mes, dia);

        }));
    }
    private boolean usuarioVerificaSaida(String dia) {
        if(irParaMenu(dia.toCharArray()[0])){
            messageEditText.setText("");
            sendButton.setOnClickListener(null);
            contextoConversa = 1;
            menuButton();


            return true;

        }
        return false;
    }

    void menuButton(){
        menu();

        sendButton.setOnClickListener((view) -> {
            String question = messageEditText.getText().toString().trim();
            addToChat(question, Message.SENT_BY_ME);
            messageEditText.setText("");
            welcomeTextView.setVisibility(View.GONE);

            verifyResponse(question.toCharArray()[0]);

        });
    }

    boolean irParaMenu(char opt){

        return opt == '0';
    }
    void conversaHorarioFim(String mes, String dia, String horarioInicio){
        sendButton.setOnClickListener((view3)->{
            irParaMenu(messageEditText.toString().toCharArray()[0]);

            String horarioFim = messageEditText.getText().toString().trim();

            String regex = "^([01][0-9]|2[0-3]):[0-5][0-9]$";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(horarioFim);
            if (!matcher.matches()){
                addToChat("o horário deve ser do tipo 00:00, por favor digite novamente", Message.SENT_BY_BOT);
                conversaHorarioFim(mes, dia, horarioInicio);
                return;
            }


            if (usuarioVerificaSaida(horarioFim)) return;
            addToChat(horarioFim, Message.SENT_BY_ME);
            addToChat("digite a capacidade ", Message.SENT_BY_BOT);
            messageEditText.setText("");
            conversaRequest(mes, dia, horarioInicio, horarioFim);
        });
    }
    void conversaRequest(String mes, String dia, String horarioInicio, String horarioFim){
        sendButton.setOnClickListener((view4)->{
            irParaMenu(messageEditText.toString().toCharArray()[0]);

            String capacidade = messageEditText.getText().toString().trim();
            if (usuarioVerificaSaida(capacidade)) return;
            addToChat(capacidade, Message.SENT_BY_ME);
            String dataFormatada = "2023-"+mes+"-"+dia;
            RequestTodasSalas requestTodasSalas =
                    new RequestTodasSalas("/salas-disponiveis?dia="+dataFormatada+"&horarioInicio="+horarioInicio+"&horarioFim="+horarioFim+"&capacidade="+capacidade, this);
            requestTodasSalas.verSalas(this, messageList, messageAdapter, recyclerView);


            messageEditText.setText("");
            addToChat("digite o código da sala ", Message.SENT_BY_BOT);
            conversaCodigoSala(mes, dia, dataFormatada, horarioInicio, horarioFim);

        });
    }
    void conversaCodigoSala(String mes, String dia, String dataFormatada, String horarioInicio, String horarioFim){
        sendButton.setOnClickListener((view5)->{
            irParaMenu(messageEditText.toString().toCharArray()[0]);

            String codigoSala = messageEditText.getText().toString().trim();

            if (usuarioVerificaSaida(codigoSala)) return;

            addToChat(codigoSala, Message.SENT_BY_ME);
            messageEditText.setText("");
            RequestBody requestBody = new FormBody.Builder()
                    .add("sala_id", codigoSala)
                    .add("data_reserva", dataFormatada)
                    .add("horario_inicio", horarioInicio+":00")
                    .add("horario_fim", horarioFim+":00").build();
            RequestCadastroReserva requestCadastroReserva =
                    new RequestCadastroReserva("/reservas", requestBody, this);
            requestCadastroReserva.salvar(this, messageList, messageAdapter, recyclerView);
            contextoConversa = 1;
            menuButton();

        });
    }
    void conversaHorarioInicio(String mes, String dia){
        sendButton.setOnClickListener((view2)->{
            irParaMenu(messageEditText.toString().toCharArray()[0]);

            String horarioInicio = messageEditText.getText().toString().trim();

            String regex = "^([01][0-9]|2[0-3]):[0-5][0-9]$";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(horarioInicio);
            if (!matcher.matches()){
                addToChat("o horário deve ser do tipo 00:00, por favor digite novamente", Message.SENT_BY_BOT);
                conversaHorarioInicio(mes, dia);
                return;
            }

                if (usuarioVerificaSaida(horarioInicio)) return;
            addToChat(horarioInicio, Message.SENT_BY_ME);
            addToChat("digite o horário de fim ex: 12:11", Message.SENT_BY_BOT);
            messageEditText.setText("");
            conversaHorarioFim(mes, dia, horarioInicio);
        });
    }
    void excluirReserva(){
        sendButton.setOnClickListener((view)->{
            String codigoReserva = messageEditText.getText().toString().trim();

            if(codigoReserva.equals("-1")){
                menuButton();
            }
            boolean ehNumero = (codigoReserva != null && codigoReserva.matches("[0-9]+"));


            if(ehNumero == false){

                addToChat("por favor digite apenas numeros", Message.SENT_BY_BOT);
                excluirReserva();
                return;
            }
            RequestReservas requestReservas = new RequestReservas(Integer.parseInt(codigoReserva), this);
            requestReservas.excluirReserva(this, messageList, messageAdapter, recyclerView);
            menuButton();
        });
    }

}