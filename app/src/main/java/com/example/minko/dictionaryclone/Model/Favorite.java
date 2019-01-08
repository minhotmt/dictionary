package com.example.minko.dictionaryclone.Model;

public class Favorite {
    private int id;
    private String name;
    private String difinition;

    public Favorite() {
    }

    public Favorite(String name, String difinition) {
        this.name = name;
        this.difinition = difinition;
    }

    public Favorite(String name) {
        this.name = name;
    }

    public Favorite(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getDifinition() {
        return difinition;
    }

    public void setDifinition(String difinition) {
        this.difinition = difinition;
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
                ", difinition='" + difinition + '\'' +
                '}';
    }
}
