package com.example.pdm2_avaliacao_01.services;

import android.os.AsyncTask;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class PokemonService extends AppCompatActivity {

    private String TAG = com.example.pdm2_avaliacao_01.services.PokemonService.class.getSimpleName();

    public String getPokemons(String url) {
        String pokemons = null;

        try {
            pokemons = new PokemonServiceAsyncTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pokemons;
    }

    private class PokemonServiceAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.usarServico("https://pokeapi.co/api/v2/pokemon?limit=16&offset=775");

            Log.e(TAG, "Endereço da URL: " + params[0]);
            Log.e(TAG, "Resposta da URL: " + jsonStr);

            return jsonStr;
//            if (jsonStr != null) {
//                try {
//
//                    JSONArray jsonArray = new JSONArray(jsonStr);
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject c = jsonArray.getJSONObject(i);
//
//                        Categoria categoria = new Categoria();
//                        categoria.setNome(c.getString("nome"));
//
//                        JSONArray jsonDicas = c.getJSONArray("dicas");
//                        for (int j = 0; j < jsonDicas.length(); j++) {
//                            String dica = jsonDicas.get(j).toString();
//
//                            categoria.getDicas().add(dica);
//                        }
//
//                        categoriaList.add(categoria);
//                    }
//                } catch (final JSONException e) {
//                    Log.e(TAG, "JSON erro: " + e.getMessage());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(Service.this,
//                                    "JSON erro: " + e.getMessage(),
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//            return categoriaList;
        }
    }
}