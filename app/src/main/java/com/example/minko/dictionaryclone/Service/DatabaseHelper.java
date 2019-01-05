package com.example.minko.dictionaryclone.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.minko.dictionaryclone.Model.Fov;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "fov_db";
    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Fov.CREATE_TABLE);
        Toast.makeText(context, "create successed", Toast.LENGTH_SHORT).show();
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Fov.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public long insertFov(String fov) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Fov.COLUMN_NOTE, fov);
        long id = db.insert(Fov.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public Fov getFov(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Fov.TABLE_NAME,
                new String[]{Fov.COLUMN_ID, Fov.COLUMN_NOTE},
                Fov.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Fov fov = new Fov(
                cursor.getInt(cursor.getColumnIndex(Fov.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Fov.COLUMN_NOTE)));

        // close the db connection
        cursor.close();

        return fov;
    }

    public List<Fov> getAllFovs() {
        List<Fov> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Fov.TABLE_NAME ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Fov note = new Fov();
                note.setId(cursor.getInt(cursor.getColumnIndex(Fov.COLUMN_ID)));
                note.setFov(cursor.getString(cursor.getColumnIndex(Fov.COLUMN_NOTE)));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int getFovsCount() {
        String countQuery = "SELECT  * FROM " + Fov.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }


    public void deleteFov(Fov note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Fov.TABLE_NAME, Fov.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }
}