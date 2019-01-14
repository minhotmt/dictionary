package com.example.minko.dictionaryclone.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Favorite implements Parcelable {
    private int id;
    private String name;
    private String difinition;
    private int status;
    private int created;

    public Favorite() {
    }

    public Favorite(String name, String difinition) {
        this.name = name;
        this.difinition = difinition;
    }

    public Favorite(int id, String name, String difinition, int status, int created) {
        this.id = id;
        this.name = name;
        this.difinition = difinition;
        this.status = status;
        this.created = created;
    }

    public Favorite(String name, String difinition, int status, int created) {
        this.name = name;
        this.difinition = difinition;
        this.status = status;
        this.created = created;
    }

    public Favorite(String name) {
        this.name = name;
    }

    public Favorite(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Favorite(Parcel in) {
        id = in.readInt();
        name = in.readString();
        difinition = in.readString();
        status = in.readInt();
        created = in.readInt();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public String getDifinition() {
        return difinition;
    }

    public void setDifinition(String difinition) {
        this.difinition = difinition;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", difinition='" + difinition + '\'' +
                ", status=" + status +
                ", created=" + created +
                '}';
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


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(difinition);
        dest.writeInt(status);
        dest.writeInt(created);
    }
}
