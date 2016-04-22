package com.fitnesstime.fitnesstime.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.DAO.DomainEntityService;
import com.fitnesstime.fitnesstime.Flujos.Flujo;

import java.io.Serializable;

/**
 * Created by pablo on 26/11/15.
 */
public abstract class ActivityFlujo<F extends Flujo> extends AppCompatActivity implements Serializable{
    protected F flujo;
    private FitnessTimeApplication app;
    private boolean guardaDatos = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.icono);
        app = (FitnessTimeApplication)getApplication();
        flujo = (F) app.getFlujo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(guardaDatos)
            guardarDatos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatos();
    }

    @Override
    public void onBackPressed()
    {
        activityAnterior();
    }

    public abstract void guardarDatos();
    public abstract void cargarDatos();

    public abstract boolean tieneSiguiente();
    public abstract boolean tieneAnterior();

    final protected void activitySigiente() {
        cambiarActivity(flujo.siguiente());
        finish();
    }

    final protected void activityAnterior() {
        if(esElPrimero()) {
            finish();
        }
        else {
            cambiarActivity(flujo.anterior());
            finish();
        }
    }

    private void cambiarActivity(Class proximoActivityClass){
        Intent intent = new Intent(this,proximoActivityClass);
        flujo.setPosicionFragment(0);
        setFlujo(flujo);
        startActivity(intent);
    }

    public void setFlujo(F flujo){
        app.setFlujo(flujo);
        this.flujo = flujo;
    }

    public boolean esElPrimero(){return false;}

    public boolean isGuardaDatos() {
        return guardaDatos;
    }

    public void setGuardaDatos(boolean guardaDatos) {
        this.guardaDatos = guardaDatos;
    }
}
