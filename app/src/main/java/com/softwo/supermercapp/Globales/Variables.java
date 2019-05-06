package com.softwo.supermercapp.Globales;

import com.softwo.supermercapp.Entidades.DetallePedido;
import com.softwo.supermercapp.Entidades.Pedidos;
import com.softwo.supermercapp.Entidades.Persona;
import com.softwo.supermercapp.Sqlite.Helper.DatabaseHelper;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Variables {
    public static Persona persona;
    public static Pedidos pedido;
    public static ArrayList<DetallePedido> detallePedido;
    public static DatabaseHelper databaseHelper;
    public static BigDecimal tope;
}
