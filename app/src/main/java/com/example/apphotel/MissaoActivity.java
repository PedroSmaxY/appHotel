package com.example.apphotel;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class MissaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missao);

        // Configura a Toolbar com o botão de voltar
        MaterialToolbar toolbar = findViewById(R.id.toolbar_missao);
        toolbar.setNavigationOnClickListener(v -> {
            // Esta ação simula o clique no botão "voltar" do sistema
            onBackPressed();
        });
    }
}