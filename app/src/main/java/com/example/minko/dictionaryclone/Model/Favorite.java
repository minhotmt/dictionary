package com.example.minko.dictionaryclone.Model;

public class Favorite {
    private int id;
    private String name;

    public Favorite() {
    }

    public Favorite(String name) {
        this.name = name;
    }

    public Favorite(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "name='" + name + '\'' +
                '}';
    }
}
