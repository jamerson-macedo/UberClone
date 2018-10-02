package com.uber.jmdevelopers.uber;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    // pegando a referencia
    FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // se tiver alguem ja cadastrado ele entra
        if(firebaseUser!=null){
            Intent intent=new Intent(MainActivity.this,PaginaPrincipal.class);
            startActivity(intent);
            finish();
            // se nao ele pede para cadastrar
        }else {
            setContentView(R.layout.activity_main);
        }


    }
    // chamando paginas
    public void abrir_entrar(View v){
        Intent intent=new Intent(MainActivity.this,EntrarActivity.class);
        startActivity(intent);

    }
    public void abrir_registrar(View v ){
        Intent intent=new Intent(MainActivity.this,RegistrarActivity.class);
        startActivity(intent);



    }



}
