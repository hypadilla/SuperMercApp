package com.softwo.supermercapp.Adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.softwo.supermercapp.Constantes.FireBase;
import com.softwo.supermercapp.Entidades.Productos;
import com.softwo.supermercapp.FragmentsProveedores.ActualizarProductosFragment;
import com.softwo.supermercapp.FragmentsProveedores.ProveedoresFragment;
import com.softwo.supermercapp.FragmentsProveedores.RegistroProductosFragment;
import com.softwo.supermercapp.Globales.Variables;
import com.softwo.supermercapp.R;
import com.softwo.supermercapp.Sqlite.Helper.DatabaseHelper;
import com.softwo.supermercapp.ViewHolder.ViewHolderProducto;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdaptadorActualizarProductos extends RecyclerView.Adapter<ViewHolderProducto> {

    private ArrayList<Productos> productos;
    private NumberFormat numberFormatPercent = NumberFormat.getPercentInstance( Locale.getDefault() );
    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance( Locale.getDefault() );

    LinearLayout lnCargando;
    ConstraintLayout cnDatos;

    Context contexto;

    public AdaptadorActualizarProductos(ArrayList<Productos> productos, Context contexto, LinearLayout lnCargando, ConstraintLayout cnDatos) {
        this.productos = productos;
        this.contexto = contexto;
        this.cnDatos = cnDatos;
        this.lnCargando = lnCargando;
        numberFormatPercent.setMinimumFractionDigits( 0 );

    }

    @NonNull
    @Override
    public ViewHolderProducto onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolderProducto( LayoutInflater.from( contexto )
                .inflate( R.layout.item_productos, viewGroup, false ) );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProducto holder, final int position) {
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

        holder.imgFavorito.setVisibility( View.INVISIBLE );

        holder.btnAgregar.setText( "Acciones" );


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


        holder.btnAgregar.setOnClickListener( new View.OnClickListener() {
            /**
             * Called when a view has been clicked and held.
             *
             * @param v The view that was clicked and held.
             * @return true if the callback consumed the long click, false otherwise.
             */
            @Override
            public void onClick(View v) {
                lnCargando.setVisibility( View.VISIBLE );
                cnDatos.setVisibility( View.GONE );

                Query query = FirebaseDatabase.getInstance().getReference( FireBase.BASEDATOS )
                        .child( FireBase.TABLAPRODUCTO )
                        .orderByChild( "Id" ).equalTo( productos.get( position ).Id );

                query.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            Productos producto = userSnapshot.getValue(Productos.class);
                            //if (producto.Id == productos.get( position ).Id){
                                FragmentManager fragmentManager = ((FragmentActivity)contexto).getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction;
                                RegistroProductosFragment registroProductosFragment = RegistroProductosFragment.newInstance((int)(producto.Id));
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace( R.id.fragment, registroProductosFragment ).addToBackStack( "RegistroProductosFragment" );
                                fragmentTransaction.commit();
                            //}
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                } );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }
}
