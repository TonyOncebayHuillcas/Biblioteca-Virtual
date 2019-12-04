package com.example.bibliotecavirtual.Models;

public class UsersClass {
    public String idUser;
    public String userName;

    public UsersClass(){}

    public UsersClass(String idUser, String userName) {
        this.idUser = idUser;
        this.userName = userName;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
