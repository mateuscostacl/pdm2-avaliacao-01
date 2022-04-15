package com.example.pdm2_avaliacao_01.resources;

import com.example.pdm2_avaliacao_01.pojo.Pokemon;
import com.google.gson.Gson;

public class GsonPokemonResource {

    public Pokemon jsonStringToPojoClass(String jsonString){
        Gson gson = new Gson();
        Pokemon pokemon = gson.fromJson(jsonString, Pokemon.class);

        return pokemon;
    }

    public String pojoClassToJsonString(Pokemon pokemon){
        Gson gson = new Gson();

        String jsonString = gson.toJson(pokemon);

        return jsonString;
    }
}
