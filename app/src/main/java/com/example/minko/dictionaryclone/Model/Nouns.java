package com.example.minko.dictionaryclone.Model;

import java.io.Serializable;

public class Nouns implements Serializable {
    private String name;

    public Nouns(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Nouns{" +
                "name='" + name + '\'' +
                '}';
    }
}
