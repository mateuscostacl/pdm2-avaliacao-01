package com.example.pdm2_avaliacao_01.ui;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.pdm2_avaliacao_01.R;
import com.example.pdm2_avaliacao_01.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //declara um objeto com latitude e longitude definidos
        LatLng reitoria = new LatLng(-10.195217, -48.332829);

        //cria um novo marcador
        MarkerOptions marcadorReitoria = new MarkerOptions().position(reitoria).title("IFTO - Reitoria").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_ifto_logo_24));

        //insere o marcado no mapa e expoe sua informação
        mMap.addMarker(marcadorReitoria).showInfoWindow();

        //move a camera para o local especificado rapidamente
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(reitoria));

        //move a camera para o local especificado rapidamente usando animação e definindo zoom
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(reitoria, 16));
    }
}