package com.softwo.supermercapp.Async;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.softwo.supermercapp.Entidades.Persona;

public class AlmacenarPreferences extends AsyncTask<Void, Integer, Boolean> {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    Persona persona;
    boolean isAlmacenado;

    private String RECORDAR = "RECORDAR";
    private String NOMBRE = "NOMBRE";
    private String DIRECCION = "DIRECCION";
    private String TELEFONO = "TELEFONO";
    private String LONGITUD = "LONGITUD";
    private String LATITUD = "LATITUD";
    private String REFERENCIA = "REFERENCIA";

    public AlmacenarPreferences(Context context, Persona persona, boolean isAlmacenado) {
        this.context = context;
        this.persona = persona;
        this.isAlmacenado = isAlmacenado;
    }

    @Override
    protected void onPreExecute() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );
        editor = sharedPreferences.edit();
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param voids The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Boolean doInBackground(Void... voids) {
        if (isAlmacenado) {
            editor.putString( NOMBRE, persona.getNombre() );
            editor.putString( DIRECCION, persona.getDireccion() );
            editor.putString( REFERENCIA, persona.getReferencia() );
            editor.putString( TELEFONO, persona.getTelefono() );
            editor.putBoolean( RECORDAR, true );
            editor.putString( LATITUD, "0" );
            editor.putString( LONGITUD, "0" );
        } else {
            editor.putString( NOMBRE, "" );
            editor.putString( DIRECCION, "" );
            editor.putString( REFERENCIA, "" );
            editor.putString( TELEFONO, "" );
            editor.putBoolean( RECORDAR, false );
            editor.putString( LATITUD, "0" );
            editor.putString( LONGITUD, "0" );
        }
        editor.commit();
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }


    @Override
    protected void onPostExecute(Boolean result) {
    }

    @Override
    protected void onCancelled() {
    }
}
