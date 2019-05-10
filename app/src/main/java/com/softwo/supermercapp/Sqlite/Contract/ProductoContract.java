package com.softwo.supermercapp.Sqlite.Contract;

import android.provider.BaseColumns;

public class ProductoContract {
    public static abstract class ProductoEntry implements BaseColumns {
        public static final String TABLE_NAME ="producto";

        public static String ID = "Id";
        public static String REFERENCIA = "Referencia";
        public static String CODIGO = "Codigo";
        public static String TITULO = "Titulo";
        public static String DESCRIPCION = "Descripcion";
        public static String CATEGORIA = "Categoria";
        public static String LINEA = "Linea";
        public static String UBICACION = "Ubicacion";
        public static String PUESTO = "Puesto";
        public static String COSTO = "Costo";
        public static String MANEJAIVA = "ManejaIVA";
        public static String VENTA = "Venta";
        public static String STOCK = "Stock";
        public static String CANTIDADMINIMACOMPRA = "CantidadMinimaCompra";
        public static String STOCKMINIMO = "StockMinimo";
        public static String IMAGEN = "Imagen";
        public static String DESCUENTO = "Descuento";
        public static String UNIDADMEDIDA = "UnidadMedida";
        public static String PRESENTACION = "Presentacion";
        public static String ESTADO = "Estado";

    }
}
