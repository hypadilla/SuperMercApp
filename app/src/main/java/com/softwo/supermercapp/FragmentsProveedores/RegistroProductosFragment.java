package com.softwo.supermercapp.FragmentsProveedores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.softwo.supermercapp.Constantes.FireBase;
import com.softwo.supermercapp.Entidades.Productos;
import com.softwo.supermercapp.Globales.Variables;
import com.softwo.supermercapp.R;
import com.softwo.supermercapp.Sqlite.Helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistroProductosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistroProductosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroProductosFragment extends Fragment {
    private CheckBox chkHabilitar;
    private TextInputLayout txtTitulo;
    private Spinner spCategoria;
    private TextInputLayout txtDescuento;
    private TextInputLayout txtVenta;
    private TextInputLayout txtCantidad;
    private Spinner spUnidadMedida;
    private TextInputLayout txtRutaImagen;
    private Button btnVerificar;
    private ImageView imgImagen;
    private Button btnEliminar;
    private Button btnGuardar;

    String Key;
    String categoriaSeleccionada;
    String unidadmedidaSeleccionada;

    private static final String KEY = "ProductKey";

    private String mProductKey;

    private OnFragmentInteractionListener mListener;

    public RegistroProductosFragment() {
        // Required empty public constructor
    }


    public static RegistroProductosFragment newInstance(String param1) {
        RegistroProductosFragment fragment = new RegistroProductosFragment();
        Bundle args = new Bundle();
        args.putString( KEY, param1 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mProductKey = getArguments().getString( KEY );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_registro_productos, container, false );

        chkHabilitar = view.findViewById( R.id.chkHabilitar );
        txtTitulo = view.findViewById( R.id.txtTitulo );
        spCategoria = view.findViewById( R.id.spCategoria );
        txtDescuento = view.findViewById( R.id.txtDescuento );
        txtVenta = view.findViewById( R.id.txtVenta );
        txtCantidad = view.findViewById( R.id.txtCantidad );
        spUnidadMedida = view.findViewById( R.id.spUnidadMedida );
        txtRutaImagen = view.findViewById( R.id.txtRutaImagen );
        btnVerificar = view.findViewById( R.id.btnVerificar );
        imgImagen = view.findViewById( R.id.imgImagen );
        btnEliminar = view.findViewById( R.id.btnEliminar );
        btnGuardar = view.findViewById( R.id.btnGuardar );

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        final String[] categorias = new String[Variables.LISTACATEGORIAS.size()];
        final String[] unidadMedida = new String[Variables.LISTAUNIDADMEDIDA.size()];

        for (int i = 0; i < Variables.LISTACATEGORIAS.size(); i++) {
            categorias[i] = Variables.LISTACATEGORIAS.get( i ).Titulo;
        }

        for (int i = 0; i < Variables.LISTAUNIDADMEDIDA.size(); i++) {
            unidadMedida[i] = Variables.LISTAUNIDADMEDIDA.get( i ).Nombre;
        }
        unidadmedidaSeleccionada = unidadMedida[0];
        categoriaSeleccionada = categorias[0];

        if (!mProductKey.equals( "" )) {
            btnGuardar.setText( "Actualizar" );
            btnEliminar.setText( "Eliminar" );

            Query query = FirebaseDatabase.getInstance().getReference( FireBase.BASEDATOS )
                    .child( FireBase.TABLAPRODUCTO )
                    .orderByChild( "Id" ).equalTo( mProductKey );

            query.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        Productos producto = userSnapshot.getValue( Productos.class );
                        txtTitulo.getEditText().setText( producto.Titulo );
                        txtTitulo.setTag( producto.Id );
                        chkHabilitar.setChecked( producto.Estado );
                        int iCategorias = 0;
                        for (String Categoria :
                                categorias) {
                            if (Categoria.equals( producto.Categoria )) {
                                break;
                            }
                            iCategorias++;
                        }
                        spCategoria.setSelection( iCategorias );
                        int iUnidadMedida = 0;
                        for (String Unidad :
                                unidadMedida) {
                            if (Unidad.equals( producto.UnidadMedida )) {
                                break;
                            }
                            iUnidadMedida++;
                        }
                        spUnidadMedida.setSelection( iUnidadMedida );
                        txtDescuento.getEditText().setText( String.valueOf( producto.Descuento ) );
                        txtVenta.getEditText().setText( String.valueOf( producto.Venta ) );

                        String[] parts = producto.getPresentacion().split( " " );
                        String part1 = parts[0];

                        txtCantidad.getEditText().setText( part1 );

                        txtRutaImagen.getEditText().setText( producto.Imagen );
                        Key = userSnapshot.getKey();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            } );
        }

        ArrayAdapter adapterCategoria = new ArrayAdapter( getContext(), android.R.layout.simple_list_item_1, categorias );
        ArrayAdapter adapterUnidadMedida = new ArrayAdapter( getContext(), android.R.layout.simple_list_item_1, unidadMedida );

        spCategoria.setAdapter( adapterCategoria );
        spUnidadMedida.setAdapter( adapterUnidadMedida );

        spUnidadMedida.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unidadmedidaSeleccionada = spUnidadMedida.getItemAtPosition( position ).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        spCategoria.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoriaSeleccionada = spCategoria.getItemAtPosition( position ).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        btnVerificar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtRutaImagen.getEditText().getText().toString().equals( "" )) {
                    txtRutaImagen.setError( "Debes ingresar una ruta" );
                    imgImagen.setImageDrawable( null );
                    imgImagen.setVisibility( View.GONE );
                } else {
                    Glide.with( getContext() )
                            .load( txtRutaImagen.getEditText().getText().toString() )
                            .thumbnail( 0.1f )
                            .centerCrop()
                            .diskCacheStrategy( DiskCacheStrategy.ALL )
                            .into( imgImagen );
                    imgImagen.setVisibility( View.VISIBLE );
                }
            }
        } );

        btnEliminar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mProductKey.equals( "" )) {
                    AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
                    builder.setMessage("¿Estás seguro de eliminar este producto?")
                            .setTitle("Eliminación de productos");
                    builder.setPositiveButton( "SI", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference( FireBase.BASEDATOS );
                            DatabaseReference currentUserBD = mDatabase.child( FireBase.TABLAPRODUCTO + "/" + Key );
                            currentUserBD.removeValue();
                            Limpiar();
                            Toasty.error( getContext(), "El producto ha sido eliminado", Toasty.LENGTH_LONG ).show();
                        }
                    } );
                    builder.setNegativeButton( "NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    } );

                    AlertDialog dialog = builder.create();
                    dialog.show();


                } else {
                    Limpiar();
                }
            }
        } );

        btnGuardar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtTitulo.getEditText().getText().toString().equals( "" )) {
                    txtTitulo.setError( "Este campo no puede estar vacío" );
                    return;
                }

                if (txtCantidad.getEditText().getText().toString().equals( "" )) {
                    txtCantidad.getEditText().setText( "1" );
                }

                if (txtDescuento.getEditText().getText().toString().equals( "" )) {
                    txtDescuento.getEditText().setText( "0" );
                }

                if (txtVenta.getEditText().getText().toString().equals( "" )) {
                    txtVenta.getEditText().setText( "0" );
                    chkHabilitar.setChecked( false );
                }

                btnGuardar.setEnabled( false );

                final Productos productos = new Productos();

                if (!mProductKey.equals( "" )) {
                    productos.Id = txtTitulo.getTag().toString();
                }
                productos.Estado = chkHabilitar.isChecked();
                productos.Titulo = txtTitulo.getEditText().getText().toString();
                productos.Presentacion = txtCantidad.getEditText().getText().toString() + " " + unidadmedidaSeleccionada;
                productos.UnidadMedida = unidadmedidaSeleccionada;
                productos.Categoria = categoriaSeleccionada;
                productos.Imagen = txtRutaImagen.getEditText().getText().toString();

                productos.Venta = Double.parseDouble( txtVenta.getEditText().getText().toString() );
                productos.Descuento = Double.parseDouble( txtDescuento.getEditText().getText().toString() );


                if (!mProductKey.equals( "" )) {
                    AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
                    builder.setMessage("¿Estás seguro de actualizar este producto?")
                            .setTitle("Actualización de productos");
                    builder.setPositiveButton( "SI", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            updateProudcto( FirebaseDatabase.getInstance().getReference( FireBase.BASEDATOS ), productos );
                            Limpiar();
                        }
                    } );
                    builder.setNegativeButton( "NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    } );

                    AlertDialog dialog = builder.create();
                    dialog.show();

                    btnGuardar.setEnabled( true );
                } else {
                    Query query = FirebaseDatabase.getInstance().getReference( FireBase.BASEDATOS )
                            .child( FireBase.TABLAPRODUCTO );

                    query.addListenerForSingleValueEvent( new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ArrayList<Productos> arrayList = new ArrayList<>();
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                Productos producto = userSnapshot.getValue( Productos.class );
                                arrayList.add( producto );
                            }

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference( FireBase.BASEDATOS );

                            final DatabaseReference newRef = ref.child( FireBase.TABLAPRODUCTO ).push();
                            productos.Id = newRef.getKey();
                            newRef.setValue( productos );

                            btnGuardar.setEnabled( true );

                            Limpiar();
                            Toasty.success( getContext(), "El producto ha sido guardado", Toasty.LENGTH_LONG ).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    } );
                }
            }
        } );
    }

    private void Limpiar() {
        chkHabilitar.setChecked( true );
        txtTitulo.getEditText().setText( "" );
        txtCantidad.getEditText().setText( "1" );
        txtDescuento.getEditText().setText( "0" );
        txtRutaImagen.getEditText().setText( "" );
        txtVenta.getEditText().setText( "0" );
        imgImagen.setImageDrawable( null );
        imgImagen.setVisibility( View.GONE );
        mProductKey = "";
        btnEliminar.setText( "Limpiar" );
        btnGuardar.setText( "Guardar" );
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void updateProudcto(DatabaseReference db, Productos producto) {
        Map<String, Object> postValues = producto.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put( "/" + FireBase.TABLAPRODUCTO + "/" + Key, postValues );
        db.updateChildren( childUpdates );

        Toasty.info( getContext(), "El producto ha sido actualizado", Toasty.LENGTH_LONG ).show();
    }
}
