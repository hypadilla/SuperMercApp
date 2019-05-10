package com.softwo.supermercapp.Entidades;

import android.location.Location;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Persona {
    public String Nombre;
    public String Direccion;
    public String Referencia;
    public String Telefono;
    public Location Localizacion;
    public String Token;
    public String Ciudad;

    public Persona() {
    }

    public Persona(String nombre, String direccion, String referencia, String telefono, Location localizacion, String token, String ciudad) {
        Nombre = nombre;
        Direccion = direccion;
        Referencia = referencia;
        Telefono = telefono;
        Localizacion = localizacion;
        Token = token;
        Ciudad = ciudad;
    }

    @Exclude
    public String getNombre() {
        return Nombre;
    }

    @Exclude
    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    @Exclude
    public String getDireccion() {
        return Direccion;
    }

    @Exclude
    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    @Exclude
    public String getReferencia() {
        return Referencia;
    }

    @Exclude
    public void setReferencia(String referencia) {
        Referencia = referencia;
    }

    @Exclude
    public String getTelefono() {
        return Telefono;
    }

    @Exclude
    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    @Exclude
    public Location getLocalizacion() {
        return Localizacion;
    }

    @Exclude
    public void setLocalizacion(Location localizacion) {
        Localizacion = localizacion;
    }

    @Exclude
    public String getToken() {
        return Token;
    }

    @Exclude
    public void setToken(String token) {
        Token = token;
    }
}
