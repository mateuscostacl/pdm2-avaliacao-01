package com.example.pdm2_avaliacao_01.services;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class HttpService extends AppCompatActivity {

    private String TAG = HttpService.class.getSimpleName();

    public String getResposta(String url) {
        String resposta = null;

        try {
            resposta = new ServiceAsyncTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resposta;
    }

    private class ServiceAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpHandler sh = new HttpHandler();

            return sh.usarServico(params[0]);
        }
    }
}
