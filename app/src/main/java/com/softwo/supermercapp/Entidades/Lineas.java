package com.softwo.supermercapp.Entidades;

public class Lineas {
    public String id;
    public String Titulo;
    public String Categorias;
    public String Descripcion;

    public Lineas() {
    }

    public Lineas(String id, String titulo, String categorias, String descripcion) {
        this.id = id;
        Titulo = titulo;
        Categorias = categorias;
        Descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getCategorias() {
        return Categorias;
    }

    public void setCategorias(String categorias) {
        Categorias = categorias;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
