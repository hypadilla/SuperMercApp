package com.softwo.supermercapp.Entidades;

import android.content.ContentValues;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.softwo.supermercapp.Sqlite.Contract.PedidosContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@IgnoreExtraProperties
public class Pedidos {
    public long _id;
    public String id;
    public double total;
    public String fecha;
    public Persona persona;
    public String observacion;
    public double recibido;
    public double devuelto;
    public ArrayList<DetallePedido> detallePedido;
    public int estado;

    public Pedidos() {
    }

    public Pedidos(long _id, String id, double total, String fecha, Persona persona, String observacion, double recibido, double devuelto, ArrayList<DetallePedido> detallePedido, int estado) {
        this._id =_id;
        this.id = id;
        this.total = total;
        this.fecha = fecha;
        this.persona = persona;
        this.observacion = observacion;
        this.recibido = recibido;
        this.devuelto = devuelto;
        this.detallePedido = detallePedido;
        this.estado = estado;
    }

    @Exclude
    public long get_Id() {
        return _id;
    }

    @Exclude
    public void set_Id(long _id) {
        this._id = _id;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public double getTotal() {
        return total;
    }

    @Exclude
    public void setTotal(double total) {
        this.total = total;
    }

    @Exclude
    public String getFecha() {
        return fecha;
    }

    @Exclude
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Exclude
    public Persona getPersona() {
        return persona;
    }

    @Exclude
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Exclude
    public String getObservacion() {
        return observacion;
    }

    @Exclude
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Exclude
    public double getRecibido() {
        return recibido;
    }

    @Exclude
    public void setRecibido(double recibido) {
        this.recibido = recibido;
    }

    @Exclude
    public double getDevuelto() {
        return devuelto;
    }

    @Exclude
    public void setDevuelto(double devuelto) {
        this.devuelto = devuelto;
    }

    @Exclude
    public ArrayList<DetallePedido> getDetallePedido() {
        return detallePedido;
    }

    @Exclude
    public void setDetallePedido(ArrayList<DetallePedido> detallePedido) {
        this.detallePedido = detallePedido;
    }

    @Exclude
    public int isEstado() {
        return estado;
    }

    @Exclude
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * Usado en Sqlite
     * @return
     */
    @Exclude
    public ContentValues toContentValues() {

        ContentValues values = new ContentValues();
        values.put( PedidosContract.PedidoEntry.ID, id);
        values.put(PedidosContract.PedidoEntry.FECHA, fecha);
        values.put(PedidosContract.PedidoEntry.NOMBRE, persona.getNombre());
        values.put(PedidosContract.PedidoEntry.DIRECCION, persona.getDireccion());
        values.put(PedidosContract.PedidoEntry.REFERENCIA, persona.getReferencia());
        values.put(PedidosContract.PedidoEntry.CELULAR, persona.getTelefono());
        values.put(PedidosContract.PedidoEntry.TOTAL, total);
        values.put(PedidosContract.PedidoEntry.RECIBIDO, recibido);
        values.put(PedidosContract.PedidoEntry.DEVUELTA, devuelto);
        values.put(PedidosContract.PedidoEntry.ESTADO, estado);
        return values;
    }
}
