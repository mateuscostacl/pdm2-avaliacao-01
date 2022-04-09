package com.example.pdm2_avaliacao_01;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.airbnb.lottie.LottieAnimationView;
import com.example.pdm2_avaliacao_01.ui.MapsActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pdm2_avaliacao_01.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        binding.appBarMain.fab.setOnClickListener(this);

        drawerConfiguration();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            Intent i = new Intent(getContext(), MapsActivity.class);
            startActivity(i);
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.nav_view, new MapsFragment())
//                        .commit();
        }
    }

    private Context getContext() {
        return this;
    }

    public void drawerConfiguration() {
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_descricao,
                R.id.nav_detalhes,
                R.id.nav_maior_menor,
                R.id.nav_jogar,
                R.id.nav_maps)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    public static int resultado = 0;
    public static LottieAnimationView lottieAnimationView;

    public static void animar(int rawId) {
        MainActivity.lottieAnimationView.setAnimation(rawId);
        MainActivity.lottieAnimationView.setRepeatCount(1);
        MainActivity.lottieAnimationView.setVisibility(View.VISIBLE);
        MainActivity.lottieAnimationView.playAnimation();
        MainActivity.lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                MainActivity.lottieAnimationView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public static String sortear(List<String> lista) {

        System.out.println(lista.size());
        System.out.println(lista.get(Util.random(lista.size())));

        return "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}