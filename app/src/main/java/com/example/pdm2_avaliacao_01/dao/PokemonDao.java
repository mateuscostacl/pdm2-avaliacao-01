package com.example.pdm2_avaliacao_01.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.pdm2_avaliacao_01.db.DbConnect;
import com.example.pdm2_avaliacao_01.db.DbHelper;
import com.example.pdm2_avaliacao_01.pojo.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class PokemonDao {

    private final String TABLE_NAME = "pokemon";
    private DbConnect dbConnect;

    public PokemonDao(Context ctx) {
        dbConnect = DbConnect.getInstance(ctx);
    }

    public void limparTabela() {
        dbConnect.getDb().delete(TABLE_NAME, null, new String[]{});
    }

    public boolean salvarOuAtualizar(Pokemon pokemon) {

        ContentValues cv = new ContentValues();
        cv.put("idPoke", pokemon.getIdPoke());
        cv.put("nome", pokemon.getNome());
        cv.put("peso", pokemon.getPeso());
        cv.put("altura", pokemon.getAltura());
        cv.put("exp_basica", pokemon.getExpBasica());
        cv.put("imagem", pokemon.getImagem());
        cv.put("habilidade", pokemon.getHabilidade());
        cv.put("tipo", pokemon.getTipo());

        return dbConnect.getDb().insert(TABLE_NAME, null, cv) > 0;

//        if(pokemon.getId() == 0)
//            return dbConnect.getDb().update(TABLE_NAME, cv, "_id = ?", new String[]{"" + pokemon.getId()}) > 0;
//        else
//            return dbConnect.getDb().insert(TABLE_NAME, null, cv) > 0;
    }

    public boolean remover(int id) {

        return dbConnect.getDb().delete(TABLE_NAME, "id = ?", new String[]{"" + id}) > 0;
    }

    @SuppressLint("Range")
    public List<Pokemon> retornarTodos() {

        List<Pokemon> pokemons = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLE_NAME;

        Cursor c = dbConnect.getDb().rawQuery(sql, null);

//        c.moveToFirst();
//        while (c.moveToNext()){
//            int id = c.getInt(c.getColumnIndex("id"));
//            String nome = c.getString(c.getColumnIndex("nome"));
//            String sexo = c.getString(c.getColumnIndex("sexo"));
//            String telefone = c.getString(c.getColumnIndex("telefone"));
//            clientes.add(new Usuario(id, nome, sexo, telefone));
//        }
        while (c.moveToNext()) {
            Pokemon pokemon = new Pokemon();
            pokemon.setId(c.getInt(c.getColumnIndex("id")));
            pokemon.setIdPoke(c.getString(c.getColumnIndex("idPoke")));
            pokemon.setNome(c.getString(c.getColumnIndex("nome")));
            pokemon.setPeso(c.getString(c.getColumnIndex("peso")));
            pokemon.setAltura(c.getString(c.getColumnIndex("altura")));
            pokemon.setExpBasica(c.getString(c.getColumnIndex("exp_basica")));
            pokemon.setImagem(c.getString(c.getColumnIndex("imagem")));
            pokemon.setHabilidade(c.getString(c.getColumnIndex("habilidade")));
            pokemon.setTipo(c.getString(c.getColumnIndex("tipo")));
            pokemons.add(pokemon);
        }
        c.close();

        return pokemons;
    }
}
