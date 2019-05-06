package com.softwo.supermercapp.Sqlite.Contract;

import android.provider.BaseColumns;

public class PedidosContract {
    public static abstract class PedidoEntry implements BaseColumns {
        public static final String TABLE_NAME ="pedido";

        public static final String ID = "id";
        public static final String FECHA = "fecha";
        public static final String NOMBRE = "nombre";
        public static final String DIRECCION = "direccion";
        public static final String REFERENCIA = "referencia";
        public static final String CELULAR = "celular";
        public static final String OBSERVACION = "observacion";
        public static final String TOTAL = "total";
        public static final String RECIBIDO = "recibido";
        public static final String DEVUELTA = "devuelta";
        public static final String ESTADO = "estado";
    }
}

