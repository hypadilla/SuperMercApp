package com.softwo.supermercapp.ViewHolder;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.softwo.supermercapp.Adaptadores.AdaptadorPedido;
import com.softwo.supermercapp.Adaptadores.AdaptadorPedidos;
import com.softwo.supermercapp.Entidades.DetallePedido;
import com.softwo.supermercapp.Entidades.Pedidos;
import com.softwo.supermercapp.R;
import com.softwo.supermercapp.Sqlite.Contract.DetallePedidoContract;
import com.softwo.supermercapp.Sqlite.Contract.PedidosContract;
import com.softwo.supermercapp.Sqlite.Helper.DatabaseHelper;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

import static android.provider.BaseColumns._ID;

public class ViewHolderPedidos extends RecyclerView.ViewHolder {
    private NumberFormat numberFormat = NumberFormat.getInstance( Locale.getDefault() );
    DatabaseHelper databaseHelper;


    private TextView txtTitulo;
    private TextView txtSerial;
    private TextView txtFecha;
    private TextView txtVenta;
    private RecyclerView recyclerView;
    private ImageButton btnUp;

    public ViewHolderPedidos(@NonNull View itemView) {
        super( itemView );
        findViews( itemView );
    }

    private void findViews(View v) {
        txtTitulo = v.findViewById( R.id.txtTitulo );
        txtSerial = v.findViewById( R.id.txtSerial );
        txtFecha = v.findViewById( R.id.txtFecha );
        txtVenta = v.findViewById( R.id.txtVenta );
        recyclerView = v.findViewById( R.id.recyclerView );
        btnUp = v.findViewById( R.id.btnUp );

        databaseHelper = new DatabaseHelper( txtTitulo.getContext() );
    }

    public void setData(final Pedidos pedidos) {
        txtTitulo.setText( "Pedido N°: " + pedidos.get_Id() );
        txtSerial.setText( "Serial: " + pedidos.getId() );
        txtFecha.setText( "Fecha: " + pedidos.getFecha() );
        txtVenta.setText( numberFormat.format( new BigDecimal( pedidos.getTotal() ) ) );

        RecyclerView.Adapter mAdapterDetallePedidos;
        RecyclerView.LayoutManager mLayoutManagerDetallePedidos;

        mLayoutManagerDetallePedidos = new LinearLayoutManager( recyclerView.getContext(), LinearLayoutManager.VERTICAL, false );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( mLayoutManagerDetallePedidos );
        mAdapterDetallePedidos = new AdaptadorPedido( pedidos.getDetallePedido(), recyclerView.getContext(), null );
        recyclerView.setAdapter( mAdapterDetallePedidos );

        btnUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerView.getVisibility() == View.GONE) {
                    recyclerView.setVisibility( View.VISIBLE );
                } else {
                    recyclerView.setVisibility( View.GONE );
                }
            }
        } );
    }

    private void Estado(int estado){

    }
}
