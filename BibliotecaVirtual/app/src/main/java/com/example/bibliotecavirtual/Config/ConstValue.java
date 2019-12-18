package com.example.bibliotecavirtual.Config;

import android.net.Uri;

import com.example.bibliotecavirtual.Models.DocumentClass;

public class ConstValue {
    public static String MAIN_PREF = "EasyNote";
    public static String URL_SEND_USER = "https://easynote1.herokuapp.com/api/usuario";
    public static String URL_LOGIN = "https://easynote1.herokuapp.com/api/loginUser";
    public static String URL_GET_DOCUMENTS = "https://easynote1.herokuapp.com/api/documento";
    public static String URL_GET_USER = "https://easynote1.herokuapp.com/api/usuario";
    public static String URL_GET_DOCUMENT_ID= "https://easynote1.herokuapp.com/api/documentoPDF";
    public static String URL_GET_TEMAS= "https://easynote1.herokuapp.com/api/tema";


    public static String response;
    public static String getResponse() { return response; }
    public static void setResponse(String response) { ConstValue.response = response; }

    public static String existUser;
    public static String getExistUser() { return existUser; }
    public static void setExistUser(String existUser) { ConstValue.existUser = existUser; }

    //Document
    public static String idDoc;
    public static String nombre;
    public static int contador;
    public static String fecha;
    public static String codTema;
    public static String codUsuario;
    public static String archivo;

    public static String getIdDoc() {
        return idDoc;
    }

    public static void setIdDoc(String idDoc) {
        ConstValue.idDoc = idDoc;
    }

    public static String getNombre() {
        return nombre;
    }

    public static void setNombre(String nombre) {
        ConstValue.nombre = nombre;
    }

    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        ConstValue.contador = contador;
    }

    public static String getFecha() {
        return fecha;
    }

    public static void setFecha(String fecha) {
        ConstValue.fecha = fecha;
    }

    public static String getCodTema() {
        return codTema;
    }

    public static void setCodTema(String codTema) {
        ConstValue.codTema = codTema;
    }

    public static String getCodUsuario() {
        return codUsuario;
    }

    public static void setCodUsuario(String codUsuario) {
        ConstValue.codUsuario = codUsuario;
    }

    public static String getArchivo() {
        return archivo;
    }

    public static void setArchivo(String archivo) {
        ConstValue.archivo = archivo;
    }
    //End DDocument

    //USER
    public String idUser;
    public String userName;
    public String correo;
    public String contraseña;
    public String codUniversidad;

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
    //END USER

    public static String uri;

    public static String getUri() {
        return uri;
    }

    public static void setUri(String uri) {
        ConstValue.uri = uri;
    }

    public static Uri uri1;

    public static Uri getUri1() {
        return uri1;
    }

    public static void setUri1(Uri uri1) {
        ConstValue.uri1 = uri1;
    }
}
