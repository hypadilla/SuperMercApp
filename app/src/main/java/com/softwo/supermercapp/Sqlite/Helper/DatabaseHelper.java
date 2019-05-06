package com.softwo.supermercapp.Sqlite.Helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.softwo.supermercapp.Entidades.DetallePedido;
import com.softwo.supermercapp.Entidades.Favoritos;
import com.softwo.supermercapp.Entidades.Pedidos;
import com.softwo.supermercapp.Globales.Variables;
import com.softwo.supermercapp.Sqlite.Contract.DetallePedidoContract;
import com.softwo.supermercapp.Sqlite.Contract.FavoritoContract;
import com.softwo.supermercapp.Sqlite.Contract.PedidosContract;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 12;
    public static final String DATABASE_NAME = "supermercapp.db";
    Context context;

    public DatabaseHelper(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( "CREATE TABLE " + FavoritoContract.FavoritoEntry.TABLE_NAME + " ("
                + FavoritoContract.FavoritoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FavoritoContract.FavoritoEntry.IDPRODUCTO + " INTEGER,"
                + FavoritoContract.FavoritoEntry.ESTADO + " INTEGER,"
                + "UNIQUE (" + FavoritoContract.FavoritoEntry.IDPRODUCTO + "))" );
        sqLiteDatabase.execSQL( "CREATE TABLE " + PedidosContract.PedidoEntry.TABLE_NAME + " ("
                + PedidosContract.PedidoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PedidosContract.PedidoEntry.ID + " TEXT,"
                + PedidosContract.PedidoEntry.FECHA + " TEXT,"
                + PedidosContract.PedidoEntry.NOMBRE + " TEXT,"
                + PedidosContract.PedidoEntry.DIRECCION + " TEXT,"
                + PedidosContract.PedidoEntry.REFERENCIA + " TEXT,"
                + PedidosContract.PedidoEntry.CELULAR + " TEXT,"
                + PedidosContract.PedidoEntry.OBSERVACION + " TEXT,"
                + PedidosContract.PedidoEntry.TOTAL + " REAL,"
                + PedidosContract.PedidoEntry.RECIBIDO + " REAL,"
                + PedidosContract.PedidoEntry.DEVUELTA + " REAL,"
                + PedidosContract.PedidoEntry.ESTADO + " INTEGER)" );
        sqLiteDatabase.execSQL( "CREATE TABLE " + DetallePedidoContract.DetallePedidoEntry.TABLE_NAME + " ("
                + DetallePedidoContract.DetallePedidoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DetallePedidoContract.DetallePedidoEntry.IDPEDIDO + " TEXT,"
                + DetallePedidoContract.DetallePedidoEntry.IDPRODUCTO + " INTEGER,"
                + DetallePedidoContract.DetallePedidoEntry.CANTIDAD + " REAL,"
                + DetallePedidoContract.DetallePedidoEntry.VENTA + " REAL)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL( "drop table if exists " + FavoritoContract.FavoritoEntry.TABLE_NAME );
        sqLiteDatabase.execSQL( "drop table if exists " + PedidosContract.PedidoEntry.TABLE_NAME );
        sqLiteDatabase.execSQL( "drop table if exists " + DetallePedidoContract.DetallePedidoEntry.TABLE_NAME );

        sqLiteDatabase.execSQL( "CREATE TABLE " + FavoritoContract.FavoritoEntry.TABLE_NAME + " ("
                + FavoritoContract.FavoritoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FavoritoContract.FavoritoEntry.IDPRODUCTO + " INTEGER,"
                + FavoritoContract.FavoritoEntry.ESTADO + " INTEGER,"
                + "UNIQUE (" + FavoritoContract.FavoritoEntry.IDPRODUCTO + "))" );
        sqLiteDatabase.execSQL( "CREATE TABLE " + PedidosContract.PedidoEntry.TABLE_NAME + " ("
                + PedidosContract.PedidoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PedidosContract.PedidoEntry.ID + " TEXT,"
                + PedidosContract.PedidoEntry.FECHA + " TEXT,"
                + PedidosContract.PedidoEntry.NOMBRE + " TEXT,"
                + PedidosContract.PedidoEntry.DIRECCION + " TEXT,"
                + PedidosContract.PedidoEntry.REFERENCIA + " TEXT,"
                + PedidosContract.PedidoEntry.CELULAR + " TEXT,"
                + PedidosContract.PedidoEntry.OBSERVACION + " TEXT,"
                + PedidosContract.PedidoEntry.TOTAL + " REAL,"
                + PedidosContract.PedidoEntry.RECIBIDO + " REAL,"
                + PedidosContract.PedidoEntry.DEVUELTA + " REAL,"
                + PedidosContract.PedidoEntry.ESTADO + " INTEGER)" );
        sqLiteDatabase.execSQL( "CREATE TABLE " + DetallePedidoContract.DetallePedidoEntry.TABLE_NAME + " ("
                + DetallePedidoContract.DetallePedidoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DetallePedidoContract.DetallePedidoEntry.IDPEDIDO + " TEXT,"
                + DetallePedidoContract.DetallePedidoEntry.IDPRODUCTO + " INTEGER,"
                + DetallePedidoContract.DetallePedidoEntry.CANTIDAD + " REAL,"
                + DetallePedidoContract.DetallePedidoEntry.VENTA + " REAL)" );
    }

    public void InsertarFavoritos(SQLiteDatabase sqLiteDatabase, Favoritos favorito) {
        if (ExisteFavoritos( sqLiteDatabase, favorito.getIdProducto() )) {
            sqLiteDatabase.update( FavoritoContract.FavoritoEntry.TABLE_NAME, favorito.toContentValues(),
                    FavoritoContract.FavoritoEntry.IDPRODUCTO + "=" + favorito.getIdProducto(), null );
        } else {
            sqLiteDatabase.insert(
                    FavoritoContract.FavoritoEntry.TABLE_NAME,
                    null,
                    favorito.toContentValues() );
        }
        //sqLiteDatabase.close();
    }

    public void EliminarFavoritos(SQLiteDatabase sqLiteDatabase, long idProducto) {
        sqLiteDatabase.delete( FavoritoContract.FavoritoEntry.TABLE_NAME, FavoritoContract.FavoritoEntry.IDPRODUCTO + "=" + idProducto, null );
        //sqLiteDatabase.close();
    }

    public Cursor ConsultarFavoritos(SQLiteDatabase sqLiteDatabase, String idProducto) {
        Cursor fila = sqLiteDatabase.rawQuery(
                "SELECT * FROM " + FavoritoContract.FavoritoEntry.TABLE_NAME + " WHERE " + FavoritoContract.FavoritoEntry.IDPRODUCTO + " = " + idProducto, null );
        //sqLiteDatabase.close();
        return fila;
    }


    public boolean ExisteFavoritos(SQLiteDatabase sqLiteDatabase, long idProducto) {
        Cursor fila = sqLiteDatabase.rawQuery(
                "SELECT * FROM " + FavoritoContract.FavoritoEntry.TABLE_NAME + " WHERE " + FavoritoContract.FavoritoEntry.IDPRODUCTO + " = " + idProducto, null );

        //sqLiteDatabase.close();
        if (fila.moveToFirst())
            return true;
        else
            return false;
    }

    public long InsertarPedido(Pedidos pedido) {
        return Variables.databaseHelper.getWritableDatabase().insert(
                PedidosContract.PedidoEntry.TABLE_NAME,
                null,
                pedido.toContentValues() );

    }

    public boolean ActualizarPedido(Pedidos pedido) {
        int i = Variables.databaseHelper.getWritableDatabase().update( PedidosContract.PedidoEntry.TABLE_NAME, pedido.toContentValues(),
                PedidosContract.PedidoEntry._ID + " = ?", new String[]{String.valueOf( pedido.get_Id() )} );
        if (i <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public void EliminarPedido(SQLiteDatabase sqLiteDatabase, Object object) {

    }

    public Cursor ConsultarPedidoPendiente() {
        Cursor fila = Variables.databaseHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM " + PedidosContract.PedidoEntry.TABLE_NAME + " WHERE " + PedidosContract.PedidoEntry.ESTADO + " = 0", null );
        return fila;
    }

    public Cursor ConsultarPedido(SQLiteDatabase sqLiteDatabase, Object object) {
        String Resultado = "";
        Cursor fila = sqLiteDatabase.rawQuery(
                "SELECT * FROM " + PedidosContract.PedidoEntry.TABLE_NAME, null );
        //sqLiteDatabase.close();
        return fila;
    }

    public Cursor ConsultarPedidos() {
        Cursor fila = Variables.databaseHelper.getWritableDatabase().rawQuery(
                "SELECT * FROM " + PedidosContract.PedidoEntry.TABLE_NAME, null );
        return fila;
    }

    public boolean ExistePedido(long idpedido) {
        Cursor fila = Variables.databaseHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM " + PedidosContract.PedidoEntry.TABLE_NAME
                        + " WHERE " + PedidosContract.PedidoEntry._ID + " = " + idpedido
                , null );
        if (fila.moveToFirst())
            return true;
        else
            return false;
    }

    public long InsertarDetallePedido(ArrayList<DetallePedido> detallePedidos) {

        for (DetallePedido detallePedido :
                detallePedidos) {
            Variables.databaseHelper.getWritableDatabase().insert(
                    DetallePedidoContract.DetallePedidoEntry.TABLE_NAME,
                    null,
                    detallePedido.toContentValues() );
        }

        return 1;
    }

    public void EliminarDetallePedido(SQLiteDatabase sqLiteDatabase, Object object) {

    }

    public Cursor ConsultarDetallePedidoPendiente(SQLiteDatabase sqLiteDatabase) {
        Cursor fila = sqLiteDatabase.rawQuery(
                "SELECT * FROM " + PedidosContract.PedidoEntry.TABLE_NAME, null );
        //sqLiteDatabase.close();
        return fila;
    }

    public Cursor ConsultarDetallePedido(SQLiteDatabase sqLiteDatabase, Object object) {
        String Resultado = "";
        Cursor fila = sqLiteDatabase.rawQuery(
                "SELECT * FROM " + PedidosContract.PedidoEntry.TABLE_NAME, null );
        //sqLiteDatabase.close();
        return fila;
    }

    public Cursor ConsultarDetallePedidos(long idPedido) {
        Cursor fila = Variables.databaseHelper.getWritableDatabase().rawQuery(
                "SELECT * FROM " + DetallePedidoContract.DetallePedidoEntry.TABLE_NAME
                        + " WHERE " + DetallePedidoContract.DetallePedidoEntry.IDPEDIDO + " = ?", new String[]{String.valueOf( idPedido )} );
        return fila;
    }

    public boolean ExisteDetallePedido(SQLiteDatabase sqLiteDatabase, long idProducto, long idPedido) {
        Cursor fila = sqLiteDatabase.rawQuery(
                "SELECT * FROM " + DetallePedidoContract.DetallePedidoEntry.TABLE_NAME
                        + " WHERE " + DetallePedidoContract.DetallePedidoEntry.IDPRODUCTO + " = " + idProducto
                        + " AND " + DetallePedidoContract.DetallePedidoEntry.IDPEDIDO + " = " + idPedido, null );
        if (fila.moveToFirst())
            return true;
        else
            return false;
    }
}
