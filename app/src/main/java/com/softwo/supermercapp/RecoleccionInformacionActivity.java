package com.softwo.supermercapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.softwo.supermercapp.Async.AlmacenarPreferences;
import com.softwo.supermercapp.Constantes.FireBase;
import com.softwo.supermercapp.Entidades.DetallePedido;
import com.softwo.supermercapp.Entidades.Pedidos;
import com.softwo.supermercapp.Entidades.Persona;
import com.softwo.supermercapp.Entidades.Productos;
import com.softwo.supermercapp.Globales.Variables;
import com.softwo.supermercapp.Sqlite.Contract.PedidosContract;
import com.softwo.supermercapp.Sqlite.Helper.DatabaseHelper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class RecoleccionInformacionActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;


    String pattern = " dd/MM/yyyy hh:mm:ss aa";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    /***
     * Constantes
     */
    private String RECORDAR = "RECORDAR";
    private String NOMBRE = "NOMBRE";
    private String DIRECCION = "DIRECCION";
    private String TELEFONO = "TELEFONO";
    private String LONGITUD = "LONGITUD";
    private String LATITUD = "LATITUD";
    private String REFERENCIA = "REFERENCIA";

    Button btnContinuar;
    TextInputLayout txtNombre;
    TextInputLayout txtDireccion;
    TextInputLayout txtTelefono;
    TextInputLayout txtReferencia;
    TextView lblMaps;
    CheckBox chkRecordar;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_recoleccion_informacion );

        Variables.databaseHelper = new DatabaseHelper( getApplicationContext() );
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences( this );

        btnContinuar = findViewById( R.id.btnContinuar );
        txtNombre = findViewById( R.id.txtNombre );
        txtDireccion = findViewById( R.id.txtDireccion );
        txtTelefono = findViewById( R.id.txtTelefono );
        txtReferencia = findViewById( R.id.txtReferencia );
        lblMaps = findViewById( R.id.lblMaps );
        chkRecordar = findViewById( R.id.chkRecordar );

        getPreferences();

        lblMaps.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );

        btnContinuar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty( txtNombre.getEditText().getText() )) {
                    txtNombre.setError( "El nombre es requerido!" );
                    return;
                } else {
                    txtNombre.setError( null );
                }

                if (TextUtils.isEmpty( txtDireccion.getEditText().getText() )) {
                    txtDireccion.setError( "La dirección es requerida!" );
                    return;
                } else {
                    txtDireccion.setError( null );
                }

                Variables.persona = new Persona(
                        txtNombre.getEditText().getText().toString()
                        , txtDireccion.getEditText().getText().toString()
                        , txtReferencia.getEditText().getText().toString()
                        , txtTelefono.getEditText().getText().toString(), null, "" );

                AlmacenarPreferences almacenarPreferences = new AlmacenarPreferences( getApplicationContext(), Variables.persona, chkRecordar.isChecked() );

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    almacenarPreferences.executeOnExecutor( AsyncTask.THREAD_POOL_EXECUTOR);
                else
                    almacenarPreferences.execute();


                if (Variables.pedido == null) {
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
                    Variables.pedido.setEstado( false );
                    Variables.pedido.setDetallePedido( Variables.detallePedido );
                }

                Intent intent = new Intent( RecoleccionInformacionActivity.this, MainActivity.class );
                startActivity( intent );
            }
        } );
    }

    private void getPreferences() {
        if (sharedPreferences.getBoolean( RECORDAR, false )) {
            String Nombre = sharedPreferences.getString( NOMBRE, "" );
            String Direccion = sharedPreferences.getString( DIRECCION, "" );
            String Telefono = sharedPreferences.getString( TELEFONO, "" );
            String Referencia = sharedPreferences.getString( REFERENCIA, "" );

            txtNombre.getEditText().setText( Nombre );
            txtDireccion.getEditText().setText( Direccion );
            txtTelefono.getEditText().setText( Telefono );
            txtReferencia.getEditText().setText( Referencia );
            chkRecordar.setChecked( sharedPreferences.getBoolean( RECORDAR, false ) );
        }
    }
}
