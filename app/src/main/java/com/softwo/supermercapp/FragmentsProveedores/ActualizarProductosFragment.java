package com.softwo.supermercapp.FragmentsProveedores;

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

import com.softwo.supermercapp.Adaptadores.AdaptadorActualizarProductos;
import com.softwo.supermercapp.Adaptadores.AdaptadorProductos;
import com.softwo.supermercapp.Entidades.Productos;
import com.softwo.supermercapp.Globales.Variables;
import com.softwo.supermercapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActualizarProductosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActualizarProductosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActualizarProductosFragment extends Fragment {
    private ArrayList<Productos> mFiltro;

    private RecyclerView mRecyclerViewProducto;
    private RecyclerView.Adapter mAdapterProducto;
    private RecyclerView.LayoutManager mLayoutManagerProducto;
    private int mPostsPerPageProducto = 50;

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

    public ActualizarProductosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActualizarProductosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActualizarProductosFragment newInstance(String param1, String param2) {
        ActualizarProductosFragment fragment = new ActualizarProductosFragment();
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

        txtCategoria.setText( "Actualización de productos" );

        mLayoutManagerProducto = new LinearLayoutManager( getContext() );

        mRecyclerViewProducto.setHasFixedSize( true );
        mRecyclerViewProducto.setItemViewCacheSize( mPostsPerPageProducto );
        mRecyclerViewProducto.setDrawingCacheEnabled( true );
        mRecyclerViewProducto.setDrawingCacheQuality( View.DRAWING_CACHE_QUALITY_HIGH );
        mRecyclerViewProducto.setLayoutManager( mLayoutManagerProducto );

        mAdapterProducto = new AdaptadorActualizarProductos( Variables.LISTAPRODUCTOS, getContext(), lnCargando, ctDatos );

        mRecyclerViewProducto.setAdapter( mAdapterProducto );

        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mFiltro = new ArrayList<>();
                for (Productos producto :
                        Variables.LISTAPRODUCTOS) {
                    if (producto.getTitulo().toUpperCase().contains( query.toUpperCase() ))
                        if (producto.Estado)
                            mFiltro.add( producto );
                }

                mAdapterProducto = new AdaptadorActualizarProductos( mFiltro, getContext(), lnCargando, ctDatos );
                mRecyclerViewProducto.setAdapter( mAdapterProducto );

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals( "" )) {
                    mAdapterProducto = new AdaptadorActualizarProductos( Variables.LISTAPRODUCTOS, getContext(), lnCargando, ctDatos );
                    mRecyclerViewProducto.setAdapter( mAdapterProducto );
                } else {
                    mFiltro = new ArrayList<>();
                    for (Productos producto :
                            Variables.LISTAPRODUCTOS) {
                        if (producto.getTitulo().toUpperCase().contains( newText.toUpperCase() ))
                            if (producto.Estado)
                                mFiltro.add( producto );
                    }

                    mAdapterProducto = new AdaptadorActualizarProductos( mFiltro, getContext(), lnCargando, ctDatos );
                    mRecyclerViewProducto.setAdapter( mAdapterProducto );
                }
                return false;
            }
        } );

        lnCargando.setVisibility( View.GONE );
        ctDatos.setVisibility( View.VISIBLE );
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
}
