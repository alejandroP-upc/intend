package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ReceiveMessage extends AppCompatActivity {

    Bundle datos;
    TextView mensajes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);

        datos = getIntent().getExtras();
        String mensaje= datos.getString("mensaje");
        mensajes = (TextView)findViewById(R.id.mensaje1);
        mensajes.setText(mensaje);


    }
}