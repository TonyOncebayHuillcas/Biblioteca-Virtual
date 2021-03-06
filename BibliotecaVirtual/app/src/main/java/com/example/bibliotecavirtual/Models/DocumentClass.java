package com.example.bibliotecavirtual.Models;

public class DocumentClass {
    public  String idDoc;
    public  String nombre;
    public  int contador;
    public  String fecha;
    public  String codTema;
    public  String codUsuario;
    public  String archivo;

    public DocumentClass(){}

    public DocumentClass( String idDoc, String nombre, int contador, String fecha, String codTema, String codUsuario, String archivo) {

        this.idDoc = idDoc;
        this.nombre = nombre;
        this.contador = contador;
        this.fecha = fecha;
        this.codTema = codTema;
        this.codUsuario = codUsuario;
        this.archivo = archivo;
    }

    public DocumentClass( String idDoc, String nombre, int contador, String fecha, String codTema, String codUsuario) {

        this.idDoc = idDoc;
        this.nombre = nombre;
        this.contador = contador;
        this.fecha = fecha;
        this.codTema = codTema;
        this.codUsuario = codUsuario;
        this.archivo = archivo;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCodTema() {
        return codTema;
    }

    public void setCodTema(String codTema) {
        this.codTema = codTema;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }
}
