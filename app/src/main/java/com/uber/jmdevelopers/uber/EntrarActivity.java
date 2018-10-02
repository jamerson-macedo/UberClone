package com.uber.jmdevelopers.uber;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EntrarActivity extends AppCompatActivity {
    private EditText email, senha;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);
        email = findViewById(R.id.EMAIL);
        senha = findViewById(R.id.SENHA);
        // pegando referencia para autenticação
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

    }

    public void user_entrar(View v) {
        progressDialog.setMessage("Entrando, por favor aguarde.");
        progressDialog.show();
        // VERIFICA SE O USUARIO DIGITOU O WMAIL E SENHA
        if (email.getText().toString().equals("") || senha.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Insira o Email e senha", Toast.LENGTH_LONG).show();

        } else {
            // SE TIVER DIFITADO ELE VAI AUTENTICAR
            auth.signInWithEmailAndPassword(email.getText().toString(), senha.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), "usuário logado com sucesso", Toast.LENGTH_LONG).show();
                        Intent i =new Intent(EntrarActivity.this,PaginaPrincipal.class);
                        startActivity(i);
                        // finish acaba a activity
                        finish();


                    } else {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), "usuário não encontrado", Toast.LENGTH_LONG).show();
                    }

                }
            });


        }
    }
}

