package com.fitnesstime.fitnesstime.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.DAO.RutinaDAO;
import com.fitnesstime.fitnesstime.Flujos.FlujoPrincipal;
import com.fitnesstime.fitnesstime.Modelo.EjercicioAerobico;
import com.fitnesstime.fitnesstime.Modelo.EjercicioCarga;
import com.fitnesstime.fitnesstime.Modelo.Rutina;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Util.HelperToast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ActivityEjercicio extends ActivityFlujo{

    private EjercicioCarga ejercicioCarga;
    private EjercicioAerobico ejercicioAerobico;
    private List<EjercicioCarga> ejerciciosCarga = new ArrayList<>();
    private List<EjercicioAerobico> ejerciciosAerobico = new ArrayList<>();
    private EditText nombre;
    private EditText series;
    private EditText repeticiones;
    private EditText tiempoActivo;
    private EditText tiempoDescanso;
    private Spinner diasDeLaSemana;
    private Button agregarEjercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iniciarActivity();
        iniciarEditTextYEjercicio();
        iniciarBotones();
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
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_ejercicio, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_check:
                guardarRutina();
                return true;
            case android.R.id.home:
                activityAnterior();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void guardarRutina()
    {
        try
        {
            Realm db = new FitnessTimeApplication().getDB();
            Rutina entidadRutina = (Rutina)flujo.getEntidad();
            db.beginTransaction();
            entidadRutina.getEjerciciosCarga().addAll(this.ejerciciosCarga);
            entidadRutina.getEjerciciosAerobicos().addAll(this.ejerciciosAerobico);
            db.commitTransaction();
            new RutinaDAO().crear(entidadRutina);
            HelperToast.generarToast(this, "Rutina creada con exito.");
            iniciarFlujoPrincipal();
        }
        catch(Exception e)
        {
            HelperToast.generarToast(this, "No se pudo guardar por el siguiente error: " + e.getMessage());
        }
    }

    private void iniciarFlujoPrincipal()
    {
        FlujoPrincipal flujo = new FlujoPrincipal();
        flujo.setPosicionFragment(Constantes.FRAGMENT_RUTINA);
        setFlujo(flujo);
        finish();
        startActivity(new Intent(ActivityEjercicio.this, ActivityPrincipal.class));
    }

    // Crea el dialogo de confirmacion.
    private void crearDialogoDeConfirmacion()
    {
        new AlertDialog.Builder(this)
                .setMessage("¿Dese cancelar la operación?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        iniciarFlujoPrincipal();
                    }})
                .setNegativeButton("Cancelar", null).show();
    }

    private void iniciarActivity()
    {
        Rutina entidadRegistro = (Rutina)flujo.getEntidad();
        if(entidadRegistro.getEsDeCarga())
            getSupportActionBar().setTitle("Rutinas: Ejercicio carga");
        else
            getSupportActionBar().setTitle("Rutinas: Ejercicio aerobico");
    }

    private void iniciarEditTextYEjercicio()
    {
        nombre = (EditText)findViewById(R.id.ejercicio_nombre);
        series = (EditText)findViewById(R.id.ejercicio_series);
        repeticiones = (EditText)findViewById(R.id.ejercicio_repeticiones);
        tiempoActivo = (EditText)findViewById(R.id.ejercicio_tiempo_activo);
        tiempoDescanso = (EditText)findViewById(R.id.ejercicio_tiempo_descanso);
        diasDeLaSemana = (Spinner)findViewById(R.id.dias_de_la_semana);

        Rutina entidadRegistro = (Rutina)flujo.getEntidad();
        if(entidadRegistro.getEsDeCarga())
        {
            repeticiones.setVisibility(View.VISIBLE);
            tiempoActivo.setVisibility(View.INVISIBLE);
            tiempoDescanso.setVisibility(View.INVISIBLE);
            this.ejercicioCarga = new EjercicioCarga();
        }
        else
        {
            repeticiones.setVisibility(View.INVISIBLE);
            tiempoDescanso.setVisibility(View.VISIBLE);
            tiempoActivo.setVisibility(View.VISIBLE);
            this.ejercicioAerobico = new EjercicioAerobico();
        }
    }

    private void iniciarBotones()
    {
        agregarEjercicio = (Button)findViewById(R.id.boton_agregar_ejercicio);
        agregarEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarEjercicio();
                limpiarCampos();
            }
        });
    }

    private void limpiarCampos()
    {
        nombre.setText("");
        series.setText("");
        repeticiones.setText("");
        tiempoActivo.setText("");
        tiempoDescanso.setText("");
        this.ejercicioCarga = new EjercicioCarga();
        this.ejercicioAerobico = new EjercicioAerobico();
        //this.ejerciciosAerobico = new ArrayList<>();
        //this.ejerciciosCarga = new ArrayList<>();
    }

    private void agregarEjercicio()
    {

        Rutina entidadRutina = (Rutina) flujo.getEntidad();
        if(entidadRutina.getEsDeCarga()) {
            this.ejercicioCarga.setDiaDeLaSemana(diasDeLaSemana.getTransitionName());
            this.ejercicioCarga.setNombre(nombre.getText().toString());
            this.ejercicioCarga.setSeries(Integer.parseInt(series.getText().toString() == "" ? "0" : series.getText().toString()));
            this.ejercicioCarga.setRepeticiones(Integer.parseInt(repeticiones.getText().toString() == "" ? "0" : repeticiones.getText().toString()));
            this.ejerciciosCarga.add(this.ejercicioCarga);

        }else{
            this.ejercicioAerobico.setDiaDeLaSemana(diasDeLaSemana.getTransitionName());
            this.ejercicioAerobico.setNombre(nombre.getText().toString());
            this.ejercicioAerobico.setSeries(Integer.parseInt(series.getText().toString() == "" ? "0" : series.getText().toString()));
            this.ejercicioAerobico.setTiempoActivo(Integer.parseInt(tiempoActivo.getText().toString() == "" ? "0" : tiempoActivo.getText().toString()));
            this.ejercicioAerobico.setTiempoDescanso(Integer.parseInt(tiempoDescanso.getText().toString() == "" ? "0" : tiempoDescanso.getText().toString()));
            this.ejerciciosAerobico.add(this.ejercicioAerobico);
        }
        limpiarCampos();
    }

}
