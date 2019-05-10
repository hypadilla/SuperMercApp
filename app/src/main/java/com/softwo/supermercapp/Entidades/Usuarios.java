package com.softwo.supermercapp.Entidades;

public class Usuarios {
    public long Id;
    public String Usuario;
    public String Password;

    public Usuarios() {
    }

    public Usuarios(long id, String usuario, String password) {
        Id = id;
        Usuario = usuario;
        Password = password;
    }
}
