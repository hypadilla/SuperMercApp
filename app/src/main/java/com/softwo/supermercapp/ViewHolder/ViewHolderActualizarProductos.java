package com.softwo.supermercapp.ViewHolder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.softwo.supermercapp.Constantes.FireBase;
import com.softwo.supermercapp.FragmentsProveedores.RegistroProductosFragment;
import com.softwo.supermercapp.R;

public class ViewHolderActualizarProductos extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

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

    public ViewHolderActualizarProductos(@NonNull View itemView) {
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

    /**
     * Called when a view has been clicked and held.
     *
     * @param v The view that was clicked and held.
     * @return true if the callback consumed the long click, false otherwise.
     */
    @Override
    public boolean onLongClick(View v) {
        AlertDialog alertDialog = new AlertDialog.Builder( cardView.getContext() ).create();

        alertDialog.setTitle( "Dialog Button" );

        alertDialog.setMessage( "This is a three-button dialog!" );

        alertDialog.setButton( AlertDialog.BUTTON_POSITIVE, "Eliminar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                //...

            }
        } );

        alertDialog.setButton( AlertDialog.BUTTON_NEGATIVE, "Actualizar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                /*Query query = FirebaseDatabase.getInstance().getReference( FireBase.BASEDATOS )
                        .child( FireBase.TABLAPRODUCTO )
                        .orderByChild( "Id" )
                        .equalTo( .get( getAdapterPosition() ).getId() );

                query.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            FragmentManager fragmentManager = ((FragmentActivity) cardView.getContext()).getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction;
                            RegistroProductosFragment registroProductosFragment = RegistroProductosFragment.newInstance( userSnapshot.getKey() );
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace( R.id.fragment, registroProductosFragment ).addToBackStack( "RegistroProductosFragment" );
                            fragmentTransaction.commit();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                } );*/

            }
        } );

        alertDialog.setButton( AlertDialog.BUTTON_NEUTRAL, "Cancelar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                //...

            }
        } );


        return true;
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }
}
