package com.softwo.supermercapp.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.softwo.supermercapp.Entidades.DetallePedido;
import com.softwo.supermercapp.Entidades.Favoritos;
import com.softwo.supermercapp.Entidades.Productos;
import com.softwo.supermercapp.Globales.Variables;
import com.softwo.supermercapp.MainActivity;
import com.softwo.supermercapp.R;
import com.softwo.supermercapp.Sqlite.Helper.DatabaseHelper;
import com.softwo.supermercapp.ViewHolder.ViewHolderProducto;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class AdaptadorProductos extends RecyclerView.Adapter<ViewHolderProducto> {

    private ArrayList<Productos> productos;
    private DatabaseHelper databaseHelper;
    private NumberFormat numberFormatPercent = NumberFormat.getPercentInstance( Locale.getDefault() );
    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance( Locale.getDefault() );

    boolean isFavorite;
    Context contexto;

    public AdaptadorProductos(Context contexto, ArrayList<Productos> productos, boolean isFavorite) {
        this.contexto = contexto;
        this.productos = productos;
        this.isFavorite = isFavorite;
        numberFormatPercent.setMinimumFractionDigits( 0 );

        databaseHelper = new DatabaseHelper( contexto );
    }

    @Override
    public ViewHolderProducto onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {
        return new ViewHolderProducto( LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.item_productos, parent, false ) );
    }

    @Override
    public void onBindViewHolder(final ViewHolderProducto holder, final int position) {

        if (productos.get( position ).getImagen().equals( "" )) {
            holder.imgImagen.setImageDrawable( null );
        } else {
            Glide.with( holder.imgImagen.getContext() )
                    .load( productos.get( position ).getImagen() )
                    .thumbnail( 0.1f )
                    .centerCrop()
                    .diskCacheStrategy( DiskCacheStrategy.ALL )
                    .into( holder.imgImagen );
        }

        holder.txtTitulo.setText( productos.get( position ).getTitulo() );
        holder.txtPresentacion.setText( "Presentación (" + productos.get( position ).getPresentacion() + ")" );

        holder.lnContador.setVisibility( View.GONE );
        holder.btnAgregar.setVisibility( View.VISIBLE );
        holder.txtCantidad.setText( "0" );

        if (databaseHelper.ExisteFavoritos( databaseHelper.getWritableDatabase(), productos.get( position ).getId() ) ) {
            holder.imgFavorito.setTag( R.drawable.ic_favorite_black_24dp );
            holder.imgFavorito.setImageResource( R.drawable.ic_favorite_black_24dp );
        } else {
            holder.imgFavorito.setTag( R.drawable.ic_favorite_border_black_24dp );
            holder.imgFavorito.setImageResource( R.drawable.ic_favorite_border_black_24dp );
        }

        if (productos.get( position ).getDescuento() == 0) {
            holder.txtVenta.setText( numberFormat.format( productos.get( position ).getVenta() ) );

            holder.txtVentaOff.setVisibility( View.INVISIBLE );
            holder.txtAntes.setVisibility( View.INVISIBLE );
            holder.txtDescuento.setVisibility( View.INVISIBLE );
        } else {
            holder.txtVentaOff.setText( numberFormat.format( productos.get( position ).getVenta() ) );
            holder.txtDescuento.setText( numberFormatPercent.format( productos.get( position ).getDescuento() / 100 ) );
            double ValorVenta = productos.get( position ).getVenta() - (productos.get( position ).getVenta() * (productos.get( position ).getDescuento() / 100));
            holder.txtVenta.setText( numberFormat.format( ValorVenta ) );
        }

        for (DetallePedido detallePedido : Variables.detallePedido) {
            if (detallePedido.getProducto().getId() == productos.get( position ).getId()) {
                holder.lnContador.setVisibility( View.VISIBLE );
                holder.btnAgregar.setVisibility( View.GONE );
                holder.txtCantidad.setText( String.valueOf( detallePedido.getCantidad() ) );
            }
        }

        holder.btnAgregar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.txtCantidad.setText( BigDecimal.ONE.toPlainString() );
                holder.lnContador.setVisibility( View.VISIBLE );
                holder.btnAgregar.setVisibility( View.GONE );

                DetallePedido detallePedido = new DetallePedido();
                detallePedido.setIdPedido( Variables.pedido.get_Id() );
                detallePedido.setCantidad( new BigDecimal( holder.txtCantidad.getText().toString() ).doubleValue() );
                detallePedido.setProducto( productos.get( position ) );
                Variables.detallePedido.add( detallePedido );

                BigDecimal Total = new BigDecimal( Variables.pedido.getTotal() );
                try {
                    Total = Total.add( new BigDecimal( numberFormat.parse( holder.txtVenta.getText().toString() ).toString() ) );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Variables.pedido.setTotal( Total.doubleValue() );
                ((MainActivity) contexto).IncrementarMenu();
            }
        } );

        holder.imgbtnAdicionar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigDecimal Cantidad = new BigDecimal( holder.txtCantidad.getText().toString() );
                Cantidad = Cantidad.add( BigDecimal.ONE );
                holder.txtCantidad.setText( Cantidad.toPlainString() );

                for (int i = 0; i < Variables.detallePedido.size(); i++) {
                    if (Variables.detallePedido.get( i ).getProducto().getId() == productos.get( position ).getId()) {
                        Variables.detallePedido.get( i ).setCantidad( new BigDecimal( holder.txtCantidad.getText().toString() ).doubleValue() );
                    }
                }

                BigDecimal Total = new BigDecimal( Variables.pedido.getTotal() );
                try {
                    Total = Total.add( new BigDecimal( numberFormat.parse( holder.txtVenta.getText().toString() ).toString() ) );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Variables.pedido.setTotal( Total.doubleValue() );

                ((MainActivity) contexto).IncrementarMenu();
            }
        } );

        holder.imgbtnMenos.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigDecimal Cantidad = new BigDecimal( holder.txtCantidad.getText().toString() );
                if (Cantidad.compareTo( BigDecimal.ONE ) == 0) {
                    holder.lnContador.setVisibility( View.GONE );
                    holder.btnAgregar.setVisibility( View.VISIBLE );
                    holder.txtCantidad.setText( "0" );
                    for (int i = 0; i < Variables.detallePedido.size(); i++) {
                        if (Variables.detallePedido.get( i ).getProducto().getId() == productos.get( position ).getId()) {
                            Variables.detallePedido.remove( i );
                        }
                    }
                } else {
                    Cantidad = Cantidad.subtract( BigDecimal.ONE );
                    holder.txtCantidad.setText( Cantidad.toPlainString() );
                    for (int i = 0; i < Variables.detallePedido.size(); i++) {
                        if (Variables.detallePedido.get( i ).getProducto().getId() == productos.get( position ).getId()) {
                            Variables.detallePedido.get( i ).setCantidad( new BigDecimal( holder.txtCantidad.getText().toString() ).doubleValue() );
                        }
                    }
                }

                BigDecimal Total = new BigDecimal( Variables.pedido.getTotal() );
                try {
                    Total = Total.subtract( new BigDecimal( numberFormat.parse( holder.txtVenta.getText().toString() ).toString() ) );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Variables.pedido.setTotal( Total.doubleValue() );
                ((MainActivity) contexto).DisminuirMenu();
            }
        } );

        holder.imgFavorito.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.imgFavorito.getTag().equals( R.drawable.ic_favorite_border_black_24dp )) {
                    holder.imgFavorito.setImageResource( R.drawable.ic_favorite_black_24dp );
                    holder.imgFavorito.setTag( R.drawable.ic_favorite_black_24dp );
                    databaseHelper.InsertarFavoritos( databaseHelper.getWritableDatabase(), new Favoritos(productos.get( position ).getId(), true ) );
                    Toasty.success( holder.imgFavorito.getContext(), "Añadido a mis favoritos!", Toast.LENGTH_SHORT, true ).show();
                } else {
                    databaseHelper.EliminarFavoritos( databaseHelper.getWritableDatabase(), productos.get( position ).getId() );
                    holder.imgFavorito.setImageResource( R.drawable.ic_favorite_border_black_24dp );
                    holder.imgFavorito.setTag( R.drawable.ic_favorite_border_black_24dp );
                    productos.remove( position );
                    Toasty.error( holder.imgFavorito.getContext(), "Eliminado de mis favoritos.", Toast.LENGTH_SHORT, true ).show();
                    if (isFavorite) {
                        notifyDataSetChanged();
                    }
                }
            }
        } );
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

}
