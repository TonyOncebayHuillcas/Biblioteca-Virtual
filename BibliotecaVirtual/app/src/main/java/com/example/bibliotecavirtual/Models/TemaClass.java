package com.example.bibliotecavirtual.Models;

public class TemaClass {
    public String idTema;
    public String nombre;

    public TemaClass(){}
    public TemaClass(String idTema, String nombre) {
        this.idTema = idTema;
        this.nombre = nombre;
    }

    public String getIdTema() {
        return idTema;
    }

    public void setIdTema(String idTema) {
        this.idTema = idTema;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
