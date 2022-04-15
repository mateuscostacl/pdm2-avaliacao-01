package com.example.pdm2_avaliacao_01.services;

public class PokemonUrlHandler {

    public static String pegarIdDoPokemonPelaUrl(String url) {
        String[] id = url.split("(\\d*)/$");
        return id[1];
    }

    public static String gerarUrlDoPokemonPeloId(String id) {
        final String URL = "https://pokeapi.co/api/v2/pokemon/345/";
        return URL + id;
    }
}
