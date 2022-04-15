package com.example.pdm2_avaliacao_01.services;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pdm2_avaliacao_01.pojo.Pokemon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PokemonService extends AppCompatActivity {

    private String TAG = PokemonService.class.getSimpleName();

    private HttpService httpService = new HttpService();

    public List<Pokemon> getPokemons(String url) {

        String stringPokemons = httpService.getResposta(url);

        if (stringPokemons.equals(null)) {
            return new ArrayList<>();
        }

        return pokemonsHandler(stringPokemons);
    }

    //MANIPULADOR DE POKEMONS
    private List<Pokemon> pokemonsHandler(String pokemons) {

        // declara e inicializa uma instancia de uma lista  de pokemons
        List<Pokemon> pokemonList = new ArrayList<>();

        try {
            //instancia um JSONObject e converte a String objeto ({}) recebida da PokeAPI em um JSONObject
            JSONObject jo = new JSONObject(pokemons);

            //declara um JSONArray e converte a String array ([]) do atributo "results" em um JSONArray
            JSONArray ja = jo.getJSONArray("results");

            //percorre a lista de pokemons para buscar mais detalhes de cada pokemon
            for (int i = 0; i < ja.length(); i++) {

                //declara um JSONObject para receber os atributos de um pokemon (name, url) recebidos na primeira busca no site
                JSONObject joPokemonResumido = ja.getJSONObject(i);

                //realiza uma nova busca, trazendo mais detalhes sobre o pokemon
                String stringPokemon = httpService.getResposta(joPokemonResumido.getString("url"));

                //verifica se a nova busca retornou algo
                if (stringPokemon.equals(null)) {

                    //se a busca não tiver retornado os dados de um pokemon
                    // encerra o método retornando uma instância de lista, vazia
                    return new ArrayList<>();
                }

                //declara e inicializa um JSONObject e converte a String objeto ({}) recebida da PokeAPI em um JSON Object
                JSONObject joPokemonCompleto = new JSONObject(stringPokemon);

                //instancia um pokemon para armazenar todos os seus dados
                Pokemon pokemon = new Pokemon();

                pokemon.setNome(joPokemonResumido.getString("name"));
                pokemon.setUrl(joPokemonResumido.getString("url"));

                pokemon.setIdPoke(joPokemonCompleto.getString("id"));
                pokemon.setPeso(joPokemonCompleto.getString("weight"));
                pokemon.setAltura(joPokemonCompleto.getString("height"));
                pokemon.setExpBasica(joPokemonCompleto.getString("base_experience"));

                //instância necessária para manipular a string do atributo "sprites" como um objeto
                JSONObject joImagens = joPokemonCompleto.getJSONObject("sprites");
                pokemon.setImagem(joImagens.getString("front_default"));

                //declaração necessária para manipular a string do atributo "abilities" como um array
                JSONArray jaHabilidades = joPokemonCompleto.getJSONArray("abilities");
                for (int idxHab = 0; idxHab < jaHabilidades.length(); idxHab++) {

                    JSONObject joHabilidade = jaHabilidades.getJSONObject(idxHab);
                    if (joHabilidade.getString("is_hidden").equals("false")) {

                        JSONObject joHab = joHabilidade.getJSONObject("ability");
                        String hab = joHab.getString("name");

                        if (!Objects.isNull(pokemon.getHabilidade()))
                            hab = pokemon.getHabilidade() + ", " + hab;
                        pokemon.setHabilidade(hab);
                    }
                }

                //instância necessária para manipular a string do atributo "types" como um array
                JSONArray jaTipos = joPokemonCompleto.getJSONArray("types");
                for (int idxTipo = 0; idxTipo < jaTipos.length(); idxTipo++) {
                    JSONObject joTipoObj = jaTipos.getJSONObject(idxTipo);

                    JSONObject joTipo = joTipoObj.getJSONObject("type");
                    String tipo = joTipo.getString("name");

                    if (!Objects.isNull(pokemon.getTipo()))
                        tipo = pokemon.getTipo() + ", " + tipo;

                    pokemon.setTipo(tipo);
                }

                pokemonList.add(pokemon);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pokemonList;
    }
}
