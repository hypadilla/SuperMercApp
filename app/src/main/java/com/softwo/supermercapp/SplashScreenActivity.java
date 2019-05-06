package com.softwo.supermercapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.softwo.supermercapp.Constantes.FireBase;
import com.softwo.supermercapp.Entidades.Productos;

import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity {
    TextView txtEtiqueta;
    DatabaseReference categoriaref;
    Query prediccionesPorClaveHija;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate( savedInstanceState );

       //FirebaseMessaging.getInstance().setAutoInitEnabled(true);

        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
        this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        setContentView( R.layout.activity_splash_screen );

        txtEtiqueta = findViewById( R.id.txtEtiqueta );

        //Crashlytics.getInstance().crash();
        Intent intent = new Intent( SplashScreenActivity.this, RecoleccionInformacionActivity.class );
        startActivity( intent );
        finish();

    }
}
