package com.example.bibliotecavirtual.Models;

public class UserClass {
    public int id;
    public String userName;
    public String correo;
    public String contraseña;
    public String codUniversidad;
    // tambien habra un atributo numero de
    // cuenta en un futuro para el prototipo
    // no se considera

    public UserClass(){}

    public UserClass(int id, String userName, String correo, String contraseña, String codUniversidad) {
        this.id = id;
        this.userName = userName;
        this.correo = correo;
        this.contraseña = contraseña;
        this.codUniversidad = codUniversidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCodUniversidad() {
        return codUniversidad;
    }

    public void setCodUniversidad(String codUniversidad) {
        this.codUniversidad = codUniversidad;
    }
}
