package com.example.bibliotecavirtual.Models;

public class UniversityClass {
    public int id;
    public String nombre;

    public UniversityClass(){}

    public UniversityClass(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
