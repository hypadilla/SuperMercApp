package com.softwo.supermercapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.softwo.supermercapp.Adaptadores.AdaptadorPedido;
import com.softwo.supermercapp.Constantes.FireBase;
import com.softwo.supermercapp.Entidades.Pedidos;
import com.softwo.supermercapp.Globales.Variables;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PedidoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PedidoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PedidoFragment extends Fragment {
    String pattern = " dd/MM/yyyy hh:mm:ss aa";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    Button btnEliminar;
    Button btnComprar;
    EditText txtNota;
    TextView txtTotal;
    TextView txtTope;

    private LinearLayout lnCargando;
    private ConstraintLayout ctDatos;

    NumberFormat numberFormatCurrency = NumberFormat.getCurrencyInstance( Locale.getDefault() );

    RecyclerView rvProductosPedido;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PedidoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PedidoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PedidoFragment newInstance(String param1, String param2) {
        PedidoFragment fragment = new PedidoFragment();
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
        View view = inflater.inflate( R.layout.fragment_pedido, container, false );
        btnEliminar = view.findViewById( R.id.btnEliminar );
        btnComprar = view.findViewById( R.id.btnComprar );
        rvProductosPedido = view.findViewById( R.id.rvProductosPedido );
        txtTotal = view.findViewById( R.id.txtTotal );
        txtNota = view.findViewById( R.id.txtNota );
        txtTope = view.findViewById( R.id.txtTope );
        lnCargando = view.findViewById(R.id.lnCargando);
        ctDatos = view.findViewById( R.id.ctDatos );

        ctDatos.setVisibility( View.VISIBLE );
        lnCargando.setVisibility( View.GONE );

        txtTope.setText( "Valor mínimo de compra: " + numberFormatCurrency.format( Variables.CONFIGURACION.Tope ) );

        btnComprar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //T0oasty.info( getContext(), Variables.tope.toString(), Toasty.LENGTH_LONG ).show();
                    if ((new BigDecimal( numberFormatCurrency.parse( txtTotal.getText().toString() ).toString() )).compareTo( new BigDecimal(Variables.CONFIGURACION.Tope )) >= 0) {
                        FinalizacionCompraFragment finalizacionCompraFragment = new FinalizacionCompraFragment(  );
                        ((FragmentActivity) getActivity()).getSupportFragmentManager()
                                .beginTransaction()
                                .addToBackStack("finalizacionCompraFragment")
                                .replace( R.id.fragment, finalizacionCompraFragment )
                                .commit();
                    } else {
                        Toasty.error( getContext(), "El pedido es menor que el valor del tope mínimo", Toasty.LENGTH_LONG ).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } );

        btnEliminar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Variables.pedido = new Pedidos();
                Variables.detallePedido = new ArrayList<>();

                ((MainActivity) getActivity()).resetMenu();
                Variables.pedido.set_Id( 0 );
                Variables.pedido.setId( "" );
                Variables.pedido.setFecha( simpleDateFormat.format( new Date(  ) ) );
                Variables.pedido.setPersona( Variables.persona );
                Variables.pedido.setObservacion( "" );
                Variables.pedido.setTotal( 0 );
                Variables.pedido.setRecibido( 0 );
                Variables.pedido.setDevuelto( 0 );
                Variables.pedido.setEstado( 0 );
                Variables.pedido.setDetallePedido( Variables.detallePedido );

                mAdapter = new AdaptadorPedido( Variables.detallePedido, getContext(), txtTotal );
                rvProductosPedido.setAdapter( mAdapter );

                txtTotal.setText( numberFormatCurrency.format( BigDecimal.ZERO ) );
            }
        } );

        txtTotal.setText( numberFormatCurrency.format( new BigDecimal( Variables.pedido.getTotal() ) ) );
        txtNota.setText( Variables.pedido.getObservacion() );

        rvProductosPedido.setHasFixedSize( true );

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getContext(), LinearLayoutManager.VERTICAL, false );
        rvProductosPedido.setLayoutManager( layoutManager );

        mAdapter = new AdaptadorPedido( Variables.detallePedido, getContext(), txtTotal );
        rvProductosPedido.setAdapter( mAdapter );

        txtNota.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Variables.pedido.setObservacion( txtNota.getText().toString() );
            }
        } );

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtNota.getWindowToken(), 0);

        return view;
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
