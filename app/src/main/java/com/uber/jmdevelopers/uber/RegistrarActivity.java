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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarActivity extends AppCompatActivity {
    private EditText nome, email, senha;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    // para salvar dados de usuarios
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        nome = findViewById(R.id.NOME);
        email = findViewById(R.id.EMAIL);
        senha = findViewById(R.id.SENHA);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


    }

    public void registrar_user(View v) {
        progressDialog.setMessage("Registrando, por favor Aguarde.");
        progressDialog.show();
        String nome_user = nome.getText().toString();
        String email_user = email.getText().toString();
        final String senha_user = senha.getText().toString();
        if (nome_user.equals("") || email_user.equals("") || senha_user.equals("")) {
            Toast.makeText(getApplicationContext(), "Insira os campos obrigatorios", Toast.LENGTH_LONG).show();
        } else {
            // criando
            auth.createUserWithEmailAndPassword(email_user,senha_user).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isComplete())
                        {
                            progressDialog.hide();
                            Toast.makeText(getApplicationContext(), "usuário assinado com sucesso", Toast.LENGTH_LONG).show();
                            // pegando a referencia para o no
                            databaseReference=FirebaseDatabase.getInstance().getReference().child("usuarios");
                            // criando o usuario objetvo
                            Usuario usuario_objeto=new Usuario(nome.getText().toString(),email.getText().toString(),senha.getText().toString());
                            // pegando referencia do usuario
                            FirebaseUser firebaseUser=auth.getCurrentUser();
                            // pegando id para cadastrar o usuario objeto com os dados
                            databaseReference.child(firebaseUser.getUid()).setValue(usuario_objeto).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Dados de Usuario salvo", Toast.LENGTH_LONG).show();
                                        Intent intent=new Intent(RegistrarActivity.this,PaginaPrincipal.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Dados de Usuario não foi salvo ", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                        }
                        else{
                        progressDialog.hide();
                            Toast.makeText(getApplicationContext(), "usuário não Cadastrado", Toast.LENGTH_LONG).show();
                        }
                    }


            });


        }


    }


}
