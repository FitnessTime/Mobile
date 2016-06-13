package com.fitnesstime.fitnesstime.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Eventos.EventoTemporizador;
import com.fitnesstime.fitnesstime.Flujos.FlujoPrincipal;
import com.fitnesstime.fitnesstime.ModelosFlujo.Temporizador;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.ServicioTemporizador;
import com.fitnesstime.fitnesstime.Util.HelperNotificacion;
import com.fitnesstime.fitnesstime.Util.HelperToast;

public class ActivityTemporizador extends ActivityFlujo{

    private Button botonComenzar;
    private Button botonDetener;
    private TextView timerValue;
    private SeekBar intervaloUno;
    private SeekBar intervaloDos;
    private int progressIntervaloUno = 1;
    private int progressIntervaloDos = 0;
    private static int TIEMPO_TONO = 1500;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporizador);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Temporizador");

        FitnessTimeApplication.getEventBus().register(this);

        timerValue = (TextView) findViewById(R.id.timerValue);

        iniciarBotones();
        iniciarSeekBar();
        botonComenzar.setVisibility(View.VISIBLE);
        botonDetener.setVisibility(View.INVISIBLE);
        cargarDatos();
        PowerManager pm = (PowerManager)getApplicationContext().getSystemService(getApplicationContext().POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "fitnesstimelock");
        wl.acquire();
    }

    public void onEvent(EventoTemporizador e)
    {
        botonDetener.setVisibility(View.VISIBLE);
        botonComenzar.setVisibility(View.INVISIBLE);
        timerValue = (TextView)findViewById(R.id.timerValue);
        timerValue.setText(e.getTiempo());
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        iniciarFlujoPrincipal();
    }

    @Override
    public void guardarDatos() {
        //Temporizador entidadTemporizador = (Temporizador)flujo.getEntidad();
        //entidadTemporizador.setIntervaloUno(progressIntervaloUno);
        //entidadTemporizador.setIntervaloDos(progressIntervaloDos);
    }

    @Override
    public void cargarDatos() {
        //Temporizador entidadTemporizador = (Temporizador)flujo.getEntidad();
        //progressIntervaloUno = entidadTemporizador.getIntervaloUno();
        //progressIntervaloDos = entidadTemporizador.getIntervaloDos();
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
        FitnessTimeApplication.setPosicionActivityPrincipal(Constantes.FRAGMENT_HERRAMIENTA);
        setFlujo(flujo);
        finish();
        startActivity(new Intent(ActivityTemporizador.this, ActivityPrincipal.class));
    }

    // Crea el dialogo de confirmacion.
    private void crearDialogoDeConfirmacion()
    {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("Desea salir del temporizador?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        iniciarFlujoPrincipal();
                    }})
                .setNegativeButton("Cancelar", null).show();

        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#F57C00"));
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#F57C00"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                guardarDatos();
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
            guardarDatos();
            crearDialogoDeConfirmacion();
        }
    }

    private void crearDialogoDeAyuda() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("Temporizador que permite programar hasta dos intervalos en minutos, emitiendo " +
                        " diferentes sonidos al pasar el temporizador por los intervalos definidos.")
                .setPositiveButton("Aceptar",null).show();

        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#F57C00"));
    }

    private void iniciarSeekBar()
    {
        intervaloUno = (SeekBar) findViewById(R.id.intervalo_uno);
        intervaloDos = (SeekBar) findViewById(R.id.intervalo_dos);
        intervaloUno.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressIntervaloUno = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                if(progressIntervaloUno > 0)
                    intervaloDos.setEnabled(true);
                else
                    intervaloDos.setEnabled(false);
                HelperToast.generarToast(ActivityTemporizador.this, "Intervalo uno: " + progressIntervaloUno + "min");
            }
        });

        intervaloDos.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressIntervaloDos = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                HelperToast.generarToast(ActivityTemporizador.this, "Intervalo dos: " + progressIntervaloDos + "min");
            }
        });
        intervaloDos.setEnabled(false);
    }

    private void iniciarBotones()
    {
        botonComenzar = (Button) findViewById(R.id.startButton);
        botonComenzar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intentService = new Intent(getBaseContext(), ServicioTemporizador.class);
                intentService.putExtra("intervalo1", progressIntervaloUno);
                intentService.putExtra("intervalo2", progressIntervaloDos);
                startService(intentService);
                botonComenzar.setVisibility(View.INVISIBLE);
                botonDetener.setVisibility(View.VISIBLE);
                intervaloUno.setEnabled(false);
                intervaloDos.setEnabled(false);
            }
        });
        botonDetener = (Button) findViewById(R.id.pauseButton);
        botonDetener.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                botonComenzar.setVisibility(View.VISIBLE);
                botonDetener.setVisibility(View.INVISIBLE);
                intervaloUno.setEnabled(true);
                intervaloDos.setEnabled(true);
                timerValue.setText("00:00:00");
                stopService(new Intent(getBaseContext(), ServicioTemporizador.class));
            }
        });
    }
}

