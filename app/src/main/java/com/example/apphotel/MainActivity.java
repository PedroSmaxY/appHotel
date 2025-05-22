package com.example.apphotel;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Handler;
import android.widget.Button;


import com.example.apphotel.adapters.ImagePagerAdapter;

public class MainActivity extends AppCompatActivity {
    Button btnReserve;
    ViewPager2 viewPager;
    ImagePagerAdapter adapter;
    int[] images = {
            R.drawable.copa_palace_noite,
            R.drawable.copa_palace_dia,
            R.drawable.main_pool_hotel,
            R.drawable.quadra_basket,
            R.drawable.quadra_tenis_praia
    };
    private final Handler sliderHandler = new Handler();
    private Runnable sliderRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        viewPager = findViewById(R.id.viewPager);
        adapter = new ImagePagerAdapter(images);
        viewPager.setAdapter(adapter);

        btnReserve = findViewById(R.id.btnReserve);

        btnReserve.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReservationActivity.class);
            startActivity(intent);
        });

        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int totalItems = adapter.getItemCount();
                if (totalItems > 0) {
                    int nextItem = (currentItem + 1) % totalItems;
                    viewPager.setCurrentItem(nextItem, true);
                }
                sliderHandler.postDelayed(this, 3000);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }
}