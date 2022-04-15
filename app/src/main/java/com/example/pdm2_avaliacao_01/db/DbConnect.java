package com.example.pdm2_avaliacao_01.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbConnect {

    private SQLiteDatabase db;
    private DbHelper banco;
    private static DbConnect dbConnect;

    public DbConnect(Context context) {
        banco = new DbHelper(context);
        db = banco.getWritableDatabase();
    }

    public static DbConnect getInstance(Context ctx){
        if(dbConnect == null)
            dbConnect = new DbConnect(ctx);
        return dbConnect;
    }

    public SQLiteDatabase getDb(){
        return this.db;
    }

}
