package com.example.pdm2_avaliacao_01.ui;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pdm2_avaliacao_01.R;
import com.example.pdm2_avaliacao_01.dao.PokemonDao;
import com.example.pdm2_avaliacao_01.pojo.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class DescricaoFragment extends Fragment {

    private String TAG = DescricaoFragment.class.getSimpleName();

    View root;

    private ConstraintLayout clListaVazia;
    private TextView tvLista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_descricao, container, false);

        conectarComViewport();
        popularLista();

        return root;
    }

    private void conectarComViewport() {
        clListaVazia = root.findViewById(R.id.cl_lista_vazia);
        tvLista = root.findViewById(R.id.tv_lista);
    }

    public void popularLista() {
        PokemonDao pokemonDao = new PokemonDao(root.getContext());
        List<Pokemon> pokemons = (ArrayList<Pokemon>) pokemonDao.retornarTodos();

        if(pokemons.size() == 0) {
            Log.e(TAG, "A LISTA ESTA VAZIA");
            clListaVazia.setVisibility(View.VISIBLE);
            return;
        }

        String pks = "";

        for (int i = 0; i < pokemons.size(); i++) {
            pks = pks + pokemons.get(i).getIdPoke() + " - " + pokemons.get(i).getNome() + "\n";
        }

        tvLista.setText(pks);
    }
}