package com.softwo.supermercapp.Constantes;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBase {
    public static String BASEDATOS = "server/softwo/super mercapp";
    public static String TABLACLIENTE = "clientes";
    public static String TABLACATEGORIA = "categoria";
    public static String TABLAPRODUCTO = "producto";
    public static String TABLAPEDIDO = "pedido";
    public static String TABLACONFIGURACION = "configuracion";
    public static String TABLACIUDAD = "ciudad";
    public static String TABLAUNIDADMEDIDA = "unidadmedida";
    public static String TABLAUSUARIO = "usuario";

    public static FirebaseDatabase DATABASE = FirebaseDatabase.getInstance();
    public static DatabaseReference REFERENCIA = DATABASE.getReference( BASEDATOS );

}
