package com.fitnesstime.fitnesstime.activity;

import android.app.Activity;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.R;

public class TimmerActivity extends Activity{

    private Button startButton;
    private Button pauseButton;
    private EditText editTextMinIntervalo1;
    private EditText editTextSegIntervalo1;
    private EditText editTextMinIntervalo2;
    private EditText editTextSegIntervalo2;
    private TextView timerValue;
    private long startTime = 0L;
    private static int TIEMPO_TONO = 1500;
    private static int INTERVALO1 = 0;
    private static int INTERVALO2 = 1;
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

        timerValue = (TextView) findViewById(R.id.timerValue);
        startButton = (Button) findViewById(R.id.startButton);
        initIntervalos();
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

    private void initIntervalos()
    {
        editTextMinIntervalo1 = (EditText)findViewById(R.id.minIntervaloUno);
        editTextSegIntervalo1 = (EditText)findViewById(R.id.segIntervaloUno);
        editTextMinIntervalo2 = (EditText)findViewById(R.id.minIntervaloDos);
        editTextSegIntervalo2 = (EditText)findViewById(R.id.segIntervaloDos);
    }

    //No deja ingresar nada por teclado en los inputs de los intervalos
    private void bloquearInputDeIntervalos()
    {
        editTextMinIntervalo1.setEnabled(false);
        editTextMinIntervalo2.setEnabled(false);
        editTextSegIntervalo1.setEnabled(false);
        editTextSegIntervalo2.setEnabled(false);
    }

    // Verifica que los intervalos que se estan usando y los setea
    private void verificarIntervalosActivos()
    {
        if(editTextMinIntervalo2.getText().toString() != "" || editTextSegIntervalo2.getText().toString() != "")
        {
            intervalosActivos[INTERVALO2] = 1;
            intervaloActual = 2;
        }
        if(editTextMinIntervalo1.getText().toString() != "" || editTextSegIntervalo1.getText().toString() != "")
        {
            intervalosActivos[INTERVALO1] = 1;
            intervaloActual = 1;
        }
        bloquearInputDeIntervalos();
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            secs = (int) (updatedTime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
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

            if(hayDosIntervalosActivos()) {
                if(intervaloActual == 1) {
                    if (mins == (Integer.parseInt(mIntervalo1.getText().toString()) + minIntervalo1 + minIntervalo2) &&
                        secs == (Integer.parseInt(sIntervalo1.getText().toString()) + segIntervalo1 + segIntervalo2)) {
                        try {
                            generarTonoYCalcularIntervalos(minIntervalo1+minIntervalo2,segIntervalo1+segIntervalo2,Integer.parseInt(mIntervalo1.getText().toString()),Integer.parseInt(sIntervalo1.getText().toString()),2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else
                {
                    if (mins == (Integer.parseInt(mIntervalo2.getText().toString()) + minIntervalo2 + minIntervalo1) &&
                            secs == (Integer.parseInt(sIntervalo2.getText().toString()) + segIntervalo2 + segIntervalo1)) {
                        try {
                            generarTonoYCalcularIntervalos(minIntervalo2+minIntervalo1,segIntervalo2+segIntervalo1,Integer.parseInt(mIntervalo2.getText().toString()),Integer.parseInt(sIntervalo2.getText().toString()),1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            handlerAlarm.postDelayed(this, 0);
        }

        private boolean hayDosIntervalosActivos()
        {
            return intervalosActivos[INTERVALO1] == 1 && intervalosActivos[INTERVALO2] == 1;
        }

        private void generarTono()
        {
            ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME);
            tone.startTone(ToneGenerator.TONE_CDMA_PIP, TIEMPO_TONO);
        }

        private void generarTonoYCalcularIntervalos(int mIntervalo, int sIntervalo, int mIntervaloActual, int sIntervaloActual, int intervaloAct)
        {
            generarTono();
            mIntervalo = mIntervalo + mIntervaloActual;
            sIntervalo = sIntervalo + sIntervaloActual;
            if(sIntervalo > 60)
            {
                int dif = sIntervalo - 60;
                mIntervalo += 1;
                sIntervalo = dif;
            }
            intervaloActual = intervaloAct;
        }
    };





}

