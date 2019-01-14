package com.example.minko.dictionaryclone.Model;

public class History {
    int id;
    String name;

    public History() {
    }

    public History(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public History(String name) {
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
        return "History{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
