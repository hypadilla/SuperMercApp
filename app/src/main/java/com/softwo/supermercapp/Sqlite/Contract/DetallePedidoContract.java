package com.softwo.supermercapp.Sqlite.Contract;

import android.provider.BaseColumns;

public class DetallePedidoContract {
    public static abstract class DetallePedidoEntry implements BaseColumns {
        public static final String TABLE_NAME ="detallepedido";

        public static final String IDPEDIDO = "idpedido";
        public static final String IDPRODUCTO = "idproducto";
        public static final String CANTIDAD = "cantidad";
        public static final String VENTA = "venta";

    }
}
