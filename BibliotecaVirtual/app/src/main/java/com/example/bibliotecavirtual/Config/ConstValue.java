package com.example.bibliotecavirtual.Config;

public class ConstValue {
    public static String MAIN_PREF = "EasyNote";
    public static String URL_SEND_USER = "https://easynote1.herokuapp.com/api/usuario";
    public static String URL_LOGIN = "https://easynote1.herokuapp.com/api/loginUser";
    public static String URL_GET_DOCUMENTS = "https://easynote1.herokuapp.com/api/documento";

    public static String response;

    public static String getResponse() { return response; }
    public static void setResponse(String response) { ConstValue.response = response; }
}
