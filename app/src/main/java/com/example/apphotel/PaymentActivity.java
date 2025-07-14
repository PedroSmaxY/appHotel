package com.example.apphotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View; // <<< ESTA É A LINHA QUE FALTAVA
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {

    // --- Views do Layout ---
    private TextView summarySuiteName, summaryDetails, summaryTotalPrice;
    private TextInputEditText editTextCardNumber, editTextCardHolderName, editTextExpiryDate, editTextCvv;
    private ChipGroup chipGroupCardBrand;
    private MaterialCheckBox checkBoxSaveCard;
    private Button btnProcessPayment;

    // --- Dados recebidos da tela anterior ---
    private String nomeReserva, cpfReserva, emailReserva, telefoneReserva, dataEntradaReserva, suiteNomeReserva;
    private long diasReserva;
    private double totalReservaValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        MaterialToolbar toolbar = findViewById(R.id.toolbar_payment);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        bindViews();
        getIntentDataAndPopulate();

        btnProcessPayment.setOnClickListener(view -> processPayment());
    }

    private void bindViews() {
        summarySuiteName = findViewById(R.id.summary_suite_name);
        summaryDetails = findViewById(R.id.summary_details);
        summaryTotalPrice = findViewById(R.id.summary_total_price);
        editTextCardNumber = findViewById(R.id.editTextCardNumber);
        editTextCardHolderName = findViewById(R.id.editTextCardHolderName);
        editTextExpiryDate = findViewById(R.id.editTextExpiryDate);
        editTextCvv = findViewById(R.id.editTextCvv);
        chipGroupCardBrand = findViewById(R.id.chipGroupCardBrand);
        checkBoxSaveCard = findViewById(R.id.checkBoxSaveCard);
        btnProcessPayment = findViewById(R.id.btnProcessPayment);
    }

    private void getIntentDataAndPopulate() {
        Intent intent = getIntent();
        if (intent == null) return;

        nomeReserva = intent.getStringExtra("nome");
        cpfReserva = intent.getStringExtra("cpf");
        emailReserva = intent.getStringExtra("email");
        telefoneReserva = intent.getStringExtra("telefone");
        dataEntradaReserva = intent.getStringExtra("dataEntrada");
        suiteNomeReserva = intent.getStringExtra("suite");
        diasReserva = intent.getLongExtra("dias", 0);
        totalReservaValor = intent.getDoubleExtra("total", 0.0);

        summarySuiteName.setText(suiteNomeReserva);
        summaryDetails.setText(String.format(Locale.getDefault(), "%d noites", diasReserva));
        summaryTotalPrice.setText(String.format(Locale.getDefault(), "R$ %.2f", totalReservaValor));
    }

    private void processPayment() {
        String numeroCartao = editTextCardNumber.getText().toString().trim();
        String nomeTitular = editTextCardHolderName.getText().toString().trim();
        String dataValidade = editTextExpiryDate.getText().toString().trim();
        String cvv = editTextCvv.getText().toString().trim();
        boolean salvarCartao = checkBoxSaveCard.isChecked();

        int selectedBrandId = chipGroupCardBrand.getCheckedChipId();
        String bandeiraCartao = "";
        if (selectedBrandId != View.NO_ID) {
            Chip selectedChip = findViewById(selectedBrandId);
            bandeiraCartao = selectedChip.getText().toString();
        }

        if (numeroCartao.isEmpty() || nomeTitular.isEmpty() || dataValidade.isEmpty() || cvv.isEmpty() || bandeiraCartao.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos de pagamento!", Toast.LENGTH_LONG).show();
            return;
        }

        Intent responseIntent = new Intent(PaymentActivity.this, ResponseActivity.class);
        responseIntent.putExtra("nome", nomeReserva);
        responseIntent.putExtra("cpf", cpfReserva);
        responseIntent.putExtra("email", emailReserva);
        responseIntent.putExtra("telefone", telefoneReserva);
        responseIntent.putExtra("dataEntrada", dataEntradaReserva);
        responseIntent.putExtra("suite", suiteNomeReserva);
        responseIntent.putExtra("dias", diasReserva);
        responseIntent.putExtra("totalReserva", totalReservaValor);
        responseIntent.putExtra("bandeiraCartao", bandeiraCartao);
        responseIntent.putExtra("numeroCartao", numeroCartao);
        responseIntent.putExtra("nomeTitularCartao", nomeTitular);
        responseIntent.putExtra("salvarCartao", salvarCartao);

        startActivity(responseIntent);
    }
}