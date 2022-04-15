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
import android.widget.Toast;

import com.example.pdm2_avaliacao_01.R;
import com.example.pdm2_avaliacao_01.Util;
import com.example.pdm2_avaliacao_01.dao.PokemonDao;
import com.example.pdm2_avaliacao_01.db.DbConnect;
import com.example.pdm2_avaliacao_01.pojo.Pokemon;
import com.example.pdm2_avaliacao_01.resources.GsonPokemonResource;
import com.example.pdm2_avaliacao_01.services.PokemonService;

import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private String TAG = HomeFragment.class.getSimpleName();

    private final int TOTAL_POKEMONS = 898;
    private final int MIN_EXPOSICAO = 3;
    private final int MAX_EXPOSICAO = 20;

    View root;

    String url;

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
                url = "https://pokeapi.co/api/v2/pokemon?limit=" + limit + "&offset=" + offset;
                Log.e(TAG, url);
                tvUrlApi.setText("URL: " + url);
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
                PokemonService ps = new PokemonService();
                List<Pokemon> pokemons = ps.getPokemons(url);

                salvarNoBD(pokemons);
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

    private void salvarNoBD(List<Pokemon> pokemons) {

        PokemonDao pokemonDao = new PokemonDao(root.getContext());

        pokemonDao.limparTabela();

        for (int i = 0; i < pokemons.size(); i++) {
            if(pokemonDao.salvarOuAtualizar(pokemons.get(i)))
                Util.mostrarUmaMensagem_Toast(root.getContext(), "Salvo no BD!");
            else
                Util.mostrarUmaMensagem_Toast(root.getContext(), "Erro ao tentar salvar no DB!");
        }
//        Util.mostrarUmaMensagem_Toast(root.getContext(), "Salvo no BD!");
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