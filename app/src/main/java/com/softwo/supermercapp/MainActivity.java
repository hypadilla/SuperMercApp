package com.softwo.supermercapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.softwo.supermercapp.FragmentsProveedores.ActualizarProductosFragment;
import com.softwo.supermercapp.FragmentsProveedores.LoginFragment;
import com.softwo.supermercapp.FragmentsProveedores.ProveedoresFragment;
import com.softwo.supermercapp.FragmentsProveedores.RegistroProductosFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainFragment.OnFragmentInteractionListener,
        ProductosFragment.OnFragmentInteractionListener, PedidoFragment.OnFragmentInteractionListener,
        FinalizacionCompraFragment.OnFragmentInteractionListener, TodosProductosFragment.OnFragmentInteractionListener,
        PedidosFragment.OnFragmentInteractionListener, PromocionesFragment.OnFragmentInteractionListener,
        DatosPersonalesFragment.OnFragmentInteractionListener, FavoritosFragment.OnFragmentInteractionListener,
        ContactoFragment.OnFragmentInteractionListener, RegistroProductosFragment.OnFragmentInteractionListener,
        AyudaFragment.OnFragmentInteractionListener, ComentarioFragment.OnFragmentInteractionListener,
        LoginFragment.OnFragmentInteractionListener, ActualizarProductosFragment.OnFragmentInteractionListener,
        ProveedoresFragment.OnFragmentInteractionListener {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

       // GoogleApiAvailability.makeGooglePlayServicesAvailable(this);

        setContentView( R.layout.activity_main );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

       /* FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        } );*/

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );

        //First start (Inbox Fragment)
        setFragment( 0 );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (count == 0) {
                super.onBackPressed();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cartshop) {
            setFragment( 3 );
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            setFragment( 1 );
        } else if (id == R.id.nav_productos) {
            setFragment( 2 );
        } else if (id == R.id.nav_carrito_compra) {
            setFragment( 3 );
        } else if (id == R.id.nav_descuentos) {
            setFragment( 4 );
        } else if (id == R.id.nav_pedidos) {
            setFragment( 5 );
        } else if (id == R.id.nav_favoritos) {
            setFragment( 6 );
        }/* else if (id == R.id.nav_datos) {
            setFragment( 7 );
        } else if (id == R.id.nav_contacto) {
            setFragment( 8 );
        } else if (id == R.id.nav_comentario) {
            setFragment( 9 );
        } else if (id == R.id.nav_ayuda) {
            setFragment( 10 );
        }*/
         else if (id == R.id.nav_proveedores){
             setFragment( 11 );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void setFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction;
        Fragment currentFragment = fragmentManager.findFragmentById( R.id.fragment );

        ProductosFragment productosFragment;
        MainFragment mainFragment;

        switch (position) {
            case 0:
                mainFragment = new MainFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add( R.id.fragment, mainFragment );
                fragmentTransaction.commit();
                break;
            case 1:
                mainFragment = new MainFragment();
                if (currentFragment.getClass().equals( mainFragment.getClass() )) return;
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace( R.id.fragment, mainFragment ).addToBackStack( "MainFragment" );
                fragmentTransaction.commit();
                break;
            case 2:
                TodosProductosFragment todosProductosFragment = new TodosProductosFragment(  );
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace( R.id.fragment, todosProductosFragment ).addToBackStack( "TodosProductosFragment" );
                fragmentTransaction.commit();
                break;
            case 3:
                PedidoFragment pedidoFragment = new PedidoFragment();
                if (currentFragment.getClass().equals( pedidoFragment.getClass() )) return;
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace( R.id.fragment, pedidoFragment ).addToBackStack( "PedidoFragment" );
                fragmentTransaction.commit();
                break;
            case 4:
                PromocionesFragment promocionesFragment =  new PromocionesFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace( R.id.fragment, promocionesFragment ).addToBackStack( "PromocionesFragment" );
                fragmentTransaction.commit();
                break;
            case 5:
                PedidosFragment pedidosFragment = new PedidosFragment();
                if (currentFragment.getClass().equals( pedidosFragment.getClass() )) return;
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace( R.id.fragment, pedidosFragment ).addToBackStack( "PedidosFragment" );
                fragmentTransaction.commit();
                break;
            case 6:
                FavoritosFragment favoritosFragment = new  FavoritosFragment( );
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace( R.id.fragment, favoritosFragment ).addToBackStack( "FavoritosFragment" );
                fragmentTransaction.commit();
                break;
            case 7:
                DatosPersonalesFragment datosPersonalesFragment = new DatosPersonalesFragment();
                if (currentFragment.getClass().equals( datosPersonalesFragment.getClass() )) return;
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace( R.id.fragment, datosPersonalesFragment ).addToBackStack( "DatosPersonalesFragment" );
                fragmentTransaction.commit();
                break;
            case 8:
                ContactoFragment contactoFragment = new ContactoFragment();
                if (currentFragment.getClass().equals( contactoFragment.getClass() )) return;
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace( R.id.fragment, contactoFragment ).addToBackStack( "ContactoFragment" );
                fragmentTransaction.commit();
                break;
            case 9:
                ComentarioFragment comentarioFragment = new ComentarioFragment();
                if (currentFragment.getClass().equals( comentarioFragment.getClass() )) return;
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace( R.id.fragment, comentarioFragment ).addToBackStack( "ComentarioFragment" );
                fragmentTransaction.commit();
                break;
            case 10:
                AyudaFragment ayudaFragment = new AyudaFragment();
                if (currentFragment.getClass().equals( ayudaFragment.getClass() )) return;
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace( R.id.fragment, ayudaFragment ).addToBackStack( "AyudaFragment" );
                fragmentTransaction.commit();
                break;
            case 11:
                LoginFragment loginFragment = new LoginFragment();
                if (currentFragment.getClass().equals( loginFragment.getClass() )) return;
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace( R.id.fragment, loginFragment ).addToBackStack( "LoginFragment" );
                fragmentTransaction.commit();
                break;
        }
    }
}
