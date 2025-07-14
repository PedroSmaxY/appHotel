package com.example.apphotel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        // Configura a Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar_sobre);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Configura os cliques
        setupClickListeners();
    }

    private void setupClickListeners() {
        // Clique no E-mail
        findViewById(R.id.layout_email).setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:contato@palaciodoatlantico.com"));
            try {
                startActivity(emailIntent);
            } catch (Exception e) {
                Toast.makeText(this, "Nenhum app de e-mail encontrado.", Toast.LENGTH_SHORT).show();
            }
        });

        // Clique no Site
        findViewById(R.id.layout_website).setOnClickListener(v -> {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.palaciodoatlantico.com"));
            try {
                startActivity(webIntent);
            } catch (Exception e) {
                Toast.makeText(this, "Nenhum navegador encontrado.", Toast.LENGTH_SHORT).show();
            }
        });

        // --- CLIQUES NAS REDES SOCIAIS ---
        // (Adicione as URLs corretas do hotel)
        findViewById(R.id.icon_facebook).setOnClickListener(v -> openUrl("https://facebook.com/palacioatlantico"));
        findViewById(R.id.icon_instagram).setOnClickListener(v -> openUrl("https://instagram.com/palacioatlantico"));
        findViewById(R.id.icon_x).setOnClickListener(v -> openUrl("https://x.com/palacioatlantico"));
    }

    // Método auxiliar para abrir URLs
    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Não foi possível abrir o link.", Toast.LENGTH_SHORT).show();
        }
    }
}