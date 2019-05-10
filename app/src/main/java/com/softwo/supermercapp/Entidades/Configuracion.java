package com.softwo.supermercapp.Entidades;

public class Configuracion {
    public String FechaInicio;
    public String FechaFin;
    public double Tope;

    public Configuracion() {
    }

    public Configuracion(String fechaInicio, String fechaFin, double tope) {
        FechaInicio = fechaInicio;
        FechaFin = fechaFin;
        Tope = tope;
    }
}
