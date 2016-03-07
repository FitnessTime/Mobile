package com.fitnesstime.fitnesstime.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Eventos.EventoTemporizador;
import com.fitnesstime.fitnesstime.Flujos.FlujoPrincipal;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.ServicioTemporizador;

public class ActivityTemporizador extends ActivityFlujo{

    private Button botonComenzar;
    private Button botonDetener;
    private TextView timerValue;
    private static int TIEMPO_TONO = 1500;
    private Handler customHandler = new Handler();
    private Handler handlerAlarm = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporizador);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Temporizador");

        FitnessTimeApplication.getEventBus().register(this);

        timerValue = (TextView) findViewById(R.id.timerValue);
        botonComenzar = (Button) findViewById(R.id.startButton);
        botonComenzar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                startService(new Intent(getBaseContext(), ServicioTemporizador.class));
                botonComenzar.setVisibility(View.INVISIBLE);
                botonDetener.setVisibility(View.VISIBLE);
            }
        });
        botonDetener = (Button) findViewById(R.id.pauseButton);
        botonDetener.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stopService(new Intent(getBaseContext(), ServicioTemporizador.class));
                botonComenzar.setVisibility(View.VISIBLE);
                botonDetener.setVisibility(View.INVISIBLE);
            }
        });

        botonComenzar.setVisibility(View.VISIBLE);
        botonDetener.setVisibility(View.INVISIBLE);
    }

    public void onEvent(EventoTemporizador e)
    {
        asd(e.getTiempo());
    }

    public void asd(String s)
    {
        timerValue = (TextView)findViewById(R.id.timerValue);
        timerValue.setText(s);
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

    private void iniciarFlujoPrincipal()
    {
        FlujoPrincipal flujo = new FlujoPrincipal();
        flujo.setPosicionFragment(Constantes.FRAGMENT_HERRAMIENTA);
        setFlujo(flujo);
        finish();
        startActivity(new Intent(ActivityTemporizador.this, ActivityPrincipal.class));
    }

    // Crea el dialogo de confirmacion.
    private void crearDialogoDeConfirmacion()
    {
        new AlertDialog.Builder(this)
                .setTitle("Herramientas")
                .setMessage("Desea salir del temporizador?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        iniciarFlujoPrincipal();
                    }})
                .setNegativeButton("Cancelar", null).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                crearDialogoDeConfirmacion();
                return true;
            case R.id.action_help:
                crearDialogoDeAyuda();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_temporizador, menu);
        return true;
    }

    @Override
    public boolean esElPrimero()
    {
        return true;
    }

    @Override
    public void onBackPressed()
    {
        if(esElPrimero())
        {
            crearDialogoDeConfirmacion();
        }
    }

    private void crearDialogoDeAyuda() {
        new AlertDialog.Builder(this)
                .setMessage("Temporizador que permite programar hasta dos intervalos, emitiendo " +
                        " diferentes sonidos al pasar el temporizador por los intervalos definidos.")
                .setIcon(R.drawable.ico_help)
                .setPositiveButton("Ok", null).show();
    }
}

