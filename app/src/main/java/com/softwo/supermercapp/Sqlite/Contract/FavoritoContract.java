package com.softwo.supermercapp.Sqlite.Contract;

import android.provider.BaseColumns;

public class FavoritoContract {
    public static abstract class FavoritoEntry implements BaseColumns {
        public static final String TABLE_NAME ="favorito";
        public static final String IDPRODUCTO = "idProducto";
        public static final String ESTADO = "estado";
    }
}