package com.example.professionals.models;

@Table(table_name = "")
public abstract class Model {
    // Cada clase que herede de Model tendrá que tener alguna anotación.
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
