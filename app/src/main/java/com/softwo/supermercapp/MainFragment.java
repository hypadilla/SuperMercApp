package com.softwo.supermercapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.softwo.supermercapp.Adaptadores.AdaptadorCategoria;
import com.softwo.supermercapp.Adaptadores.AdaptadorProductos;
import com.softwo.supermercapp.Constantes.FireBase;
import com.softwo.supermercapp.Entidades.Categorias;
import com.softwo.supermercapp.Entidades.Productos;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    private int mContador = 0;

    //private ImageButton imgbtnUp;
    private LinearLayout lnCargando;
    private ConstraintLayout ctDatos;

    LayoutAnimationController animation;

    //Paginacion en el Recyclerview de Producto
    /*private RecyclerView mRecyclerViewProducto;
    private RecyclerView.Adapter mAdapterProducto;
    private RecyclerView.LayoutManager mLayoutManagerProducto;
    private int mTotalItemCountProducto = 0;
    private int mLastVisibleItemPositionProducto;
    private boolean mIsLoadingProducto = false;
    private int mPostsPerPageProducto = 5;*/

    //Paginacion en el Recyclerview de Categorias
    private RecyclerView mRecyclerViewCategoria;
    private RecyclerView.Adapter mAdapterCategoria;
    private RecyclerView.LayoutManager mlayoutManagerCategoria;
    private int mTotalItemCountCategoria = 0;
    private int mLastVisibleItemPositionCategoria;
    private boolean mIsLoadingCategoria = false;
    private int mPostsPerPageCategoria = 10;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        View view = inflater.inflate( R.layout.fragment_main, container, false );

        //mRecyclerViewProducto = view.findViewById( R.id.rvDescuentosDiarios );
        mRecyclerViewCategoria = view.findViewById( R.id.gvCategorias );
        //imgbtnUp = view.findViewById( R.id.imgbtnUp );
        lnCargando = view.findViewById( R.id.lnCargando );
        ctDatos = view.findViewById( R.id.ctDatos );

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        //mLayoutManagerProducto = new LinearLayoutManager( getContext(), LinearLayoutManager.HORIZONTAL, false );
        //((LinearLayoutManager) mLayoutManagerProducto).setStackFromEnd( true );

        mlayoutManagerCategoria = new GridLayoutManager( getActivity(), 2 );

        //int resId = R.anim.layout_animation_fall_down;
        //animation = AnimationUtils.loadLayoutAnimation( getContext(), resId );

        //mRecyclerViewProducto.setLayoutAnimation( animation );
        //mRecyclerViewCategoria.setLayoutAnimation( animation );

        //mRecyclerViewProducto.setHasFixedSize( true );
        mRecyclerViewCategoria.setHasFixedSize( true );

        //mRecyclerViewProducto.setLayoutManager( mLayoutManagerProducto );
        mRecyclerViewCategoria.setLayoutManager( mlayoutManagerCategoria );

        //mAdapterProducto = new AdaptadorProductos( );
        mAdapterCategoria = new AdaptadorCategoria();

        //mRecyclerViewProducto.setAdapter( mAdapterProducto );
        mRecyclerViewCategoria.setAdapter( mAdapterCategoria );

        //getProductos(0);
        getCategorias( 0 );

        /*mRecyclerViewProducto.addOnScrollListener( new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mTotalItemCountProducto = mLayoutManagerProducto.getItemCount();
                mLastVisibleItemPositionProducto = ((LinearLayoutManager) mLayoutManagerProducto).findLastVisibleItemPosition();

                if (!mIsLoadingProducto && mTotalItemCountProducto <= (mLastVisibleItemPositionProducto + mPostsPerPageProducto)) {
                    getProductos(((AdaptadorProductos) mAdapterProducto).getLastItemId(  ));
                    mIsLoadingProducto = true;
                }
            }
        });*/

        mRecyclerViewCategoria.addOnScrollListener( new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled( recyclerView, dx, dy );

                mTotalItemCountCategoria = mlayoutManagerCategoria.getItemCount();
                mLastVisibleItemPositionCategoria = ((LinearLayoutManager) mlayoutManagerCategoria).findLastVisibleItemPosition();

                if (!mIsLoadingCategoria && mTotalItemCountCategoria <= (mLastVisibleItemPositionCategoria + mPostsPerPageCategoria)) {
                    getCategorias( ((AdaptadorCategoria) mAdapterCategoria).getLastItemId() );
                    mIsLoadingCategoria = true;
                }
            }
        } );

        /*imgbtnUp.setTag( R.drawable.ic_arrow_drop_up_black_24dp );
        imgbtnUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerViewProducto.setVisibility( View.GONE );

                if (imgbtnUp.getTag().equals( R.drawable.ic_arrow_drop_up_black_24dp )) {
                    imgbtnUp.setImageResource( R.drawable.ic_arrow_drop_down_black_24dp );
                    imgbtnUp.setTag( R.drawable.ic_arrow_drop_down_black_24dp );
                    mRecyclerViewProducto.setVisibility( View.GONE );
                } else {
                    imgbtnUp.setImageResource( R.drawable.ic_arrow_drop_up_black_24dp );
                    imgbtnUp.setTag( R.drawable.ic_arrow_drop_up_black_24dp );
                    mRecyclerViewProducto.setVisibility( View.VISIBLE );
                }
            }
        } );*/
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

    /*
        private void getProductos(long nodeId) {
            Query query;

            if (nodeId == 0)
                query = FirebaseDatabase.getInstance().getReference()
                        .child( FireBase.BASEDATOS + "/" + FireBase.TABLAPRODUCTO )
                        .orderByKey()
                        .limitToFirst( mPostsPerPageProducto );
            else
                query = FirebaseDatabase.getInstance().getReference()
                        .child( FireBase.BASEDATOS + "/" + FireBase.TABLAPRODUCTO )
                        .orderByKey()
                        .startAt( String.valueOf(nodeId + 1))
                        .limitToFirst( mPostsPerPageProducto );

            query.addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<Productos> productos = new ArrayList<>();
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        Productos producto = userSnapshot.getValue( Productos.class );

                        if (producto.getDescuento() > 0){
                            productos.add( producto );
                        }
                    }

                    ((AdaptadorProductos) mAdapterProducto).addAll( productos );
                    mIsLoadingProducto = false;

                    mContador += 50;

                    if (mContador == 100){
                        ctDatos.setVisibility( View.VISIBLE );
                        lnCargando.setVisibility( View.GONE );
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    mIsLoadingProducto = false;
                }
            } );
        }
    */

    private void getCategorias(long nodeId) {
        Query query;

        if (nodeId == 0)
            query = FirebaseDatabase.getInstance().getReference()
                    .child( FireBase.BASEDATOS + "/" + FireBase.TABLACATEGORIA )
                    .orderByKey()
                    .limitToFirst( mPostsPerPageCategoria );
        else
            query = FirebaseDatabase.getInstance().getReference()
                    .child( FireBase.BASEDATOS + "/" + FireBase.TABLACATEGORIA )
                    .orderByKey()
                    .startAt( String.valueOf( nodeId + 1 ) )
                    .limitToFirst( mPostsPerPageCategoria );

        query.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Categorias> Categorias = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Categorias.add( userSnapshot.getValue( Categorias.class ) );
                }

                ((AdaptadorCategoria) mAdapterCategoria).addAll( Categorias );
                mIsLoadingCategoria = false;

                    ctDatos.setVisibility( View.VISIBLE );
                    lnCargando.setVisibility( View.GONE );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mIsLoadingCategoria = false;
            }
        } );
    }
}
