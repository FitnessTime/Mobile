package com.fitnesstime.fitnesstime.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Flujos.FlujoPrincipal;
import com.fitnesstime.fitnesstime.R;

public class ActivityTimmer extends ActivityFlujo{

    private Button startButton;
    private Button pauseButton;
    private TextView timerValue;
    private long startTime = 0L;
    private static int TIEMPO_TONO = 1500;
    private Handler customHandler = new Handler();
    private Handler handlerAlarm = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private int secs = 0;
    private int mins = 0;
    private int realDelay = 0;
    private int[] intervalosActivos = {0,0};
    private int intervaloActual = 0;
    private int minIntervalo1 = 0;
    private int segIntervalo1 = 0;
    private int minIntervalo2 = 0;
    private int segIntervalo2 = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timmer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Temporizador");

        timerValue = (TextView) findViewById(R.id.timerValue);
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
                verificarIntervalosActivos();
                if(intervalosActivos[0] == 1 || intervalosActivos[1] == 1)
                    handlerAlarm.postDelayed(alarma, 0);
            }
        });
        pauseButton = (Button) findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
                if(intervalosActivos[0] == 1 || intervalosActivos[1] == 1)
                    handlerAlarm.removeCallbacks(alarma);
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
        startActivity(new Intent(ActivityTimmer.this, ActivityPrincipal.class));
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
                // app icon in action bar clicked; goto parent activity.
                crearDialogoDeConfirmacion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    private Runnable updateTimerThread = new Runnable() {

        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            secs = (int) (updatedTime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }

    };

    private Runnable alarma = new Runnable() {
        @Override
        public void run() {
            EditText mIntervalo1 = (EditText)findViewById(R.id.minIntervaloUno);
            EditText sIntervalo1 = (EditText)findViewById(R.id.segIntervaloUno);
            EditText mIntervalo2 = (EditText)findViewById(R.id.minIntervaloDos);
            EditText sIntervalo2 = (EditText)findViewById(R.id.segIntervaloDos);

            if(intervalosActivos[0] == 1 || intervalosActivos[1] == 1) {
                if(intervaloActual == 1) {
                    if (mins == (Integer.parseInt(mIntervalo1.getText().toString()) + minIntervalo1 + minIntervalo2) &&
                            secs == (Integer.parseInt(sIntervalo1.getText().toString()) + segIntervalo1 + segIntervalo2)) {
                        try {
                            ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME);
                            tone.startTone(ToneGenerator.TONE_CDMA_PIP, TIEMPO_TONO);
                            minIntervalo1 = minIntervalo1 + Integer.parseInt(mIntervalo1.getText().toString());
                            segIntervalo1 = segIntervalo1 + Integer.parseInt(sIntervalo1.getText().toString());
                            intervaloActual = 2;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else
                {
                    if (mins == (Integer.parseInt(mIntervalo2.getText().toString()) + minIntervalo2 + minIntervalo1) &&
                            secs == (Integer.parseInt(sIntervalo2.getText().toString()) + segIntervalo2 + segIntervalo1)) {
                        try {
                            ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME);
                            tone.startTone(ToneGenerator.TONE_CDMA_PIP, TIEMPO_TONO);
                            minIntervalo2 = minIntervalo2 + Integer.parseInt(mIntervalo2.getText().toString());
                            segIntervalo2 = segIntervalo2 + Integer.parseInt(sIntervalo2.getText().toString());
                            intervaloActual = 1;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            handlerAlarm.postDelayed(this, 0);
        }
    };

    private void verificarIntervalosActivos()
    {
        EditText minIntervalo1 = (EditText)findViewById(R.id.minIntervaloUno);
        EditText segIntervalo1 = (EditText)findViewById(R.id.segIntervaloUno);
        EditText minIntervalo2 = (EditText)findViewById(R.id.minIntervaloDos);
        EditText segIntervalo2 = (EditText)findViewById(R.id.segIntervaloDos);
        if(minIntervalo2.getText().toString() != "" || segIntervalo2.getText().toString() != "")
        {
            intervalosActivos[0] = 1;
            intervaloActual = 2;
        }
        if(minIntervalo1.getText().toString() != "" || segIntervalo1.getText().toString() != "")
        {
            intervalosActivos[1] = 1;
            intervaloActual = 1;
        }
        minIntervalo1.setEnabled(false);
        minIntervalo2.setEnabled(false);
        segIntervalo1.setEnabled(false);
        segIntervalo2.setEnabled(false);
    }

}

