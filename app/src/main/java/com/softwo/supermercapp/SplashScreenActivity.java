package com.softwo.supermercapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.softwo.supermercapp.Adaptadores.AdaptadorProductos;
import com.softwo.supermercapp.Constantes.FireBase;
import com.softwo.supermercapp.Entidades.Categorias;
import com.softwo.supermercapp.Entidades.Ciudades;
import com.softwo.supermercapp.Entidades.Configuracion;
import com.softwo.supermercapp.Entidades.Productos;
import com.softwo.supermercapp.Entidades.UnidadMedida;
import com.softwo.supermercapp.Entidades.Usuarios;
import com.softwo.supermercapp.Globales.Variables;
import com.softwo.supermercapp.Sqlite.Helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashScreenActivity extends AppCompatActivity {
    TextView txtEtiqueta;
    int mProgreso;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
        this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView( R.layout.activity_splash_screen );

        txtEtiqueta = findViewById( R.id.txtEtiqueta );
        mProgreso = 0;

        databaseHelper = new DatabaseHelper( this );


        getConfiguracion();
        getProductos();
        getCategorias();
        getCiudades();
        getUnidadMedida();

        /*DatabaseReference mDatabase =FirebaseDatabase.getInstance().getReference(FireBase.BASEDATOS);
        DatabaseReference currentUserBD = mDatabase.child("producto");
        currentUserBD.removeValue();*/

    }

    private void getUnidadMedida() {
        Query query = FirebaseDatabase.getInstance().getReference( FireBase.BASEDATOS )
                .child( FireBase.TABLAUNIDADMEDIDA );

        query.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Variables.LISTAUNIDADMEDIDA = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Variables.LISTAUNIDADMEDIDA.add( userSnapshot.getValue( UnidadMedida.class ) );
                }

                mProgreso += 20;
                txtEtiqueta.setText( "Cargando listado de unidades de medidas" );
                Iniciar( mProgreso );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        } );
    }

    private void getCategorias() {
        Query query = FirebaseDatabase.getInstance().getReference()
                .child( FireBase.BASEDATOS + "/" + FireBase.TABLACATEGORIA );

        query.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Variables.LISTACATEGORIAS = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Variables.LISTACATEGORIAS.add( userSnapshot.getValue( Categorias.class ) );
                }

                mProgreso += 20;
                txtEtiqueta.setText( "Cargando listado de categorias" );
                Iniciar( mProgreso );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        } );
    }

    private void getCiudades() {
        Query query = FirebaseDatabase.getInstance().getReference( FireBase.BASEDATOS )
                .child( FireBase.TABLACIUDAD );

        query.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Variables.LISTACIUDADES = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Variables.LISTACIUDADES.add( userSnapshot.getValue( Ciudades.class ) );
                }

                mProgreso += 20;
                txtEtiqueta.setText( "Cargando listado de ciudades" );
                Iniciar( mProgreso );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        } );
    }

    private void getConfiguracion() {
        Query query = FirebaseDatabase.getInstance().getReference( FireBase.BASEDATOS )
                .child( FireBase.TABLACONFIGURACION );

        query.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Variables.CONFIGURACION = dataSnapshot.getValue( Configuracion.class );

                mProgreso += 20;
                txtEtiqueta.setText( "Cargando listado de configuraciones" );
                Iniciar( mProgreso );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        } );
    }

    private void getProductos() {
        Query query = FirebaseDatabase.getInstance().getReference( FireBase.BASEDATOS )
                .child( FireBase.TABLAPRODUCTO );

        query.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*Variables.LISTAPRODUCTOS = new ArrayList<>();
                Variables.LISTAPRODUCTOSPROMOCIONES = new ArrayList<>();
                Variables.LISTAPRODUCTOSFAVORITOS = new ArrayList<>();*/

//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    Productos producto = userSnapshot.getValue( Productos.class );
//                    producto.setId( userSnapshot.getKey() );
//
//                    Map<String, Object> postValues = producto.toMap();
//                    Map<String, Object> childUpdates = new HashMap<>();
//                    childUpdates.put( "/" + FireBase.TABLAPRODUCTO + "/" + userSnapshot.getKey(), postValues );
//                    FirebaseDatabase.getInstance().getReference( FireBase.BASEDATOS ).updateChildren( childUpdates );

                    /*if (producto.Estado){
                        Variables.LISTAPRODUCTOS.add( producto );
                        if (producto.Descuento > 0)
                            Variables.LISTAPRODUCTOSPROMOCIONES.add( producto );
                        if (databaseHelper.ExisteFavoritos( databaseHelper.getWritableDatabase(), producto.getId() ))
                            Variables.LISTAPRODUCTOSFAVORITOS.add( producto );
                    }*/
               // }
                mProgreso += 20;
                txtEtiqueta.setText( "Cargando listado de productos" );
                Iniciar( mProgreso );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        } );
    }

    void Iniciar(int progreso) {
        if (progreso == 100) {
            Intent intent = new Intent( SplashScreenActivity.this, RecoleccionInformacionActivity.class );
            startActivity( intent );
            finish();
        }

    }
}
