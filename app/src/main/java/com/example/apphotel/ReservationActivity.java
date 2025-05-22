package com.example.apphotel;

import static com.example.apphotel.R.layout.activity_reservation;

import android.app.DatePickerDialog;
import android.content.Intent;
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
        setContentView(activity_reservation);

        RadioGroup radioGroup = findViewById(R.id.radioGroupSuites);
        EditText nome = findViewById(R.id.inputNome);
        EditText sobrenome = findViewById(R.id.inputSobrenome);
        EditText email = findViewById(R.id.inputEmail);
        EditText cpf = findViewById(R.id.inputCpf);
        EditText telefone = findViewById(R.id.inputTelefone);
        txtEntrada = findViewById(R.id.txtEntrada);
        txtSaida = findViewById(R.id.txtSaida);
        Button btnConfirmar = findViewById(R.id.btnConfirmar);

        txtEntrada.setOnClickListener(v -> showDatePicker(dataEntrada, txtEntrada, true));
        txtSaida.setOnClickListener(v -> showDatePicker(dataSaida, txtSaida, false));

        btnConfirmar.setOnClickListener(v -> {
            int idSelecionado = radioGroup.getCheckedRadioButtonId();

            if (idSelecionado == -1) {
                Toast.makeText(this, "Selecione uma suíte!", Toast.LENGTH_SHORT).show();
                return;
            }

            Calendar hoje = Calendar.getInstance();
            hoje.set(Calendar.HOUR_OF_DAY, 0);
            hoje.set(Calendar.MINUTE, 0);
            hoje.set(Calendar.SECOND, 0);
            hoje.set(Calendar.MILLISECOND, 0);

            Calendar dataEntradaSelecionada = (Calendar) dataEntrada.clone();
            dataEntradaSelecionada.set(Calendar.HOUR_OF_DAY, 0);
            dataEntradaSelecionada.set(Calendar.MINUTE, 0);
            dataEntradaSelecionada.set(Calendar.SECOND, 0);
            dataEntradaSelecionada.set(Calendar.MILLISECOND, 0);

            if (dataEntradaSelecionada.before(hoje)) {
                Toast.makeText(this, "Data de entrada não pode ser anterior a hoje!", Toast.LENGTH_LONG).show();
                return;
            }

            long diff = dataSaida.getTimeInMillis() - dataEntrada.getTimeInMillis();
            long dias = diff / (1000 * 60 * 60 * 24);

            if (dias <= 0) {
                Toast.makeText(this, "Data de saída deve ser após a entrada!", Toast.LENGTH_LONG).show();
                return;
            }

            double preco;
            String suiteNome;
            if (idSelecionado == R.id.suite1) {
                preco = 850;
                suiteNome = "Suíte Imperial Atlântica (Cobertura - Vista Mar)";
            } else if (idSelecionado == R.id.suite2) {
                preco = 650;
                suiteNome = "Suíte Horizonte Azul (Luxo - Vista Mar)";
            } else if (idSelecionado == R.id.suite3) {
                preco = 700;
                suiteNome = "Suíte Cidade Maravilhosa (Cobertura - Vista Cidade)";
            } else { // Assume-se R.id.suite4
                preco = 500;
                suiteNome = "Suíte Jardim Carioca (Conforto - Vista Cidade)";
            }

            double total = preco * dias;

            Intent intent = new Intent(ReservationActivity.this, ResponseActivity.class);
            intent.putExtra("nome", nome.getText().toString() + " " + sobrenome.getText().toString());
            intent.putExtra("cpf", cpf.getText().toString());
            intent.putExtra("email", email.getText().toString());
            intent.putExtra("telefone", telefone.getText().toString());
            intent.putExtra("dataEntrada", txtEntrada.getText().toString());
            intent.putExtra("dataSaida", txtSaida.getText().toString());
            intent.putExtra("suite", suiteNome);
            intent.putExtra("dias", dias);
            intent.putExtra("total", total);

            startActivity(intent);
        });
    }

    private void showDatePicker(Calendar calendar, TextView textView, boolean isEntrada) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            textView.setText(sdf.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        if (isEntrada) {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        } else {
            if (dataEntrada.getTimeInMillis() > System.currentTimeMillis() - 1000) {
                datePickerDialog.getDatePicker().setMinDate(dataEntrada.getTimeInMillis() + (24 * 60 * 60 * 1000));
            } else {
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + (24 * 60 * 60 * 1000) - 1000);
            }
        }
        datePickerDialog.show();
    }
}