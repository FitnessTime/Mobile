package com.fitnesstime.fitnesstime.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.fitnesstime.fitnesstime.ModelosFlujo.Registro;
import com.fitnesstime.fitnesstime.R;

import java.util.Calendar;

public class ActivityRegistrosFecha extends ActivityFlujo {

    private Button siguiente;

    private DatePicker fecha;

    private int year;
    private int month;
    private int day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros_fecha);
        getSupportActionBar().setTitle("Fecha nacimiento");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        iniciarBotones();
    }

    private void iniciarBotones() {
        siguiente = (Button) findViewById(R.id.boton_siguiente_datos_fecha);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarDatos();
                activitySigiente();
            }
        });
        if(tieneSiguiente())
            siguiente.setVisibility(View.VISIBLE);
        else
            siguiente.setVisibility(View.INVISIBLE);
    }

    // display current date
    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }

    private void iniciarDatePicker()
    {
        fecha = (DatePicker) findViewById(R.id.registro_fecha);
        fecha.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int año, int mes, int dia) {
                year = año;
                month = mes;
                day = dia;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                guardarDatos();
                activityAnterior();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void guardarDatos() {
        Registro entidadRegistro = (Registro)flujo.getEntidad();
        entidadRegistro.setFecha(day + "/" + month + "/" + year);
    }

    @Override
    public void cargarDatos() {
        Registro entidadRegistro = (Registro)flujo.getEntidad();
        if(entidadRegistro.getFecha()=="" || entidadRegistro.getFecha() == null)
        {
            setCurrentDateOnView();
        }
        else{
            String[] splitFecha = entidadRegistro.getFecha().split("/");
            year = Integer.parseInt(splitFecha[2]);
            month = Integer.parseInt(splitFecha[1]);
            day = Integer.parseInt(splitFecha[0]);
        }
        iniciarDatePicker();
    }

    @Override
    public boolean tieneSiguiente() {
        return true;
    }

    @Override
    public boolean tieneAnterior() {
        return true;
    }
}
