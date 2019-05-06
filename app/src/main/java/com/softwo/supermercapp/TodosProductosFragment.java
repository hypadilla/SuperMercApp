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

import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TodosProductosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TodosProductosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodosProductosFragment extends Fragment {
    private RecyclerView mRecyclerViewProducto;
    private RecyclerView.Adapter mAdapterProducto;
    private RecyclerView.LayoutManager mLayoutManagerProducto;
    private int mTotalItemCountProducto = 0;
    private int mLastVisibleItemPositionProducto;
    private boolean mIsLoadingProducto = false;
    private int mPostsPerPageProducto = 5;

    private TextView txtCategoria;
    private SearchView searchView;

    private LinearLayout lnCargando;
    private ConstraintLayout ctDatos;

    private boolean query = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TodosProductosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodosProductosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodosProductosFragment newInstance(String param1, String param2) {
        TodosProductosFragment fragment = new TodosProductosFragment();
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

        txtCategoria.setText( "Todos los productos" );

        mLayoutManagerProducto = new LinearLayoutManager( getContext() );

        //int resId = R.anim.layout_animation_fall_down;
        //animation = AnimationUtils.loadLayoutAnimation( getContext(), resId );

        //mRecyclerViewProducto.setLayoutAnimation( animation );

        mRecyclerViewProducto.setHasFixedSize( true );

        mRecyclerViewProducto.setLayoutManager( mLayoutManagerProducto );

        mAdapterProducto = new AdaptadorProductos();

        mRecyclerViewProducto.setAdapter( mAdapterProducto );

        getProductos( 0 );

        mRecyclerViewProducto.addOnScrollListener( new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled( recyclerView, dx, dy );

                if (!query){
                    mTotalItemCountProducto = mLayoutManagerProducto.getItemCount();
                    mLastVisibleItemPositionProducto = ((LinearLayoutManager) mLayoutManagerProducto).findLastVisibleItemPosition();

                    if (!mIsLoadingProducto && mTotalItemCountProducto <= (mLastVisibleItemPositionProducto + mPostsPerPageProducto)) {
                        getProductos( ((AdaptadorProductos) mAdapterProducto).getLastItemId() );
                        mIsLoadingProducto = true;
                    } else {

                    }
                }
            }
        } );

        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                query = true;
                mAdapterProducto = new AdaptadorProductos();
                mRecyclerViewProducto.setAdapter( mAdapterProducto );
                getProductosSearch(q);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals( "" )){
                    query = false;
                    mAdapterProducto = new AdaptadorProductos();
                    mRecyclerViewProducto.setAdapter( mAdapterProducto );
                    getProductos( 0 );
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

    private void getProductosSearch( final String consulta) {
        Query query;
            query = FirebaseDatabase.getInstance().getReference( FireBase.BASEDATOS )
                    .child( FireBase.TABLAPRODUCTO );
        query.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Productos> productos = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Productos producto = userSnapshot.getValue( Productos.class );
                    if (producto.getTitulo().toLowerCase().contains( consulta.toLowerCase().trim() )){
                        productos.add( producto );
                    }
                }

                ((AdaptadorProductos) mAdapterProducto).addAll( productos );
                mIsLoadingProducto = false;

                ctDatos.setVisibility( View.VISIBLE );
                lnCargando.setVisibility( View.GONE );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mIsLoadingProducto = false;
            }
        } );
    }

    private void getProductos(long nodeId) {
        Query query;

        if (nodeId == 0)
            query = FirebaseDatabase.getInstance().getReference( FireBase.BASEDATOS )
                    .child( FireBase.TABLAPRODUCTO )
                    .orderByKey()
                    .limitToFirst( mPostsPerPageProducto );
        else
            query = FirebaseDatabase.getInstance().getReference( FireBase.BASEDATOS )
                    .child( FireBase.TABLAPRODUCTO )
                    .orderByKey()
                    .startAt( String.valueOf( nodeId + 1 ) )
                    .limitToFirst( mPostsPerPageProducto );

        query.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Productos> productos = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    productos.add( userSnapshot.getValue( Productos.class ) );
                }

                ((AdaptadorProductos) mAdapterProducto).addAll( productos );
                mIsLoadingProducto = false;


                ctDatos.setVisibility( View.VISIBLE );
                lnCargando.setVisibility( View.GONE );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mIsLoadingProducto = false;
            }
        } );
    }
}
