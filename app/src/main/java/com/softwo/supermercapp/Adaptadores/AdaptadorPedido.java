package com.softwo.supermercapp.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class AdaptadorPedido extends RecyclerView.Adapter<AdaptadorPedido.PedidoViewHolder> {
    private ArrayList<DetallePedido> mDataset;
    private Context contexto;

    private NumberFormat numberFormatPercent = NumberFormat.getPercentInstance( Locale.getDefault() );

    private NumberFormat numberFormatCurrency = NumberFormat.getCurrencyInstance( Locale.getDefault() );

    private NumberFormat numberFormat = NumberFormat.getInstance( Locale.getDefault() );

    private DatabaseHelper databaseHelper;
    private TextView txtTotal;


    public AdaptadorPedido(ArrayList<DetallePedido> mDataset, Context contexto, TextView txtTotal) {
        this.mDataset = mDataset;
        this.contexto = contexto;
        this.txtTotal = txtTotal;
        databaseHelper = new DatabaseHelper( contexto );
        numberFormatPercent.setMinimumFractionDigits(0);
    }

    public AdaptadorPedido(ArrayList<DetallePedido> mDataset , Context contexto) {
        this.mDataset = mDataset;
        this.contexto = contexto;
        databaseHelper = new DatabaseHelper( contexto );
        numberFormatPercent.setMinimumFractionDigits(0);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdaptadorPedido.PedidoViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_productos, parent, false );
        AdaptadorPedido.PedidoViewHolder pvh = new AdaptadorPedido.PedidoViewHolder( v );
        return pvh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final AdaptadorPedido.PedidoViewHolder holder, final int position) {
        Glide.with( holder.imgImagen.getContext() )
                .load( mDataset.get( position ).getProducto().getImagen() )
                .thumbnail( 0.1f )
                .centerCrop()
                .diskCacheStrategy( DiskCacheStrategy.ALL )
                .into( holder.imgImagen);


        //Picasso.get().load( mDataset.get( position ).getProducto().getImagen() ).into( holder.imgImagen );
        holder.txtTitulo.setText( mDataset.get( position ).getProducto().getTitulo() );
        holder.txtPresentacion.setText( "Presentación (" + mDataset.get( position ).getProducto().getPresentacion() + ")" );

        holder.lnContador.setVisibility( View.VISIBLE );
        holder.btnAgregar.setVisibility( View.GONE );

        holder.txtCantidad.setText( String.valueOf( mDataset.get( position ).getCantidad() ) );

        if (databaseHelper.ExisteFavoritos( databaseHelper.getWritableDatabase(), mDataset.get( position ).getProducto().getId() )) {
            holder.imgFavorito.setTag( R.drawable.ic_favorite_black_24dp );
            holder.imgFavorito.setImageResource( R.drawable.ic_favorite_black_24dp );
        } else {
            holder.imgFavorito.setTag( R.drawable.ic_favorite_border_black_24dp );
            holder.imgFavorito.setImageResource( R.drawable.ic_favorite_border_black_24dp );
        }

        if (mDataset.get( position ).getProducto().getDescuento() == 0) {
            holder.txtVenta.setText( numberFormat.format( mDataset.get( position ).getProducto().getVenta() ) );

            holder.txtVentaOff.setVisibility( View.GONE );
            holder.imgVentaOff.setVisibility( View.GONE );
            holder.txtDescuento.setVisibility( View.GONE );
        } else {
            holder.txtVentaOff.setText( numberFormat.format( mDataset.get( position ).getProducto().getVenta() ) );
            holder.txtDescuento.setText( numberFormatPercent.format(mDataset.get( position ).getProducto().getDescuento()/100) );
            double ValorVenta = mDataset.get( position ).getProducto().getVenta() - (mDataset.get( position ).getProducto().getVenta() * (mDataset.get( position ).getProducto().getDescuento() / 100));
            holder.txtVenta.setText( numberFormat.format( ValorVenta ) );
        }

        if (txtTotal == null){
            holder.btnAgregar.setVisibility( View.GONE );
            holder.lnContador.setVisibility( View.VISIBLE );
            holder.imgbtnMenos.setVisibility( View.INVISIBLE );
            holder.imgbtnAdicionar.setVisibility( View.INVISIBLE );
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
                detallePedido.setProducto( mDataset.get( position ).getProducto() );
                Variables.detallePedido.add( detallePedido );

                BigDecimal Total = new BigDecimal( Variables.pedido.getTotal() );
                try {
                    Total = Total.add( new BigDecimal( numberFormat.parse( holder.txtVenta.getText().toString() ).toString() ) );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Variables.pedido.setTotal( Total.doubleValue() );

                txtTotal.setText( numberFormatCurrency.format( new BigDecimal( Variables.pedido.getTotal() ) ) );
            }
        } );

        holder.imgbtnAdicionar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigDecimal Cantidad = new BigDecimal( holder.txtCantidad.getText().toString() );
                Cantidad = Cantidad.add( BigDecimal.ONE );
                holder.txtCantidad.setText( Cantidad.toPlainString() );

                for (int i = 0; i < Variables.detallePedido.size(); i++) {
                    if (Variables.detallePedido.get( i ).getProducto().getId() == mDataset.get( position ).getProducto().getId()) {
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

                txtTotal.setText( numberFormatCurrency.format( new BigDecimal( Variables.pedido.getTotal() ) ) );
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
                        if (Variables.detallePedido.get( i ).getProducto().getId() == mDataset.get( position ).getProducto().getId()) {
                            Variables.detallePedido.remove( i );
                        }
                    }
                } else {
                    Cantidad = Cantidad.subtract( BigDecimal.ONE );
                    holder.txtCantidad.setText( Cantidad.toPlainString() );
                    for (int i = 0; i < Variables.detallePedido.size(); i++) {
                        if (Variables.detallePedido.get( i ).getProducto().getId() == mDataset.get( position ).getProducto().getId()) {
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

                txtTotal.setText( numberFormatCurrency.format( new BigDecimal( Variables.pedido.getTotal() ) ) );
            }
        } );

        holder.imgFavorito.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.imgFavorito.getTag().equals( R.drawable.ic_favorite_border_black_24dp )) {
                    holder.imgFavorito.setImageResource( R.drawable.ic_favorite_black_24dp );
                    holder.imgFavorito.setTag( R.drawable.ic_favorite_black_24dp );
                    databaseHelper.InsertarFavoritos( databaseHelper.getWritableDatabase(), new Favoritos( mDataset.get( position ).getProducto().getId(), true ) );
                    Toasty.success( contexto, "Añadido a mis favoritos!", Toast.LENGTH_SHORT, true ).show();
                } else {
                    databaseHelper.EliminarFavoritos( databaseHelper.getWritableDatabase(), mDataset.get( position ).getProducto().getId() );
                    holder.imgFavorito.setImageResource( R.drawable.ic_favorite_border_black_24dp );
                    holder.imgFavorito.setTag( R.drawable.ic_favorite_border_black_24dp );
                    Toasty.error( contexto, "Eliminado de mis favoritos.", Toast.LENGTH_SHORT, true ).show();
                }
            }
        } );
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class PedidoViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
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

        public PedidoViewHolder(View v) {
            super( v );
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
        }
    }
}
