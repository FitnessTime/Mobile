package com.fitnesstime.fitnesstime.Activities;

import android.os.Bundle;

import com.fitnesstime.fitnesstime.R;

public class ActivityRegistroFechaRutina extends ActivityFlujo {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_fecha_rutina);
    }

    @Override
    public void guardarDatos() {

    }

    @Override
    public void cargarDatos() {

    }

    @Override
    public boolean tieneSiguiente() {
        return true;
    }

    @Override
    public boolean tieneAnterior() {
        return true;
    }

    @Override
    public boolean esElPrimero()
    {
        return false;
    }
}
