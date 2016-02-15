package com.fitnesstime.fitnesstime.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Flujos.FlujoLoggin;
import com.fitnesstime.fitnesstime.Flujos.FlujoPrincipal;
import com.fitnesstime.fitnesstime.Flujos.FlujoRegistro;
import com.fitnesstime.fitnesstime.Modelo.SecurityToken;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.Network;

import java.util.Iterator;

public class ActivityLoggin extends ActivityFlujo {

    private EditText email;
    private EditText password;
    private Button iniciarSesion;
    private ProgressBar spinner;
    private TextView registro;
    private TextView iniciandoSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);

        Iterator<SecurityToken> secToken = SecurityToken.findAll(SecurityToken.class);


        if(secToken.hasNext())
        {
            finish();
            iniciarFlujoApplicacion();
        }
        else {
            setFlujo(new FlujoLoggin());
            iniciarEditText();
            desactivarSpinner();
            iniciarAccionEnBotones();
        }
    }

    // Inicia todos los edit text del activity.
    private void iniciarEditText()
    {
        email = (EditText) findViewById(R.id.edit_text_email);
        password = (EditText) findViewById(R.id.edit_text_password);
        iniciarSesion = (Button) findViewById(R.id.boton_loggin);
        registro = (TextView) findViewById(R.id.link_registro);
        iniciandoSesion = (TextView) findViewById(R.id.texto_iniciando_sesion);
        iniciandoSesion.setVisibility(View.INVISIBLE);
    }

    private void desactivarSpinner()
    {
        spinner = (ProgressBar) findViewById(R.id.progressBarLoggin);
        spinner.setVisibility(View.INVISIBLE);
    }

    private void activarSpinner()
    {
        spinner = (ProgressBar) findViewById(R.id.progressBarLoggin);
        spinner.setVisibility(View.VISIBLE);
    }

    private void iniciarFlujoApplicacion()
    {
        setFlujo(new FlujoPrincipal());
        finish();
        startActivity(new Intent(ActivityLoggin.this, ActivityPrincipal.class));
    }

    // Inicia los botones del activity.
    private void iniciarAccionEnBotones()
    {
        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activarSpinner();
                String[] params = {email.getText().toString(), password.getText().toString()};
                desactivarCampos();
                new LogginTask().execute(params);
            }
        });
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFlujo(new FlujoRegistro());
                finish();
                startActivity(new Intent(ActivityLoggin.this, ActivityRegistroDatosPersonales.class));
            }
        });
    }

    @Override
    public void guardarDatos() {

    }

    @Override
    public void cargarDatos() {

    }

    @Override
    public boolean esElPrimero() {
        return true;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Desactiva edit text, botones, etc del activity.
    protected void desactivarCampos()
    {
        email.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        iniciarSesion.setVisibility(View.INVISIBLE);
        registro.setVisibility(View.INVISIBLE);
        iniciandoSesion.setVisibility(View.VISIBLE);
    }

    // Activa edit text, botones, etc del activity.
    protected void activarCampos()
    {
        email.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        iniciarSesion.setVisibility(View.VISIBLE);
        registro.setVisibility(View.VISIBLE);
        iniciandoSesion.setVisibility(View.INVISIBLE);
    }

    private class LogginTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String mensaje = "";

            if(Network.isOnline(ActivityLoggin.this)) {
                SecurityToken securityToken = FitnessTimeApplication.getLogginServicio().autenticar(strings[0], strings[1]);
                if(securityToken == null)
                    mensaje = "Usuario o contraseña invalidos.";
                else
                {
                    mensaje = "Usuario " + securityToken.getEmailUsuario() + " loggeado con exito.";
                    securityToken.save();
                    iniciarFlujoApplicacion();
                }
            }
            else
            {
                mensaje = "No tiene conexión a internet.";
            }
            return mensaje;
        }
        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);

            Toast toast = Toast.makeText(ActivityLoggin.this, string, Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.setBackgroundResource(R.color.boton_loggin);
            toast.show();
            activarCampos();
            desactivarSpinner();
        }
    }
}
