package com.example.apphotel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ReservationActivity extends AppCompatActivity {

    // Views do layout
    private ImageView selectedSuiteImage;
    private TextView selectedSuiteName;
    private TextInputEditText inputNome, inputEmail, inputCpf, inputTelefone, inputDatas; // CAMPOS ADICIONADOS
    private Button btnConfirmar;

    // Dados da reserva
    private long checkInDate = 0;
    private long checkOutDate = 0;
    private double suitePricePerNight = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        MaterialToolbar toolbar = findViewById(R.id.toolbar_reservation);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Vincula as views
        selectedSuiteImage = findViewById(R.id.selected_suite_image);
        selectedSuiteName = findViewById(R.id.selected_suite_name);
        inputNome = findViewById(R.id.inputNome);
        inputDatas = findViewById(R.id.inputDatas);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        // VINCULANDO NOVOS CAMPOS
        inputEmail = findViewById(R.id.inputEmail);
        inputCpf = findViewById(R.id.inputCpf);
        inputTelefone = findViewById(R.id.inputTelefone);

        getSuiteDataFromIntent();
        setupDatePicker();

        btnConfirmar.setOnClickListener(v -> processReservation());
    }

    private void getSuiteDataFromIntent() {
        Intent intent = getIntent();
        String suiteName = intent.getStringExtra("SUITE_NAME");
        suitePricePerNight = intent.getDoubleExtra("SUITE_PRICE_RAW", 0.0);
        int imageRes = intent.getIntExtra("SUITE_IMAGE", R.drawable.logoapp);

        selectedSuiteName.setText(suiteName);
        selectedSuiteImage.setImageResource(imageRes);
    }

    private void setupDatePicker() {
        MaterialDatePicker<Pair<Long, Long>> datePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Selecione o período")
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            checkInDate = selection.first;
            checkOutDate = selection.second;

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String dateString = sdf.format(new Date(checkInDate)) + " - " + sdf.format(new Date(checkOutDate));
            inputDatas.setText(dateString);
        });

        inputDatas.setOnClickListener(v -> datePicker.show(getSupportFragmentManager(), "DATE_PICKER"));
    }

    private void processReservation() {
        // COLETA DE DADOS DE TODOS OS CAMPOS
        String nome = inputNome.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String cpf = inputCpf.getText().toString().trim();
        String telefone = inputTelefone.getText().toString().trim();

        // Validação completa
        if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || checkInDate == 0) {
            Toast.makeText(this, "Preencha todos os campos e selecione as datas!", Toast.LENGTH_SHORT).show();
            return;
        }

        long diffInMillis = Math.abs(checkOutDate - checkInDate);
        long nights = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

        if (nights <= 0) {
            Toast.makeText(this, "A data de saída deve ser após a de entrada.", Toast.LENGTH_SHORT).show();
            return;
        }

        double totalPrice = nights * suitePricePerNight;

        // ENVIANDO TODOS OS DADOS PARA A PRÓXIMA TELA
        Intent intent = new Intent(ReservationActivity.this, PaymentActivity.class);
        intent.putExtra("nome", nome);
        intent.putExtra("email", email);
        intent.putExtra("cpf", cpf);
        intent.putExtra("telefone", telefone);
        intent.putExtra("dataEntrada", inputDatas.getText().toString());
        intent.putExtra("suite", selectedSuiteName.getText().toString());
        intent.putExtra("dias", nights);
        intent.putExtra("total", totalPrice);

        startActivity(intent);
    }
}