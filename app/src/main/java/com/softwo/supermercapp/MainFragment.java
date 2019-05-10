package com.softwo.supermercapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.softwo.supermercapp.Adaptadores.AdaptadorCategoria;
import com.softwo.supermercapp.Adaptadores.AdaptadorProductos;
import com.softwo.supermercapp.Constantes.FireBase;
import com.softwo.supermercapp.Entidades.Categorias;
import com.softwo.supermercapp.Entidades.Productos;
import com.softwo.supermercapp.Globales.Variables;
import com.softwo.supermercapp.ViewHolder.ViewHolderCategoria;

import java.util.ArrayList;
import java.util.EventListener;

public class MainFragment extends Fragment {

    private RecyclerView mRecyclerViewCategoria;
    private RecyclerView.Adapter mAdapterCategoria;
    private RecyclerView.LayoutManager mlayoutManagerCategoria;
    SearchView searchView;
    LinearLayout lnCargando;
    ConstraintLayout ctDatos;
    ValueEventListener valueEventListenerCategoria;
    ValueEventListener valueEventListenerProductos;
    Query queryCategoria;
    Query queryProductos;
    String TextQuery = "";

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_main, container, false );

        lnCargando = view.findViewById( R.id.lnCargando );
        ctDatos = view.findViewById( R.id.ctDatos );
        mRecyclerViewCategoria = view.findViewById( R.id.gvCategorias );
        searchView = view.findViewById( R.id.searchView );

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        mRecyclerViewCategoria.setHasFixedSize( true );

        queryCategoria = FirebaseDatabase.getInstance().getReference()
                .child( FireBase.BASEDATOS + "/" + FireBase.TABLACATEGORIA );

        queryProductos = FirebaseDatabase.getInstance().getReference()
                .child( FireBase.BASEDATOS + "/" + FireBase.TABLAPRODUCTO );

        valueEventListenerProductos = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mlayoutManagerCategoria = new LinearLayoutManager( getContext() );
                mRecyclerViewCategoria.setLayoutManager( mlayoutManagerCategoria );

                ArrayList<Productos> arrayList = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Productos producto = userSnapshot.getValue( Productos.class );
                    if (producto.Titulo.toLowerCase().contains( TextQuery.toLowerCase() )) {
                        arrayList.add( producto );
                    }
                }
                mAdapterCategoria = new AdaptadorProductos( getContext(), arrayList, false );
                mRecyclerViewCategoria.setAdapter( mAdapterCategoria );

                ctDatos.setVisibility( View.VISIBLE );
                lnCargando.setVisibility( View.GONE );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        valueEventListenerCategoria = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mlayoutManagerCategoria = new GridLayoutManager( getContext(),2 );
                mRecyclerViewCategoria.setLayoutManager( mlayoutManagerCategoria );

                ArrayList<Categorias> arrayList = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    arrayList.add( userSnapshot.getValue( Categorias.class ) );
                }
                mAdapterCategoria = new AdaptadorCategoria( arrayList );
                mRecyclerViewCategoria.setAdapter( mAdapterCategoria );

                ctDatos.setVisibility( View.VISIBLE );
                lnCargando.setVisibility( View.GONE );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        queryCategoria.addValueEventListener( valueEventListenerCategoria );

        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                TextQuery = newText;
                if (newText.equals( "" )) {
                    ctDatos.setVisibility( View.GONE );
                    lnCargando.setVisibility( View.VISIBLE );
                    queryProductos.removeEventListener(valueEventListenerProductos);
                    queryCategoria.addValueEventListener(valueEventListenerCategoria);
                } else {
                    queryCategoria.removeEventListener( valueEventListenerCategoria );
                    queryProductos.addValueEventListener(valueEventListenerProductos);
                }
                return false;
            }
        } );
    }

    @Override
    public void onStart() {
        super.onStart();
        ctDatos.setVisibility( View.GONE );
        lnCargando.setVisibility( View.VISIBLE );
        if (TextQuery.equals( "" )) {
            queryCategoria.addValueEventListener( valueEventListenerCategoria );
        }else{
            queryProductos.addValueEventListener(valueEventListenerProductos);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        ctDatos.setVisibility( View.GONE );
        lnCargando.setVisibility( View.VISIBLE );
        if (TextQuery.equals( "" )) {
            queryCategoria.removeEventListener( valueEventListenerCategoria );
        }else{
            queryProductos.removeEventListener(valueEventListenerProductos);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException( context.toString()
                    + " must implement OnFragmentInteractionListener" );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
