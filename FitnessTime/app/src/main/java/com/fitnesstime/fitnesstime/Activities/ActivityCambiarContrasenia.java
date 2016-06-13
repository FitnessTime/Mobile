package com.fitnesstime.fitnesstime.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Flujos.FlujoPrincipal;
import com.fitnesstime.fitnesstime.Flujos.FlujoRegistro;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Modelo.SecurityToken;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.Network;
import com.fitnesstime.fitnesstime.Util.HelperToast;

import java.util.Iterator;

public class ActivityCambiarContrasenia extends ActivityFlujo {

    private ProgressBar spinner;
    private EditText nuevaContrasenia;
    private EditText confirmarContrasenia;
    private Button aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasenia);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cambiar contraseña");

        iniciarEditText();
        desactivarSpinner();
        iniciarAccionEnBotones();
    }

    @Override
    public void guardarDatos() {

    }

    @Override
    public void cargarDatos() {

    }

    @Override
    public boolean tieneSiguiente() {
        return false;
    }

    @Override
    public boolean tieneAnterior() {
        return false;
    }

    @Override
    public boolean esElPrimero()
    {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                crearDialogoDeConfirmacion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        if(esElPrimero())
        {
            crearDialogoDeConfirmacion();
        }
    }

    // Crea el dialogo de confirmacion.
    private void crearDialogoDeConfirmacion()
    {
        new AlertDialog.Builder(this)
                .setMessage("¿Desea cancelar la operación?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        iniciarFlujoPrincipal();
                    }})
                .setNegativeButton("Cancelar", null).show();
    }

    private void iniciarFlujoPrincipal()
    {
        FlujoPrincipal flujo = new FlujoPrincipal();
        FitnessTimeApplication.setPosicionActivityPrincipal(Constantes.FRAGMENT_RUTINA);
        setFlujo(flujo);
        finish();
        startActivity(new Intent(ActivityCambiarContrasenia.this, ActivityPrincipal.class));
    }

    // Inicia todos los edit text del activity.
    private void iniciarEditText()
    {
        nuevaContrasenia = (EditText) findViewById(R.id.nueva_contrasenia);
        confirmarContrasenia = (EditText) findViewById(R.id.confirmacion_contrasenia);
        aceptar = (Button) findViewById(R.id.boton_aceptar_cambio_contrasenia);
    }

    // Inicia los botones del activity.
    private void iniciarAccionEnBotones()
    {
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (confirmarContrasenia.getText().toString().equals(nuevaContrasenia.getText().toString())) {
                    activarSpinner();
                    //Iterator<SecurityToken> secToken = SecurityToken.findAll(SecurityToken.class);
                    //SecurityToken st = secToken.next();
                    //String[] params = {st.getEmailUsuario(), st.getAuthToken(), nuevaContrasenia.getText().toString()};
                    desactivarCampos();
                    //new CambiarContraseniaTask().execute(params);
                } else {
                    HelperToast.generarToast(getBaseContext(), "Las contraseñas no coinciden");
                }
            }
        });
    }

    private void desactivarSpinner()
    {
        spinner = (ProgressBar) findViewById(R.id.progress_cambiar_contrasenia);
        spinner.setVisibility(View.INVISIBLE);
    }

    private void activarSpinner()
    {
        spinner = (ProgressBar) findViewById(R.id.progress_cambiar_contrasenia);
        spinner.setVisibility(View.VISIBLE);
    }

    // Desactiva edit text, botones, etc del activity.
    protected void desactivarCampos()
    {
        nuevaContrasenia.setVisibility(View.INVISIBLE);
        confirmarContrasenia.setVisibility(View.INVISIBLE);
        aceptar.setVisibility(View.INVISIBLE);
    }

    // Activa edit text, botones, etc del activity.
    protected void activarCampos()
    {
        nuevaContrasenia.setVisibility(View.VISIBLE);
        confirmarContrasenia.setVisibility(View.VISIBLE);
        aceptar.setVisibility(View.VISIBLE);
    }

    private class CambiarContraseniaTask extends AsyncTask<String,Void,ResponseHelper> {

        @Override
        protected ResponseHelper doInBackground(String... strings) {
            ResponseHelper rs = new ResponseHelper(404, "");

            if(Network.isOnline(ActivityCambiarContrasenia.this)) {
                rs = FitnessTimeApplication.getServicioUsuario().cambiarContrasenia(strings[0], strings[1], strings[2]);
            }
            else
            {
                rs.setMensaje("No tiene conexión a internet.");
            }
            return rs;
        }
        @Override
        protected void onPostExecute(ResponseHelper rs) {
            super.onPostExecute(rs);

            HelperToast.generarToast(getBaseContext(), rs.getMensaje());
            activarCampos();
            desactivarSpinner();
        }
    }
}
