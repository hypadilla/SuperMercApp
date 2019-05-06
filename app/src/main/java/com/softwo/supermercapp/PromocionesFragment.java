package com.softwo.supermercapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import com.softwo.supermercapp.Adaptadores.AdaptadorProductos;
import com.softwo.supermercapp.Constantes.FireBase;
import com.softwo.supermercapp.Entidades.Productos;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PromocionesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PromocionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PromocionesFragment extends Fragment {
    private RecyclerView mRecyclerViewProducto;
    private RecyclerView.Adapter mAdapterProducto;
    private RecyclerView.LayoutManager mLayoutManagerProducto;
    private TextView txtCategoria;
    private SearchView searchView;
    private LinearLayout lnCargando;
    private ConstraintLayout ctDatos;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PromocionesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PromocionesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PromocionesFragment newInstance(String param1, String param2) {
        PromocionesFragment fragment = new PromocionesFragment();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }
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

        txtCategoria.setText( "Productos en promociones" );

        mLayoutManagerProducto = new LinearLayoutManager( getContext(),  LinearLayoutManager.VERTICAL, true  );
        ((LinearLayoutManager) mLayoutManagerProducto).setReverseLayout( true );

        mRecyclerViewProducto.setHasFixedSize( true );

        mRecyclerViewProducto.setLayoutManager( mLayoutManagerProducto );

        mAdapterProducto = new AdaptadorProductos();

        mRecyclerViewProducto.setAdapter( mAdapterProducto );

        getProductos(  );

        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                mAdapterProducto = new AdaptadorProductos();
                mRecyclerViewProducto.setAdapter( mAdapterProducto );
                getProductosSearch( q );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals( "" )) {
                    mAdapterProducto = new AdaptadorProductos();
                    mRecyclerViewProducto.setAdapter( mAdapterProducto );
                    getProductos( );
                }
                return false;
            }
        } );
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction( uri );
        }
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

    private void getProductosSearch(final String consulta) {
        Query query;

        //if (nodeId == 0)
        query = FirebaseDatabase.getInstance().getReference( FireBase.BASEDATOS )
                .child( FireBase.TABLAPRODUCTO )
                .orderByChild( "Descuento" );
        query.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Productos> productos = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Productos producto = userSnapshot.getValue( Productos.class );
                    if (producto.getTitulo().toLowerCase().contains( consulta.toLowerCase().trim() )) {
                        if (producto.getDescuento() > 0)
                            productos.add( producto );
                    }
                }

                ((AdaptadorProductos) mAdapterProducto).addAll( productos );

                ctDatos.setVisibility( View.VISIBLE );
                lnCargando.setVisibility( View.GONE );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        } );
    }

    private void getProductos() {
        Query query = FirebaseDatabase.getInstance().getReference( FireBase.BASEDATOS )
                .child( FireBase.TABLAPRODUCTO )
                .orderByChild( "Descuento" );

        query.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Productos> productos = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Productos producto = userSnapshot.getValue(Productos.class);
                    if (producto.getDescuento() > 0)
                        productos.add( producto );
                }
                ((AdaptadorProductos) mAdapterProducto).addAll( productos );

                ctDatos.setVisibility( View.VISIBLE );
                lnCargando.setVisibility( View.GONE );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        } );
    }
}
