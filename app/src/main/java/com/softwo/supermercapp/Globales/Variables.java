package com.softwo.supermercapp.Globales;

import com.softwo.supermercapp.Entidades.Categorias;
import com.softwo.supermercapp.Entidades.Ciudades;
import com.softwo.supermercapp.Entidades.Configuracion;
import com.softwo.supermercapp.Entidades.DetallePedido;
import com.softwo.supermercapp.Entidades.Pedidos;
import com.softwo.supermercapp.Entidades.Persona;
import com.softwo.supermercapp.Entidades.Productos;
import com.softwo.supermercapp.Entidades.UnidadMedida;
import com.softwo.supermercapp.Sqlite.Helper.DatabaseHelper;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Variables {
    public static Persona persona;
    public static Pedidos pedido;
    public static ArrayList<DetallePedido> detallePedido;
    public static DatabaseHelper databaseHelper;

    public static ArrayList<Categorias> LISTACATEGORIAS;

    public static ArrayList<Ciudades> LISTACIUDADES;

    public static ArrayList<UnidadMedida> LISTAUNIDADMEDIDA;

    public static Configuracion CONFIGURACION;

    public Boolean isOnlineNet() {
        try {
            Process p = java.lang.Runtime.getRuntime().exec( "ping -c 1 www.google.es" );
            int val = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
