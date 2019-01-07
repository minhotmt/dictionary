package com.example.minko.dictionaryclone.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.minko.dictionaryclone.Model.Favorite;
import java.util.ArrayList;
import java.util.List;
public class DBFavoriteManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="Favorite_list";
    private static final String TABLE_NAME ="favorite";
    private static final String ID ="id";
    private static final String NAME ="name";


    private Context context;

    public DBFavoriteManager(Context context) {
        super(context, DATABASE_NAME,null, 1);
        Log.d("DBManager", "DBManager: ");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE "+TABLE_NAME +" (" +
                ID +" integer primary key, "+
                NAME + " TEXT)";
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    //Add new a Favorite
    public void addFavorite(Favorite favorite){
        Boolean check = false;
        List<String> lst = this.getAllFavoriteString();
        for (String item: lst){
            if (item.equals(favorite.getName())){
                check = true;
            }
        }
        if (check){

        } else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NAME, favorite.getName());
            //Neu de null thi khi value bang null thi loi
            db.insert(TABLE_NAME,null,values);
            db.close();
        }

    }

    /*
    Select a Favorite by ID
     */

    public Favorite getFavoriteById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { ID,
                        NAME}, ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Favorite favorite = new Favorite(cursor.getString(1));
        cursor.close();
        db.close();
        return favorite;
    }

    /*
    Update name of Favorite
     */

    public int Update(Favorite favorite){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME,favorite.getName());

        return db.update(TABLE_NAME,values,ID +"=?",new String[] { String.valueOf(favorite.getId())});


    }

    /*
     Getting All Favorite
      */

    public List<Favorite> getAllFavorite() {
        List<Favorite> listFavorite = new ArrayList<Favorite>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Favorite favorite = new Favorite();
                favorite.setId(cursor.getInt(0));
                favorite.setName(cursor.getString(1));
                listFavorite.add(favorite);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listFavorite;
    }


    public List<String> getAllFavoriteString() {
        List<String> listFavoriteString = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Favorite favorite = new Favorite();
                favorite.setId(cursor.getInt(0));
                favorite.setName(cursor.getString(1));
                listFavoriteString.add(favorite.getName());
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listFavoriteString;
    }
    /*
    Delete a Favorite by ID
     */
    public void deleteFavorite(Favorite favorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?",
                new String[] { String.valueOf(favorite.getId()) });
        db.close();
    }

    public void deleteFavoriteByName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, NAME + "=?", new String[]{name});
        db.close();
    }
    /*
    Get Count Favorite in Table Favorite
     */
    public int getFavoritesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}


