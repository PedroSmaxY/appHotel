package com.example.apphotel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.Locale;

public class ResponseActivity extends AppCompatActivity {

    // Views
    private TextView responseDetails, responsePaymentDetails;
    private Button btnOkResponse, btnCancelResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        // Configura a Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar_response);
        toolbar.setNavigationOnClickListener(v -> finish()); // Permite voltar para editar

        // Vincula as views
        responseDetails = findViewById(R.id.response_details);
        responsePaymentDetails = findViewById(R.id.response_payment_details);
        btnOkResponse = findViewById(R.id.btnOkResponse);
        btnCancelResponse = findViewById(R.id.btnCancelResponse);

        // Recebe os dados e preenche os campos
        populateData();

        // Configura os botões
        btnOkResponse.setOnClickListener(view -> {
            Toast.makeText(ResponseActivity.this, "Obrigado! Tenha uma ótima estadia!", Toast.LENGTH_LONG).show();
            // Volta para a tela inicial, limpando o histórico de reserva
            Intent mainIntent = new Intent(ResponseActivity.this, HomeActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainIntent);
            finish();
        });

        // O botão de cancelar/alterar simplesmente fecha esta tela, voltando para a de pagamento
        btnCancelResponse.setOnClickListener(view -> finish());
    }

    private void populateData() {
        Intent intent = getIntent();
        if (intent == null) return;

        // Coleta todos os dados do Intent
        String nome = intent.getStringExtra("nome");
        String cpf = intent.getStringExtra("cpf");
        String dataEntrada = intent.getStringExtra("dataEntrada");
        String suite = intent.getStringExtra("suite");
        long dias = intent.getLongExtra("dias", 0);
        double totalReserva = intent.getDoubleExtra("totalReserva", 0.0);
        String bandeiraCartao = intent.getStringExtra("bandeiraCartao");
        String numeroCartao = intent.getStringExtra("numeroCartao");

        // Monta o texto para os detalhes da estadia
        String detailsText = "Hóspede: " + nome + "\n" +
                "CPF: " + cpf + "\n\n" +
                "Suíte: " + suite + "\n" +
                "Período: " + dataEntrada + "\n" +
                "Duração: " + dias + " noites";
        responseDetails.setText(detailsText);

        // Monta o texto para os detalhes do pagamento
        String finalCartao = "";
        if (numeroCartao != null && numeroCartao.length() > 4) {
            finalCartao = " final " + numeroCartao.substring(numeroCartao.length() - 4);
        }
        String paymentDetailsText = "Valor Total: " + String.format(Locale.getDefault(), "R$ %.2f", totalReserva) + "\n" +
                "Cartão: " + bandeiraCartao + finalCartao;
        responsePaymentDetails.setText(paymentDetailsText);
    }
}