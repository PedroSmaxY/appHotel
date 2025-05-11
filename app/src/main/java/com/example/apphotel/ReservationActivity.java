package com.example.apphotel;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReservationActivity extends AppCompatActivity {

    TextView txtEntrada, txtSaida;
    Calendar dataEntrada = Calendar.getInstance();
    Calendar dataSaida = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        RadioGroup radioGroup = findViewById(R.id.radioGroupSuites);
        EditText nome = findViewById(R.id.inputNome);
        EditText sobrenome = findViewById(R.id.inputSobrenome);
        EditText email = findViewById(R.id.inputEmail);
        EditText cpf = findViewById(R.id.inputCpf);
        EditText telefone = findViewById(R.id.inputTelefone);
        EditText endereco = findViewById(R.id.inputEndereco);
        txtEntrada = findViewById(R.id.txtEntrada);
        txtSaida = findViewById(R.id.txtSaida);
        Button btnConfirmar = findViewById(R.id.btnConfirmar);

        txtEntrada.setOnClickListener(v -> showDatePicker(dataEntrada, txtEntrada));
        txtSaida.setOnClickListener(v -> showDatePicker(dataSaida, txtSaida));

        btnConfirmar.setOnClickListener(v -> {
            int idSelecionado = radioGroup.getCheckedRadioButtonId();

            if (idSelecionado == -1) {
                Toast.makeText(this, "Selecione uma suíte!", Toast.LENGTH_SHORT).show();
                return;
            }

            long diff = dataSaida.getTimeInMillis() - dataEntrada.getTimeInMillis();
            long dias = diff / (1000 * 60 * 60 * 24);

            if (dias <= 0) {
                Toast.makeText(this, "Data de saída deve ser após a entrada!", Toast.LENGTH_LONG).show();
                return;
            }

            double preco;
            if (idSelecionado == R.id.suite1) preco = 850;
            else if (idSelecionado == R.id.suite2) preco = 650;
            else if (idSelecionado == R.id.suite3) preco = 700;
            else preco = 500;

            double total = preco * dias;

            String msg = "Reserva confirmada!\nCliente: " + nome.getText() + " " + sobrenome.getText()
                    + "\nE-mail: " + email.getText()
                    + "\nEntrada: " + txtEntrada.getText()
                    + "\nSaída: " + txtSaida.getText()
                    + "\nDiárias: " + dias
                    + "\nValor total: R$ " + total;

            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        });
    }

    private void showDatePicker(Calendar calendar, TextView textView) {
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            textView.setText(sdf.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
