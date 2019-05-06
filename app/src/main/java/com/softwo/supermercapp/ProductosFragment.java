package com.softwo.supermercapp;

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
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
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
import com.softwo.supermercapp.Sqlite.Helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import static com.softwo.supermercapp.Globales.Variables.databaseHelper;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductosFragment extends Fragment {

    private RecyclerView mRecyclerViewProducto;
    private RecyclerView.Adapter mAdapterProducto;
    private RecyclerView.LayoutManager mLayoutManagerProducto;

    private TextView txtCategoria;
    private SearchView searchView;

    private LinearLayout lnCargando;
    private ConstraintLayout ctDatos;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CATEGORIA = "Categoria";

    // TODO: Rename and change types of parameters
    private String mCategoria;

    private OnFragmentInteractionListener mListener;

    public ProductosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ProductosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductosFragment newInstance(String param1) {
        ProductosFragment fragment = new ProductosFragment();
        Bundle args = new Bundle();
        args.putString( ARG_CATEGORIA, param1 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mCategoria = getArguments().getString( ARG_CATEGORIA, "" );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

        txtCategoria.setText( mCategoria );

        mLayoutManagerProducto = new LinearLayoutManager( getContext(), LinearLayoutManager.VERTICAL, false );

        //int resId = R.anim.layout_animation_fall_down;
        //animation = AnimationUtils.loadLayoutAnimation( getContext(), resId );

        //mRecyclerViewProducto.setLayoutAnimation( animation );

        mRecyclerViewProducto.setHasFixedSize( true );

        mRecyclerViewProducto.setLayoutManager( mLayoutManagerProducto );

        mAdapterProducto = new AdaptadorProductos();

        mRecyclerViewProducto.setAdapter( mAdapterProducto );

        getProductos();

        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                ctDatos.setVisibility( View.GONE );
                lnCargando.setVisibility( View.VISIBLE );
                getProductosSearch(  query );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals( "" )) {
                    ctDatos.setVisibility( View.GONE );
                    lnCargando.setVisibility( View.VISIBLE );
                    getProductos();
                }
                return false;
            }
        } );
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
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
                .orderByChild( "Categoria" );
        query.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Productos> productos = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Productos producto = userSnapshot.getValue( Productos.class );
                    if (producto.getTitulo().toLowerCase().contains( consulta.toLowerCase().trim() )) {
                            if (producto.getCategoria().equals( mCategoria )) {
                                productos.add( producto );
                            }
                       // }
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
                .orderByChild( "Categoria" )
                .equalTo( mCategoria );

        query.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Productos> productos = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Productos producto = userSnapshot.getValue( Productos.class );
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
