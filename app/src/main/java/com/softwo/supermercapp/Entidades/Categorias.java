package com.softwo.supermercapp.Entidades;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Categorias {
    public long Id;
    public String Titulo;
    public String Imagen;

    public Categorias() {
    }

    public Categorias(long Id, String Titulo, String Imagen) {
        this.Titulo = Titulo;
        this.Imagen = Imagen;
    }

    @Exclude
    public long getId() {
        return Id;
    }
    @Exclude
    public void setId(long Id) {
        this.Id = Id;
    }
    @Exclude
    public String getTitulo() {
        return Titulo;
    }
    @Exclude
    public void setTitulo(String titulo) {
        Titulo = titulo;
    }
    @Exclude
    public String getImagen() {
        return Imagen;
    }
    @Exclude
    public void setImagen(String imagen) {
        Imagen = imagen;
    }
}
