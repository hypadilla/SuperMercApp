package com.softwo.supermercapp.FragmentsProveedores;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.softwo.supermercapp.Constantes.FireBase;
import com.softwo.supermercapp.Entidades.Usuarios;
import com.softwo.supermercapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    Button btnIngresar;
    TextInputLayout txtUsername;
    TextInputLayout txtPassword;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate( R.layout.fragment_login, container, false );

        btnIngresar = view.findViewById( R.id.btnIngresar );
        txtUsername = (TextInputLayout) view.findViewById( R.id.txtUsername );
        txtPassword = (TextInputLayout) view.findViewById( R.id.txtPassword );

        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        btnIngresar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (txtUsername.getEditText().getText().toString().equals( "" )) {
                    txtUsername.setError( "Este campo es requerido" );
                }

                if (txtPassword.getEditText().getText().toString().equals( "" )) {
                    txtPassword.setError( "Este campo es requerido" );
                }

                if (txtUsername.isErrorEnabled() || txtPassword.isErrorEnabled())
                    return;

                Query query = FirebaseDatabase.getInstance().getReference()
                        .child( FireBase.BASEDATOS + "/" + FireBase.TABLAUSUARIO );

                query.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean res = false;
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            Usuarios usuarios = userSnapshot.getValue( Usuarios.class );
                            if (txtUsername.getEditText().getText().toString().toUpperCase().equals( usuarios.Usuario.toUpperCase() )
                                    && txtPassword.getEditText().getText().toString().equals( usuarios.Password )) {
                                res = true;
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction;
                                ProveedoresFragment proveedoresFragment = new ProveedoresFragment();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace( R.id.fragment, proveedoresFragment ).addToBackStack( "ProveedoresFragment" );
                                fragmentTransaction.commit();
                            }
                        }

                        if (!res)
                            Snackbar.make( view, "Usuario y/o contraseña incorrecto/s", Snackbar.LENGTH_LONG ).show();
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                } );
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
}
