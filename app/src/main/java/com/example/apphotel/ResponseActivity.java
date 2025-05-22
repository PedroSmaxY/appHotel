package com.example.apphotel;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

    public class ResponseActivity extends AppCompatActivity {

        TextView textViewDetails;
        Button btnOk;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_response);

            textViewDetails = findViewById(R.id.textViewDetails);
            btnOk = findViewById(R.id.btnOk);

            // Receber dados da Intent
            Intent intent = getIntent();
            String nome = intent.getStringExtra("nome");
            String cpf = intent.getStringExtra("cpf");
            String email = intent.getStringExtra("email");
            String dataEntrada = intent.getStringExtra("dataEntrada");
            String dataSaida = intent.getStringExtra("dataSaida");

            // Montar mensagem
            String mensagem = "Reserva feita com sucesso para " + nome + ".\n\n" +
                    "Período: " + dataEntrada + " até " + dataSaida + ".\n" +
                    "CPF: " + cpf + "\n" +
                    "Uma cópia foi enviada para o e-mail: " + email;

            textViewDetails.setText(mensagem);

            // Botão OK volta à tela principal
            btnOk.setOnClickListener(view -> {
                Intent i = new Intent(ResponseActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            });
        }
    }

