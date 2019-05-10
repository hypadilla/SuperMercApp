package com.softwo.supermercapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.softwo.supermercapp.Constantes.FireBase;
import com.softwo.supermercapp.Entidades.Pedidos;
import com.softwo.supermercapp.Globales.Variables;
import com.softwo.supermercapp.Sqlite.Helper.DatabaseHelper;

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
 * {@link FinalizacionCompraFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FinalizacionCompraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinalizacionCompraFragment extends Fragment {
    String pattern = " dd/MM/yyyy hh:mm:ss aa";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    Button btnFinalizar;
    TextView txtMontoPagar;
    EditText txtValorEntregar;
    Button btnPagarCompleto;

    private DatabaseHelper databaseHelper;

    private NumberFormat numberFormat = NumberFormat.getInstance( Locale.getDefault() );

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FinalizacionCompraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinalizacionCompraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinalizacionCompraFragment newInstance(String param1, String param2) {
        FinalizacionCompraFragment fragment = new FinalizacionCompraFragment();
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
        final View view = inflater.inflate( R.layout.fragment_finalizacion_compra, container, false );
        btnFinalizar = view.findViewById( R.id.btnFinalizar );
        txtMontoPagar = view.findViewById( R.id.txtMontoPagar );
        txtValorEntregar = view.findViewById( R.id.txtValorEntregar );
        btnPagarCompleto = view.findViewById( R.id.btnPagarCompleto );

        txtMontoPagar.setText( numberFormat.format( Variables.pedido.getTotal() ) );

        btnPagarCompleto.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtValorEntregar.setText( String.valueOf(Variables.pedido.getTotal()) );
            }
        } );

        databaseHelper = new DatabaseHelper( getContext() );

        btnFinalizar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigDecimal Monto = BigDecimal.ZERO;
                BigDecimal Recibido = BigDecimal.ZERO;
                BigDecimal Devuelta = BigDecimal.ZERO;
                try {
                    Monto = new BigDecimal( String.valueOf( ((numberFormat.parse( txtMontoPagar.getText().toString() ))) ) );
                    Recibido = new BigDecimal( txtValorEntregar.getText().toString() );
                    Devuelta = BigDecimal.ZERO;
                    if (Recibido.compareTo( Monto ) >= 0) {
                        Devuelta = Recibido.subtract( Monto );
                    } else {
                        Toasty.error( getContext(), "El valor a entregar, \"Págare con\", no puede ser menor a \"Monto a pagar\"", Toasty.LENGTH_LONG ).show();
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Variables.pedido.setRecibido( Recibido.doubleValue() );
                Variables.pedido.setDevuelto( Devuelta.doubleValue() );

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference( com.softwo.supermercapp.Constantes.FireBase.BASEDATOS );

                final DatabaseReference newRef = ref.child( FireBase.TABLAPEDIDO ).push();
                Variables.pedido.setId( newRef.getKey() );


                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    return;
                                }

                                String token = task.getResult().getToken();
                                Variables.persona.setToken( token );
                                Variables.pedido.persona.setToken( token );

                                newRef.setValue( Variables.pedido );

                                databaseHelper.InsertarPedido( Variables.pedido );
                                databaseHelper.InsertarDetallePedido( Variables.pedido.getDetallePedido() );

                                Toasty.success( getContext(), "Tu pedido ha sido recibido, en breve te contactamos.", Toasty.LENGTH_LONG ).show();

                                Variables.pedido = new Pedidos();
                                Variables.detallePedido = new ArrayList<>();

                                Variables.pedido.set_Id( 0 );
                                Variables.pedido.setId( "" );
                                Variables.pedido.setFecha( simpleDateFormat.format(  new Date() ));
                                Variables.pedido.setPersona( Variables.persona );
                                Variables.pedido.setObservacion( "" );
                                Variables.pedido.setTotal( 0 );
                                Variables.pedido.setRecibido( 0 );
                                Variables.pedido.setDevuelto( 0 );
                                Variables.pedido.setEstado( 0 );
                                Variables.pedido.setDetallePedido( Variables.detallePedido );

                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                                    fm.popBackStack();
                                }

                                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(txtValorEntregar.getWindowToken(), 0);

                                MainFragment mainFragment = new MainFragment();
                                ((FragmentActivity) getActivity()).getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace( R.id.fragment, mainFragment )
                                        .commit();
                            }
                        });




            }
        } );
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
