package com.example.pdm2_avaliacao_01.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

//    private static DbHelper instance;

    private static final String NOME_BANCO = "db_pokemon.db";
    private static final int VERSAO = 1;

    public DbHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = ""
                + "CREATE TABLE  pokemon ("
                + "id integer PRIMARY KEY AUTOINCREMENT,"
                + "idPoke TEXT,"
                + "nome TEXT,"
                + "imagem TEXT,"
                + "peso TEXT,"
                + "altura TEXT,"
                + "habilidade TEXT,"
                + "tipo TEXT,"
                + "exp_basica TEXT)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS pokemon");
        onCreate(db);
    }
}
