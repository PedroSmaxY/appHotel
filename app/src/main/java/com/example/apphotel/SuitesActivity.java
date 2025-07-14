package com.example.apphotel;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.apphotel.adapters.SuiteAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.ArrayList;
import java.util.List;

public class SuitesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SuiteAdapter adapter;
    private List<Suite> suiteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suites);

        // Configura a Toolbar com o botão de voltar
        MaterialToolbar toolbar = findViewById(R.id.toolbar_suites);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Inicializa a lista e o RecyclerView
        recyclerView = findViewById(R.id.suites_recycler_view);
        suiteList = new ArrayList<>();

        // Prepara os dados das suítes
        prepareSuiteData();

        // Configura o adapter e o layout manager
        adapter = new SuiteAdapter(suiteList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void prepareSuiteData() {
        // Lembre-se de trocar os R.drawable pelos nomes corretos das suas imagens
        suiteList.add(new Suite("Suíte Imperial Atlântica", "Cobertura com vista para o mar. O máximo de luxo e exclusividade.", "Diária: R$ 850,00", R.drawable.suite_imperial));
        suiteList.add(new Suite("Suíte Horizonte Azul", "Quarto de luxo com uma deslumbrante vista para o mar.", "Diária: R$ 650,00", R.drawable.suite_jardim));
        suiteList.add(new Suite("Suíte Cidade Maravilhosa", "Localizada na cobertura, com uma vista panorâmica da cidade.", "Diária: R$ 700,00", R.drawable.suite_horizonte));
        suiteList.add(new Suite("Suíte Jardim Carioca", "Acomodação confortável com vista para a cidade.", "Diária: R$ 500,00", R.drawable.suite_cidade));

    }
}