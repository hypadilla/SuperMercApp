package com.softwo.supermercapp.FragmentsProveedores;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.softwo.supermercapp.Adaptadores.AdaptadorActualizarProductos;
import com.softwo.supermercapp.Adaptadores.AdaptadorProductos;
import com.softwo.supermercapp.Constantes.FireBase;
import com.softwo.supermercapp.Entidades.Productos;
import com.softwo.supermercapp.Globales.Variables;
import com.softwo.supermercapp.R;

import java.util.ArrayList;

public class ActualizarProductosFragment extends Fragment {
    private RecyclerView mRecyclerViewProducto;
    private RecyclerView.LayoutManager mLayoutManagerProducto;
    private RecyclerView.Adapter mAdapterProducto;
    private TextView txtCategoria;
    private SearchView searchView;
    ValueEventListener valueEventListenerProductos;
    Query queryProductos;
    private LinearLayout lnCargando;
    private ConstraintLayout ctDatos;
    String TextQuery = "";

    private OnFragmentInteractionListener mListener;

    public ActualizarProductosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_productos, container, false );

        txtCategoria = view.findViewById( R.id.txtCategoria );
        mRecyclerViewProducto = view.findViewById( R.id.rvProductos );
        searchView = view.findViewById( R.id.searchView );
        lnCargando = view.findViewById( R.id.lnCargando );
        ctDatos = view.findViewById( R.id.ctDatos );

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        txtCategoria.setText( "Actualización de productos" );

        mLayoutManagerProducto = new LinearLayoutManager( getContext() );
        mRecyclerViewProducto.setHasFixedSize( true );
        mRecyclerViewProducto.setLayoutManager( mLayoutManagerProducto );

        queryProductos = FirebaseDatabase.getInstance().getReference()
                .child( FireBase.BASEDATOS + "/" + FireBase.TABLAPRODUCTO );

        valueEventListenerProductos = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Productos> arrayList = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Productos producto = userSnapshot.getValue( Productos.class );
                        if (!TextQuery.equals( "" )) {
                            if (producto.Titulo.toLowerCase().contains( TextQuery.toLowerCase() )) {
                                arrayList.add( producto );
                            }
                        } else {
                            arrayList.add( producto );
                        }
                }
                mAdapterProducto = new AdaptadorActualizarProductos(arrayList,  getContext(), lnCargando, ctDatos );
                mRecyclerViewProducto.setAdapter( mAdapterProducto );

                ctDatos.setVisibility( View.VISIBLE );
                lnCargando.setVisibility( View.GONE );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        queryProductos.addValueEventListener( valueEventListenerProductos );

        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                TextQuery = newText;
                if (TextQuery.equals( "" )) {
                    ctDatos.setVisibility( View.VISIBLE );
                    lnCargando.setVisibility( View.GONE );
                }
                queryProductos.removeEventListener( valueEventListenerProductos );
                queryProductos.addValueEventListener( valueEventListenerProductos );
                return false;
            }
        } );
    }

    @Override
    public void onStart() {
        super.onStart();
        queryProductos.addValueEventListener(valueEventListenerProductos);
    }

    @Override
    public void onStop() {
        super.onStop();
        queryProductos.removeEventListener( valueEventListenerProductos );
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
