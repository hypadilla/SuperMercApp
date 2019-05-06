package com.softwo.supermercapp.Entidades;

import android.media.Image;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Productos {
    public long Id;
    public String Referencia;
    public String Codigo;
    public String Titulo;
    public String Descripcion;
    public String Categoria;
    public String Linea;
    public String Ubicacion;
    public String Puesto;
    public double Costo;
    public boolean ManejaIVA;
    public double Venta;
    public double Stock;
    public double CantidadMininaCompra;
    public double StockMinimo;
    public String Imagen;
    public double Descuento;
    public String UnidadMedida;
    public String Presentacion;
    public boolean Estado;

    public Productos() {
    }

    public Productos(long Id, String referencia, String codigo, String titulo, String descripcion, String categoria, String linea, String ubicacion, String puesto, double costo, boolean manejaIVA, double venta, double stock, double cantidadMininaCompra, double stockMinimo, String imagen, double descuento, String unidadMedida, String presentacion, boolean estado) {
        this.Id = Id;
        Referencia = referencia;
        Codigo = codigo;
        Titulo = titulo;
        Descripcion = descripcion;
        Categoria = categoria;
        Linea = linea;
        Ubicacion = ubicacion;
        Puesto = puesto;
        Costo = costo;
        ManejaIVA = manejaIVA;
        Venta = venta;
        Stock = stock;
        CantidadMininaCompra = cantidadMininaCompra;
        StockMinimo = stockMinimo;
        Imagen = imagen;
        Descuento = descuento;
        UnidadMedida = unidadMedida;
        Presentacion = presentacion;
        Estado = estado;
    }

    @Exclude
    public long getId() {
        return Id;
    }

    @Exclude
    public void setId(long Id) {
        this.Id = Id;
    }

    @Exclude
    public String getReferencia() {
        return Referencia;
    }

    @Exclude
    public void setReferencia(String referencia) {
        Referencia = referencia;
    }

    @Exclude
    public String getCodigo() {
        return Codigo;
    }

    @Exclude
    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    @Exclude
    public String getTitulo() {
        return Titulo;
    }

    @Exclude
    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    @Exclude
    public String getDescripcion() {
        return Descripcion;
    }

    @Exclude
    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    @Exclude
    public String getCategoria() {
        return Categoria;
    }

    @Exclude
    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    @Exclude
    public String getLinea() {
        return Linea;
    }

    @Exclude
    public void setLinea(String linea) {
        Linea = linea;
    }

    @Exclude
    public String getUbicacion() {
        return Ubicacion;
    }

    @Exclude
    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    @Exclude
    public String getPuesto() {
        return Puesto;
    }

    @Exclude
    public void setPuesto(String puesto) {
        Puesto = puesto;
    }

    @Exclude
    public double getCosto() {
        return Costo;
    }

    @Exclude
    public void setCosto(double costo) {
        Costo = costo;
    }

    @Exclude
    public boolean isManejaIVA() {
        return ManejaIVA;
    }

    @Exclude
    public void setManejaIVA(boolean manejaIVA) {
        ManejaIVA = manejaIVA;
    }

    @Exclude
    public double getVenta() {
        return Venta;
    }

    @Exclude
    public void setVenta(double venta) {
        Venta = venta;
    }

    @Exclude
    public double getStock() {
        return Stock;
    }

    @Exclude
    public void setStock(double stock) {
        Stock = stock;
    }

    @Exclude
    public double getCantidadMininaCompra() {
        return CantidadMininaCompra;
    }

    @Exclude
    public void setCantidadMininaCompra(double cantidadMininaCompra) {
        CantidadMininaCompra = cantidadMininaCompra;
    }

    @Exclude
    public double getStockMinimo() {
        return StockMinimo;
    }

    @Exclude
    public void setStockMinimo(double stockMinimo) {
        StockMinimo = stockMinimo;
    }

    @Exclude
    public String getImagen() {
        return Imagen;
    }

    @Exclude
    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    @Exclude
    public double getDescuento() {
        return Descuento;
    }

    @Exclude
    public void setDescuento(double descuento) {
        Descuento = descuento;
    }

    @Exclude
    public String getUnidadMedida() {
        return UnidadMedida;
    }

    @Exclude
    public void setUnidadMedida(String unidadMedida) {
        UnidadMedida = unidadMedida;
    }

    @Exclude
    public String getPresentacion() {
        return Presentacion;
    }

    @Exclude
    public void setPresentacion(String presentacion) {
        Presentacion = presentacion;
    }

    @Exclude
    public boolean isEstado() {
        return Estado;
    }

    @Exclude
    public void setEstado(boolean estado) {
        Estado = estado;
    }
}
