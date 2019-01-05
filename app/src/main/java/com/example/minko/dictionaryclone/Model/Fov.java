package com.example.minko.dictionaryclone.Model;


public class Fov {

    public static final String TABLE_NAME = "fovs";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOTE = "fov";

    private int id;
    private String fov;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOTE + " TEXT)";

    public Fov() {
    }

    public Fov(int id, String fov) {
        this.id = id;
        this.fov = fov;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFov() {
        return fov;
    }

    public void setFov(String fov) {
        this.fov = fov;
    }
}