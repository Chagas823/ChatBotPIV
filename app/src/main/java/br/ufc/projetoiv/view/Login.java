package br.ufc.projetoiv.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import br.ufc.projetoiv.Api.RequestLogin;
import br.ufc.projetoiv.R;
import br.ufc.projetoiv.model.Token;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class Login extends AppCompatActivity {
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);



        loginBtn.setOnClickListener((view) ->{
            login();
        });
    }
    public void login()
    { RequestBody requestBody = new FormBody.Builder()
            .add("email", "chagas@gmail.com" )
            .add("senha","123456").build();

        RequestLogin requestLogin = new RequestLogin("/verificar-senha", requestBody, this);
        requestLogin.login(this);

    }



}