package com.softwo.supermercapp.ViewHolder;

import android.support.annotation.NonNull;
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

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class ViewHolderProducto extends RecyclerView.ViewHolder {
    private DatabaseHelper databaseHelper;
    private NumberFormat numberFormatPercent = NumberFormat.getPercentInstance( Locale.getDefault() );
    private NumberFormat numberFormat = NumberFormat.getInstance( Locale.getDefault() );

    private TextView txtTitulo;
    private TextView txtPresentacion;
    private TextView txtVenta;
    private TextView txtDescuento;
    private ImageView imgVentaOff;
    private TextView txtVentaOff;
    private LinearLayout lnContador;
    private Button btnAgregar;
    private ImageButton imgbtnAdicionar;
    private TextView txtCantidad;
    private ImageButton imgbtnMenos;
    private ImageView imgFavorito;
    private ImageView imgImagen;

    public ViewHolderProducto(@NonNull View itemView) {
        super( itemView );
        findViews( itemView );
    }

    private void findViews(View v) {
        txtTitulo = v.findViewById( R.id.txtTitulo );
        txtPresentacion = v.findViewById( R.id.txtPresentacion );
        txtVenta = v.findViewById( R.id.txtVenta );
        txtDescuento = v.findViewById( R.id.txtDescuento );
        imgVentaOff = v.findViewById( R.id.imgVentaOff );
        txtVentaOff = v.findViewById( R.id.txtVentaOff );
        lnContador = v.findViewById( R.id.lnContador );
        btnAgregar = v.findViewById( R.id.btnAgregar );
        imgbtnAdicionar = v.findViewById( R.id.imgbtnAdicionar );
        imgbtnMenos = v.findViewById( R.id.imgbtnMenos );
        txtCantidad = v.findViewById( R.id.txtCantidad );
        imgFavorito = v.findViewById( R.id.imgFavorito );
        imgImagen = v.findViewById( R.id.imgImagen );

        databaseHelper = new DatabaseHelper( txtTitulo.getContext() );
        numberFormatPercent.setMinimumFractionDigits( 0 );
    }

    public void setData(final Productos producto) {
        Glide.with( imgImagen.getContext() )
                .load( producto.getImagen() )
                .thumbnail( 0.1f )
                .centerCrop()
                .diskCacheStrategy( DiskCacheStrategy.ALL )
                .into( imgImagen );

        txtTitulo.setText( producto.getTitulo() );
        txtPresentacion.setText( "Presentación (" + producto.getPresentacion() + ")" );

        lnContador.setVisibility( View.GONE );
        btnAgregar.setVisibility( View.VISIBLE );
        txtCantidad.setText( "0" );

        if (databaseHelper.ExisteFavoritos( databaseHelper.getWritableDatabase(), producto.getId() )) {
            imgFavorito.setTag( R.drawable.ic_favorite_black_24dp );
            imgFavorito.setImageResource( R.drawable.ic_favorite_black_24dp );
        } else {
            imgFavorito.setTag( R.drawable.ic_favorite_border_black_24dp );
            imgFavorito.setImageResource( R.drawable.ic_favorite_border_black_24dp );
        }

        if (producto.getDescuento() == 0) {
            txtVenta.setText( numberFormat.format( producto.getVenta() ) );

            txtVentaOff.setVisibility( View.GONE );
            imgVentaOff.setVisibility( View.GONE );
            txtDescuento.setVisibility( View.GONE );
        } else {
            txtVentaOff.setText( numberFormat.format( producto.getVenta() ) );
            txtDescuento.setText( numberFormatPercent.format( producto.getDescuento() / 100 ) );
            double ValorVenta = producto.getVenta() - (producto.getVenta() * (producto.getDescuento() / 100));
            txtVenta.setText( numberFormat.format( ValorVenta ) );
        }

        for (DetallePedido detallePedido : Variables.detallePedido) {
            if (detallePedido.getProducto().getId() == producto.getId()){
                lnContador.setVisibility( View.VISIBLE );
                btnAgregar.setVisibility( View.GONE );
                txtCantidad.setText( String.valueOf(detallePedido.getCantidad()) );
            }
        }

        btnAgregar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCantidad.setText( BigDecimal.ONE.toPlainString() );
                lnContador.setVisibility( View.VISIBLE );
                btnAgregar.setVisibility( View.GONE );

                DetallePedido detallePedido = new DetallePedido();
                detallePedido.setIdPedido( Variables.pedido.get_Id() );
                detallePedido.setCantidad( new BigDecimal( txtCantidad.getText().toString() ).doubleValue() );
                detallePedido.setProducto( producto );
                Variables.detallePedido.add( detallePedido );

                BigDecimal Total = new BigDecimal( Variables.pedido.getTotal() );
                try {
                    Total = Total.add( new BigDecimal( numberFormat.parse( txtVenta.getText().toString() ).toString() ) );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Variables.pedido.setTotal( Total.doubleValue() );

            }
        } );

        imgbtnAdicionar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigDecimal Cantidad = new BigDecimal( txtCantidad.getText().toString() );
                Cantidad = Cantidad.add( BigDecimal.ONE );
                txtCantidad.setText( Cantidad.toPlainString() );

                for (int i = 0; i < Variables.detallePedido.size(); i++) {
                    if (Variables.detallePedido.get( i ).getProducto().getId() == producto.getId()) {
                        Variables.detallePedido.get( i ).setCantidad( new BigDecimal( txtCantidad.getText().toString() ).doubleValue() );
                    }
                }

                BigDecimal Total = new BigDecimal( Variables.pedido.getTotal() );
                try {
                    Total = Total.add( new BigDecimal( numberFormat.parse( txtVenta.getText().toString() ).toString() ) );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Variables.pedido.setTotal( Total.doubleValue() );
            }
        } );

        imgbtnMenos.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigDecimal Cantidad = new BigDecimal( txtCantidad.getText().toString() );
                if (Cantidad.compareTo( BigDecimal.ONE ) == 0) {
                    lnContador.setVisibility( View.GONE );
                    btnAgregar.setVisibility( View.VISIBLE );
                    txtCantidad.setText( "0" );
                    for (int i = 0; i < Variables.detallePedido.size(); i++) {
                        if (Variables.detallePedido.get( i ).getProducto().getId() == producto.getId()) {
                            Variables.detallePedido.remove( i );
                        }
                    }
                } else {
                    Cantidad = Cantidad.subtract( BigDecimal.ONE );
                    txtCantidad.setText( Cantidad.toPlainString() );
                    for (int i = 0; i < Variables.detallePedido.size(); i++) {
                        if (Variables.detallePedido.get( i ).getProducto().getId() == producto.getId()) {
                            Variables.detallePedido.get( i ).setCantidad( new BigDecimal( txtCantidad.getText().toString() ).doubleValue() );
                        }
                    }
                }

                BigDecimal Total = new BigDecimal( Variables.pedido.getTotal() );
                try {
                    Total = Total.subtract( new BigDecimal( numberFormat.parse( txtVenta.getText().toString() ).toString() ) );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Variables.pedido.setTotal( Total.doubleValue() );
            }
        } );

        imgFavorito.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgFavorito.getTag().equals( R.drawable.ic_favorite_border_black_24dp )) {
                    imgFavorito.setImageResource( R.drawable.ic_favorite_black_24dp );
                    imgFavorito.setTag( R.drawable.ic_favorite_black_24dp );
                    databaseHelper.InsertarFavoritos( databaseHelper.getWritableDatabase(), new Favoritos( producto.getId(), true ) );
                    Toasty.success( imgFavorito.getContext(), "Añadido a mis favoritos!", Toast.LENGTH_SHORT, true ).show();
                } else {
                    databaseHelper.EliminarFavoritos( databaseHelper.getWritableDatabase(), producto.getId() );
                    imgFavorito.setImageResource( R.drawable.ic_favorite_border_black_24dp );
                    imgFavorito.setTag( R.drawable.ic_favorite_border_black_24dp );
                    Toasty.error( imgFavorito.getContext(), "Eliminado de mis favoritos.", Toast.LENGTH_SHORT, true ).show();
                }
            }
        } );
    }
}
