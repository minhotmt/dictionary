package com.example.minko.dictionaryclone.Service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.minko.dictionaryclone.Model.Favorite;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;


public class MyDatabase extends SQLiteAssetHelper {


    private static final String DATABASE_NAME = "dictionary.sqlite";
    private static final int DATABASE_VERSION = 1;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ArrayList<Favorite> getAllWord() {

        ArrayList<Favorite> lst = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"0 _id", "word", "definition", "status", "user_created"};
        String sqlTables = "words";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);
        if (c.moveToFirst()) {
            do {
                Favorite favorite = new Favorite();
//                favorite.setId(c.getInt(c.getColumnIndex("_id")));
                favorite.setName(c.getString(c.getColumnIndex("word")));
                favorite.setDifinition(c.getString(c.getColumnIndex("definition")));
                favorite.setStatus(c.getInt(c.getColumnIndex("status")));
                favorite.setCreated(c.getInt(c.getColumnIndex("user_created")));
                lst.add(favorite);
            } while (c.moveToNext());
        }
        return lst;

    }

}