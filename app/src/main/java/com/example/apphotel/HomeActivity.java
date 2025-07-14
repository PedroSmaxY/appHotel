package com.example.apphotel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.apphotel.adapters.ImagePagerAdapter;
import com.example.apphotel.adapters.ServiceAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    // --- Componentes da tela ---
    private ViewPager2 viewPager;
    private ImagePagerAdapter adapter;
    private final int[] images = {
            R.drawable.copa_palace_noite,
            R.drawable.copa_palace_dia,
            R.drawable.main_pool_hotel,
            R.drawable.quadra_basket,
            R.drawable.quadra_tenis_praia
    };
    private final Handler sliderHandler = new Handler();
    private Runnable sliderRunnable;

    private ExtendedFloatingActionButton btnExploreHotel;
    private RecyclerView servicesRecyclerView;
    private Button btnVerOferta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        setupToolbarAndNavigation();


        setupImageSlider();
        setupServicesRecyclerView();

        btnExploreHotel = findViewById(R.id.btnExploreHotel);
        btnExploreHotel.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SuitesActivity.class);
            startActivity(intent);
        });


        btnVerOferta = findViewById(R.id.btnVerOferta);
        btnVerOferta.setOnClickListener(v -> {
            // Ação alterada para levar à tela de Suítes
            Intent intent = new Intent(HomeActivity.this, SuitesActivity.class);
            startActivity(intent);
        });
        //
    }

    private void setupToolbarAndNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupImageSlider() {
        viewPager = findViewById(R.id.viewPager);
        adapter = new ImagePagerAdapter(images);
        viewPager.setAdapter(adapter);

        sliderRunnable = () -> {
            if (adapter.getItemCount() > 0) {
                int currentItem = viewPager.getCurrentItem();
                int nextItem = (currentItem + 1) % adapter.getItemCount();
                viewPager.setCurrentItem(nextItem, true);
            }
            sliderHandler.postDelayed(sliderRunnable, 3000);
        };
    }

    private void setupServicesRecyclerView() {
        servicesRecyclerView = findViewById(R.id.services_recycler_view);

        List<Service> serviceList = new ArrayList<>();
        serviceList.add(new Service(R.drawable.ic_hotel, "Piscina"));
        serviceList.add(new Service(R.drawable.ic_mission, "Spa & Bem-estar"));
        serviceList.add(new Service(R.drawable.ic_about, "Alta Gastronomia"));

        ServiceAdapter serviceAdapter = new ServiceAdapter(serviceList);
        servicesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        servicesRecyclerView.setAdapter(serviceAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_suites) {
            startActivity(new Intent(this, SuitesActivity.class));
        } else if (itemId == R.id.nav_missao) {
            startActivity(new Intent(this, MissaoActivity.class));
        } else if (itemId == R.id.nav_sobre) {
            startActivity(new Intent(this, SobreActivity.class));
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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