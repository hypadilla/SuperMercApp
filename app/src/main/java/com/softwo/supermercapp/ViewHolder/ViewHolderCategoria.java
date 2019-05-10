package com.softwo.supermercapp.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.softwo.supermercapp.Entidades.Categorias;
import com.softwo.supermercapp.ProductosFragment;
import com.softwo.supermercapp.R;

public class ViewHolderCategoria extends RecyclerView.ViewHolder {

    public CardView cvTarjeta;
    public TextView txtTitulo;
    public ImageView imgImagen;

    public ViewHolderCategoria(@NonNull View itemView) {
        super( itemView );
        findViews( itemView );
    }

    private void findViews(View view) {
        txtTitulo = view.findViewById( R.id.txtTitulo );
        imgImagen = view.findViewById( R.id.imgImagen );
        cvTarjeta = view.findViewById( R.id.cvTarjeta );
    }

    public void setData(final Categorias categoria) {

        txtTitulo.setText( categoria.getTitulo() );

        cvTarjeta.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductosFragment productosFragment = ProductosFragment.newInstance( categoria.getTitulo() );
                ((FragmentActivity) cvTarjeta.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack( "ProductoFragment" )
                        .replace( R.id.fragment, productosFragment )
                        .commit();
            }
        } );

        if (!categoria.getImagen().equals( null )){
            Glide.with( imgImagen.getContext() )
                    .load( categoria.getImagen() )
                    .thumbnail( 0.1f )
                    .centerCrop()
                    .diskCacheStrategy( DiskCacheStrategy.ALL )
                    .into( imgImagen );
        }
    }
}
