package com.softwo.supermercapp.Entidades;

import android.content.ContentValues;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.softwo.supermercapp.Sqlite.Contract.DetallePedidoContract;

@IgnoreExtraProperties
public class DetallePedido {
    public long idpedido;
    public double cantidad;
    public Productos producto;

    public DetallePedido() {
    }

    public DetallePedido(long idpedido, double cantidad, Productos producto) {
        this.idpedido = idpedido;
        this.cantidad = cantidad;
        this.producto = producto;
    }

    @Exclude
    public long getIdPedido() {
        return idpedido;
    }

    @Exclude
    public void setIdPedido(long idpedido) {
        this.idpedido = idpedido;
    }

    @Exclude
    public double getCantidad() {
        return cantidad;
    }

    @Exclude
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    @Exclude
    public Productos getProducto() {
        return producto;
    }

    @Exclude
    public void setProducto(Productos producto) {
        this.producto = producto;
    }

    @Exclude
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put( DetallePedidoContract.DetallePedidoEntry.IDPEDIDO, idpedido );
        values.put( DetallePedidoContract.DetallePedidoEntry.CANTIDAD, cantidad );
        values.put( DetallePedidoContract.DetallePedidoEntry.IDPRODUCTO, producto.getId() );
        values.put( DetallePedidoContract.DetallePedidoEntry.VENTA, producto.getVenta() );
        return values;
    }
}
