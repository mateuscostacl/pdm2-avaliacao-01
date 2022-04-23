package com.example.pdm2_avaliacao_01.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pdm2_avaliacao_01.R;
import com.example.pdm2_avaliacao_01.dao.PokemonDao;
import com.example.pdm2_avaliacao_01.pojo.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MaiorMenorFragment extends Fragment {

    View root;

    TextView tvNomeMaiorPeso;
    TextView tvMaiorPeso;
    ImageView ivMaiorPeso;

    TextView tvNomeMenorPeso;
    TextView tvMenorPeso;
    ImageView ivMenorPeso;

    TextView tvNomeMaiorAltura;
    TextView tvMaiorAltura;
    ImageView ivMaiorAltura;

    TextView tvNomeMenorAltura;
    TextView tvMenorAltura;
    ImageView ivMenorAltura;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_maior_menor, container, false);

        conectarComViewport();
        pegarMaiorMenor();

        return root;
    }

    private void conectarComViewport() {
        tvNomeMaiorPeso = root.findViewById(R.id.tv_nome_maior_peso);
        tvMaiorPeso = root.findViewById(R.id.tv_maior_peso);
        ivMaiorPeso = root.findViewById(R.id.iv_imagem_maior_peso);

        tvNomeMenorPeso = root.findViewById(R.id.tv_nome_menor_peso);
        tvMenorPeso = root.findViewById(R.id.tv_menor_peso);
        ivMenorPeso = root.findViewById(R.id.iv_imagem_menor_peso);

        tvNomeMaiorAltura = root.findViewById(R.id.tv_nome_maior_altura);
        tvMaiorAltura = root.findViewById(R.id.tv_maior_altura);
        ivMaiorAltura = root.findViewById(R.id.iv_imagem_maior_altura);

        tvNomeMenorAltura = root.findViewById(R.id.tv_nome_menor_altura);
        tvMenorAltura = root.findViewById(R.id.tv_menor_altura);
        ivMenorAltura = root.findViewById(R.id.iv_imagem_menor_altura);
    }

    private void pegarMaiorMenor() {
        PokemonDao pokemonDao = new PokemonDao(root.getContext());
        List<Pokemon> pokemonList = pokemonDao.retornarTodos();

        //inicializa um pokemon para ser usado como base
        Pokemon pMin = new Pokemon();
        Pokemon pMax = new Pokemon();

        pMax.setPeso("0");
        pMax.setAltura("0");
        Pokemon pokemonMaiorPeso = pMax;
        Pokemon pokemonMaiorAltura = pMax;

        pMin.setPeso(String.valueOf(Integer.MAX_VALUE));
        pMin.setAltura(String.valueOf(Integer.MAX_VALUE));
        Pokemon pokemonMenorPeso = pMin;
        Pokemon pokemonMenorAltura = pMin;

        //variáveis auxiliares
        int peso;
        int altura;

        //percorrer a lista de pokemons
        for (int i = 0; i < pokemonList.size(); i++) {

            //quarda o peso e a altura do pokemon atual
            peso = Integer.parseInt(pokemonList.get(i).getPeso());
            altura = Integer.parseInt(pokemonList.get(i).getAltura());

            //verifica o menor peso
            if (peso < Integer.parseInt(pokemonMenorPeso.getPeso()))
                pokemonMenorPeso = pokemonList.get(i);

            //verifica o maior peso
            if (peso > Integer.parseInt(pokemonMaiorPeso.getPeso()))
                pokemonMaiorPeso = pokemonList.get(i);

            //verifica a menor altura
            if (altura < Integer.parseInt(pokemonMenorAltura.getAltura()))
                pokemonMenorAltura = pokemonList.get(i);

            //verifica a maior altura
            if (altura > Integer.parseInt(pokemonMaiorAltura.getAltura()))
                pokemonMaiorAltura = pokemonList.get(i);
        }

        //pokemon de maior peso
        tvNomeMaiorPeso.setText(pokemonMaiorPeso.getNome());
        tvMaiorPeso.setText(pokemonMaiorPeso.getPeso());
        Picasso.get().load(pokemonMaiorPeso.getImagem()).resize(250, 250).into((ImageView) ivMaiorPeso);

        //pokemon de menor peso
        tvNomeMenorPeso.setText(pokemonMenorPeso.getNome());
        tvMenorPeso.setText(pokemonMenorPeso.getPeso());
        Picasso.get().load(pokemonMenorPeso.getImagem()).resize(250, 250).into((ImageView) ivMenorPeso);

        //pokemon de maior altura
        tvNomeMaiorAltura.setText(pokemonMaiorAltura.getNome());
        tvMaiorAltura.setText(pokemonMaiorAltura.getAltura());
        Picasso.get().load(pokemonMaiorAltura.getImagem()).resize(250, 250).into((ImageView) ivMaiorAltura);

        //pokemon de menor altura
        tvNomeMenorAltura.setText(pokemonMenorAltura.getNome());
        tvMenorAltura.setText(pokemonMenorAltura.getAltura());
        Picasso.get().load(pokemonMenorAltura.getImagem()).resize(250, 250).into((ImageView) ivMenorAltura);

    }
}