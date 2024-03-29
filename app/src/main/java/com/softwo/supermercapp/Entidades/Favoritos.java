package com.softwo.supermercapp.Entidades;

import android.content.ContentValues;

import com.google.firebase.database.Exclude;
import com.softwo.supermercapp.Sqlite.Contract.FavoritoContract;

public class Favoritos {
    public String idProducto;
    public boolean estado;

    public Favoritos() {
    }

    public Favoritos( String idProducto, boolean estado) {
        this.idProducto = idProducto;
        this.estado = estado;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     * Usado en Sqlite
     *
     * @return
     */
    @Exclude
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put( FavoritoContract.FavoritoEntry.IDPRODUCTO, idProducto );
        values.put( FavoritoContract.FavoritoEntry.ESTADO, estado );
        return values;
    }
}
