package com.softwo.supermercapp.Entidades;

public class Configuracion {
    public String HoraInicio;
    public String HoraFin;
    public double Tope;

    public Configuracion() {
    }

    public Configuracion(String horaInicio, String horaFin, double tope) {
        HoraInicio = horaInicio;
        HoraFin = horaFin;
        Tope = tope;
    }
}
