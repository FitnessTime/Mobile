package com.fitnesstime.fitnesstime.Activities;

import android.content.Intent;
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
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Eventos.EventoLoggin;
import com.fitnesstime.fitnesstime.Eventos.EventoTemporizador;
import com.fitnesstime.fitnesstime.Flujos.FlujoLoggin;
import com.fitnesstime.fitnesstime.Flujos.FlujoPrincipal;
import com.fitnesstime.fitnesstime.Flujos.FlujoRegistro;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.Network;
import com.fitnesstime.fitnesstime.Servicios.ServicioPodometro;
import com.fitnesstime.fitnesstime.Servicios.ServicioSecurityToken;
import com.fitnesstime.fitnesstime.Tasks.LogginTask;
import com.fitnesstime.fitnesstime.Util.HelperToast;

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

        FitnessTimeApplication.getEventBus().register(this);
        boolean estaAutenticado = new ServicioSecurityToken().estaAutenticado();

        if(estaAutenticado)
        {
            finish();
            FitnessTimeApplication.setSession(new ServicioSecurityToken().getAll().get(0));
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
        FlujoPrincipal flujo = new FlujoPrincipal();
        FitnessTimeApplication.setPosicionActivityPrincipal(Constantes.FRAGMENT_RUTINA);
        setFlujo(flujo);
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
                new LogginTask(ActivityLoggin.this).execute(params);
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onEvent(EventoLoggin evento)
    {
        String mensajeToast = evento.getError();
        if(mensajeToast.isEmpty())
        {
            mensajeToast = evento.getMensaje();
            new ServicioSecurityToken().guardar(evento.getSecurityToken());
            FitnessTimeApplication.setSession(evento.getSecurityToken());
            iniciarFlujoApplicacion();
        }
        HelperToast.generarToast(this, mensajeToast);
        activarCampos();
        desactivarSpinner();
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
}
