package com.softwo.supermercapp.FragmentsProveedores;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.softwo.supermercapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProveedoresFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProveedoresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProveedoresFragment extends Fragment {
    Button btnActualizarProductos;
    Button btnRegistrarProductos;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProveedoresFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProveedoresFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProveedoresFragment newInstance(String param1, String param2) {
        ProveedoresFragment fragment = new ProveedoresFragment();
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
        View view = inflater.inflate( R.layout.fragment_proveedores, container, false );
        btnRegistrarProductos = view.findViewById( R.id.btnRegistrarProductos );
        btnActualizarProductos = view.findViewById( R.id.btnActualizarProductos );
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        btnActualizarProductos.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction;
                ActualizarProductosFragment actualizarProductosFragment = new ActualizarProductosFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace( R.id.fragment, actualizarProductosFragment ).addToBackStack( "ActualizarProductosFragment" );
                fragmentTransaction.commit();
            }
        } );

        btnRegistrarProductos.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction;
                RegistroProductosFragment registroProductosFragment = RegistroProductosFragment.newInstance("");
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace( R.id.fragment, registroProductosFragment ).addToBackStack( "RegistroProductosFragment" );
                fragmentTransaction.commit();
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
