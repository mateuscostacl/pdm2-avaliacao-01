package com.example.pdm2_avaliacao_01;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.pdm2_avaliacao_01.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mostrar botão voltar
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //ativar botão voltar
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getOnBackPressedDispatcher().addCallback(this, retornarPersonalizado());

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
    }

    private OnBackPressedCallback retornarPersonalizado(){
        return new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
            }
        };
    }

    //este método recebe o contexto da aplication e um imagem (no formato SVG) e retorna um BitmapDescriptor
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorDrawableResourceId) {


        //retorna um Drawable a partir do contexto e a imagem SVG
        Drawable background = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        //configura a posição da imagem: esquerda, em cima, direita e em baixo
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());

        //cria um Bitmap com os valores de comprimento, altura e cada pixels tem 4 bytes.
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(),
                background.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        //cria uma tela
        Canvas canvas = new Canvas(bitmap);

        //desenha na tela criada
        background.draw(canvas);

        //cria um BitmapDescriptor através de um bitmap
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(-34, 151)).title("New Marker");

        //cria um marcador com a posição especificada e o ícone alterado.

        LatLng reitoria = new LatLng(-10.195217, -48.332829);
        MarkerOptions marcadorReitoria = new MarkerOptions().position(reitoria).title("IFTO - Reitoria").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_ifto_logo_24));
        mMap.addMarker(marcadorReitoria).showInfoWindow();
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(reitoria));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(reitoria, 16));
    }
}