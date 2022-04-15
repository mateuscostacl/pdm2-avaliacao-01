package com.example.pdm2_avaliacao_01.ui;

import android.app.Activity;
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
import com.example.pdm2_avaliacao_01.adapters.PokemonAdapter;
import com.example.pdm2_avaliacao_01.dao.PokemonDao;
import com.example.pdm2_avaliacao_01.pojo.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class DetalhesFragment extends Fragment {

    private String TAG = DetalhesFragment.class.getSimpleName();

    private PokemonAdapter pokemonAdapter;
    private ListView lvLista;

    View root;

    private ConstraintLayout clListaVazia;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_detalhes, container, false);

        conectarComViewport();
        popularLista();

        return root;
    }

    private void conectarComViewport() {
        lvLista = root.findViewById(R.id.lv_lista);
        clListaVazia = root.findViewById(R.id.cl_lista_vazia);
    }

    public void popularLista() {
        PokemonDao pokemonDao = new PokemonDao(root.getContext());
        List<Pokemon> pokemons = (ArrayList<Pokemon>) pokemonDao.retornarTodos();

        if (pokemons.size() == 0) {
            Log.e(TAG, "A LISTA ESTA VAZIA");
//            clListaVazia.setVisibility(View.VISIBLE);
            return;
        }
//        clListaVazia.setVisibility(View.INVISIBLE);

        pokemonAdapter = new PokemonAdapter(pokemons, getActivity());
        lvLista.setAdapter(pokemonAdapter);
    }
}