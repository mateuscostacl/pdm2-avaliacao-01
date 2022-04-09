package com.example.pdm2_avaliacao_01.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pdm2_avaliacao_01.R;
import com.example.pdm2_avaliacao_01.Util;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private String TAG = HomeFragment.class.getSimpleName();

    private final int TOTAL_POKEMONS = 898;
    private final int MIN_EXPOSICAO = 3;
    private final int MAX_EXPOSICAO = 20;

    View root;

    Button btQtdPokemon;
    Button btIdxPokemon;
    Button btConsumirAPI;
    Button btReiniciar;

    EditText etQtdPokemon;
    EditText etIdxPokemon;

    TextView tvUrlApi;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);

        conectarComViewport();
        escutarCliques();

        return root;
    }

    private void conectarComViewport() {
        btQtdPokemon = root.findViewById(R.id.bt_qtd_pokemon);
        btIdxPokemon = root.findViewById(R.id.bt_idx_pokemon);
        btConsumirAPI = root.findViewById(R.id.bt_consumir_api);
        btReiniciar = root.findViewById(R.id.bt_reiniciar);

        etQtdPokemon = root.findViewById(R.id.et_qtd_pokemon);
        etIdxPokemon = root.findViewById(R.id.et_idx_pokemon);

        tvUrlApi = root.findViewById(R.id.tv_url_api);
    }

    private void escutarCliques() {
        btQtdPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Gerar quantidade de pokemons coletados, aleatoriamente
                etQtdPokemon.setText("" + Util.random(MIN_EXPOSICAO, MAX_EXPOSICAO));
                btQtdPokemon.setEnabled(false);
                btIdxPokemon.setEnabled(true);
            }
        });
        btIdxPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Gerar indice inicial para coleta de pokemons, aleatoriamente
                int max = TOTAL_POKEMONS - Integer.parseInt(etQtdPokemon.getText().toString());
                etIdxPokemon.setText("" + Util.random(max));
                btIdxPokemon.setEnabled(false);


                String limit = etQtdPokemon.getText().toString();
                String offset = etIdxPokemon.getText().toString();
                String url = "https://pokeapi.co/api/v2/pokemon?limit=" + limit + "&offset=" + offset;
                Log.e(TAG, url);
                tvUrlApi.setText("URL: "+ url);
                btConsumirAPI.setEnabled(true);
            }
        });
        btReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etQtdPokemon.setText("");
                etIdxPokemon.setText("");
                btQtdPokemon.setEnabled(true);
                btIdxPokemon.setEnabled(false);
            }
        });
        btConsumirAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        btReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etQtdPokemon.setText("");
                etIdxPokemon.setText("");
                tvUrlApi.setText("URL: ");
                btQtdPokemon.setEnabled(true);
                btIdxPokemon.setEnabled(false);
            }
        });
    }

    @Override
    public void onClick(View view) {
//        if(view.getId() == R.id.bt_qtd_pokemon){
//            //Gerar quantidade de pokemons coletados, aleatoriamente
//            etQtdPokemon.setText(Util.random(3, 20));
//            Log.e(TAG, "FOi" );
//        }
        if (view.getId() == R.id.bt_idx_pokemon) {
            //Gerar indice inicial para coleta de pokemons, aleatoriamente
            etIdxPokemon.setText(Util.random(3, 20));
        }
    }
}