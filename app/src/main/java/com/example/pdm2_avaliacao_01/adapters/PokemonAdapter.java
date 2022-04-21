package com.example.pdm2_avaliacao_01.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pdm2_avaliacao_01.R;
import com.example.pdm2_avaliacao_01.pojo.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PokemonAdapter extends BaseAdapter {
    private List<Pokemon> items;
    private final Context context;

    public PokemonAdapter(List<Pokemon> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        v = LayoutInflater.from(context).inflate(R.layout.item_lista, null);

        Pokemon p = items.get(position);

        Pokemon pokemon = items.get(position);
        if (pokemon != null) {
            ((TextView) v.findViewById(R.id.tv_nome)).setText(pokemon.getIdPoke()+ " " + pokemon.getNome());
            ((TextView) v.findViewById(R.id.tv_tipo)).setText("(" + pokemon.getTipo() + ")");
            ((TextView) v.findViewById(R.id.tv_exp_basica)).setText("Exp: "+pokemon.getExpBasica());
            ((TextView) v.findViewById(R.id.tv_altura)).setText("Altura: "+pokemon.getAltura());
            ((TextView) v.findViewById(R.id.tv_peso)).setText("Peso: "+pokemon.getPeso());
            ((TextView) v.findViewById(R.id.tv_habilidade)).setText("Habilidades: "+pokemon.getHabilidade());
            Picasso.get().load(pokemon.getImagem()).resize(250, 250).into((ImageView) v.findViewById(R.id.iv_imagem));
        }
        return v;
    }
}