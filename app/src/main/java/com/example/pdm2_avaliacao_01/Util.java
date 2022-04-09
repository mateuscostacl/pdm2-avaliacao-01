package com.example.pdm2_avaliacao_01;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class Util {

    public static int random(int max) {
        Random gerador = new Random();
        return gerador.nextInt(max);
    }

    public static int random(int min, int max) {
        Random gerador = new Random();
        return gerador.nextInt(max + 1 - min) + min;
    }

    public static void mostrarUmaMensagem_Snackbar(View view, String mensagem) {
        Snackbar.make(view, mensagem, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public static void mostrarUmaMensagem_Toast(Context context, String mensagem) {
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
    }

    public static void mostrarUmaMensagem_AlertDialog(Context context,String title, String mensagem) {
        AlertDialog alert = new AlertDialog.Builder(context).create();

        alert.setTitle(title);
        alert.setMessage(mensagem);
        alert.show();
    }
}
