package com.example.bibliotecavirtual.Models;

public class TemaClass {
    public String idTema;
    public String userName;

    public TemaClass(String idTema, String userName) {
        this.idTema = idTema;
        this.userName = userName;
    }

    public String getIdTema() {
        return idTema;
    }

    public void setIdTema(String idTema) {
        this.idTema = idTema;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
