package com.softwo.supermercapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.softwo.supermercapp.Constantes.FireBase;
import com.softwo.supermercapp.Entidades.Ciudades;
import com.softwo.supermercapp.Entidades.Configuracion;
import com.softwo.supermercapp.Entidades.UnidadMedida;
import com.softwo.supermercapp.Globales.Variables;
import com.softwo.supermercapp.Sqlite.Helper.DatabaseHelper;

import java.util.ArrayList;

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

        getCiudades();
        getUnidadMedida();
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

                mProgreso += 50;
                txtEtiqueta.setText( "Cargando listado de unidades de medidas" );
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

                mProgreso += 50;
                txtEtiqueta.setText( "Cargando listado de ciudades" );
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
