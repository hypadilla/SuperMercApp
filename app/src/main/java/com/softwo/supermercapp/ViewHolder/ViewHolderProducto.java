package com.softwo.supermercapp.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.softwo.supermercapp.Entidades.DetallePedido;
import com.softwo.supermercapp.Entidades.Favoritos;
import com.softwo.supermercapp.Entidades.Productos;
import com.softwo.supermercapp.Globales.Variables;
import com.softwo.supermercapp.R;
import com.softwo.supermercapp.Sqlite.Helper.DatabaseHelper;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class ViewHolderProducto extends RecyclerView.ViewHolder {

    public CardView cardView;
    public TextView txtTitulo;
    public TextView txtPresentacion;
    public TextView txtVenta;
    public TextView txtDescuento;
    public TextView txtAntes;
    public TextView txtVentaOff;
    public LinearLayout lnContador;
    public Button btnAgregar;
    public ImageButton imgbtnAdicionar;
    public TextView txtCantidad;
    public ImageButton imgbtnMenos;
    public ImageView imgFavorito;
    public ImageView imgImagen;

    public ViewHolderProducto(@NonNull View itemView) {
        super( itemView );
        findViews( itemView );
    }

    private void findViews(View v) {
        cardView = v.findViewById( R.id.cardView );
        txtTitulo = v.findViewById( R.id.txtTitulo );
        txtPresentacion = v.findViewById( R.id.txtPresentacion );
        txtVenta = v.findViewById( R.id.txtVenta );
        txtDescuento = v.findViewById( R.id.txtDescuento );
        txtVentaOff = v.findViewById( R.id.txtVentaOff );
        lnContador = v.findViewById( R.id.lnContador );
        btnAgregar = v.findViewById( R.id.btnAgregar );
        imgbtnAdicionar = v.findViewById( R.id.imgbtnAdicionar );
        imgbtnMenos = v.findViewById( R.id.imgbtnMenos );
        txtCantidad = v.findViewById( R.id.txtCantidad );
        imgFavorito = v.findViewById( R.id.imgFavorito );
        imgImagen = v.findViewById( R.id.imgImagen );
        txtAntes = v.findViewById( R.id.txtAntes );
    }
}
