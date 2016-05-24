package com.fitnesstime.fitnesstime.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Assemblers.RutinaAssembler;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.DTOs.RutinaDTO;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.Eventos.EventoGuardarRutina;
import com.fitnesstime.fitnesstime.Flujos.FlujoPrincipal;
import com.fitnesstime.fitnesstime.Dominio.Ejercicio;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Flujos.FlujoRutinas;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.ServicioEjercicio;
import com.fitnesstime.fitnesstime.Servicios.ServicioRutina;
import com.fitnesstime.fitnesstime.Tasks.GuardarRutinaTask;
import com.fitnesstime.fitnesstime.Util.HelperToast;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ActivityEjercicio extends ActivityFlujo{

    private Ejercicio ejercicio;
    private List<Ejercicio> ejercicios = new ArrayList<>();
    private EditText nombre;
    private EditText series;
    private EditText repeticiones;
    private EditText tiempoActivo;
    private EditText tiempoDescanso;
    private Spinner diasDeLaSemana;
    private Button agregarEjercicio;
    private Rutina entidadRutina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(FitnessTimeApplication.isEjecutandoTarea() && !((FlujoRutinas) flujo).isModoIndividual())
        {
            FitnessTimeApplication.desactivarProgressDialog();
            FitnessTimeApplication.activarProgressDialog(this, "Guardando rutina...");
        }
        FitnessTimeApplication.getEventBus().register(this);

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
                if(((FlujoRutinas) flujo).isModoIndividual())
                    actualizarEjercicio();
                else
                    guardarRutina();
                return true;
            case android.R.id.home:
                if(((FlujoRutinas) flujo).isModoIndividual())
                    iniciarFlujoPrincipal();
                else
                    activityAnterior();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onEvent(EventoGuardarRutina evento)
    {
        FitnessTimeApplication.desactivarProgressDialog();
        FitnessTimeApplication.setEjecutandoTarea(false);
        HelperToast.generarToast(this, "Rutina creada con éxito.");
        iniciarFlujoPrincipal();
    }

    private void guardarRutina()
    {
        entidadRutina = (Rutina)flujo.getEntidad();
        try
        {
            entidadRutina.setEstaSincronizado(false);
            new ServicioRutina().guardar(entidadRutina);
            new ServicioEjercicio().guardarEjerciciosEnRutina(entidadRutina, this.ejercicios);
            new GuardarRutinaTask(this).execute(entidadRutina);
            FitnessTimeApplication.setEjecutandoTarea(true);
            FitnessTimeApplication.activarProgressDialog(this, "Guardando rutina...");
        }
        catch(Exception e)
        {
            HelperToast.generarToast(this, "No se pudo guardar por el siguiente error: " + e.getMessage());
        }
    }

    private void iniciarFlujoPrincipal()
    {
        FlujoPrincipal flujo = new FlujoPrincipal();
        if(((FlujoRutinas) this.flujo).isModoIndividual())
            flujo.setPosicionFragment(Constantes.FRAGMENT_EJERCICIOS);
        else
            flujo.setPosicionFragment(Constantes.FRAGMENT_RUTINA);
        setFlujo(flujo);
        finish();
        startActivity(new Intent(ActivityEjercicio.this, ActivityPrincipal.class));
    }

    private void iniciarActivity()
    {
        boolean esDeCarga;
        if(((FlujoRutinas) flujo).isModoIndividual())
        {
            esDeCarga = ((FlujoRutinas) flujo).getEjercicio().getEsDeCarga();
        }
        else
        {
            Rutina entidadRutina = (Rutina)flujo.getEntidad();
            esDeCarga = entidadRutina.getEsDeCarga();
        }
        if(esDeCarga)
            getSupportActionBar().setTitle(((FlujoRutinas) flujo).isModoIndividual()?((FlujoRutinas) flujo).getEjercicio().getNombre():"Rutinas: Ejercicio carga");
        else
            getSupportActionBar().setTitle(((FlujoRutinas) flujo).isModoIndividual()?((FlujoRutinas) flujo).getEjercicio().getNombre():"Rutinas: Ejercicio aerobico");
    }

    private void iniciarEditTextYEjercicio()
    {
        nombre = (EditText)findViewById(R.id.ejercicio_nombre);
        series = (EditText)findViewById(R.id.ejercicio_series);
        repeticiones = (EditText)findViewById(R.id.ejercicio_repeticiones);
        tiempoActivo = (EditText)findViewById(R.id.ejercicio_tiempo_activo);
        tiempoDescanso = (EditText)findViewById(R.id.ejercicio_tiempo_descanso);
        diasDeLaSemana = (Spinner)findViewById(R.id.dias_de_la_semana);

        boolean esDeCarga;
        if(((FlujoRutinas) flujo).isModoIndividual())
        {
            Ejercicio ejercicio = ((FlujoRutinas) flujo).getEjercicio();
            esDeCarga = ejercicio.getEsDeCarga();
            this.ejercicio = ejercicio;
            if(this.ejercicio.getId() != null)
            {
                nombre.setText(this.ejercicio.getNombre().toString());
                series.setText(this.ejercicio.getSeries().toString());
                diasDeLaSemana.setSelection(getPosicionDiaDeLaSemana(this.ejercicio.getDiaDeLaSemana()));
            }
            if(esDeCarga)
            {
                repeticiones.setText(this.ejercicio.getRepeticiones().toString());
            }
            else
            {
                tiempoActivo.setText(this.ejercicio.getTiempoActivo().toString());
                tiempoDescanso.setText(this.ejercicio.getTiempoDescanso().toString());
            }
        }
        else
        {
            Rutina entidadRutina = (Rutina)flujo.getEntidad();
            esDeCarga = entidadRutina.getEsDeCarga();
            this.ejercicio = new Ejercicio();
        }

        if(esDeCarga)
        {
            repeticiones.setVisibility(View.VISIBLE);
            tiempoActivo.setVisibility(View.INVISIBLE);
            tiempoDescanso.setVisibility(View.INVISIBLE);
        }
        else
        {
            repeticiones.setVisibility(View.INVISIBLE);
            tiempoDescanso.setVisibility(View.VISIBLE);
            tiempoActivo.setVisibility(View.VISIBLE);
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
        if(((FlujoRutinas) flujo).isModoIndividual())
            agregarEjercicio.setVisibility(View.INVISIBLE);
    }

    private void limpiarCampos()
    {
        nombre.setText("");
        series.setText("");
        repeticiones.setText("");
        tiempoActivo.setText("");
        tiempoDescanso.setText("");
        diasDeLaSemana.setSelection(0);
        this.ejercicio = new Ejercicio();
    }

    private void agregarEjercicio()
    {

        if(diasDeLaSemana.getSelectedItem().toString().equals("Día de la semana..."))
        {
            HelperToast.generarToast(this,"Seleccione un día de la semana.");
            return;
        }

        Rutina entidadRutina = (Rutina) flujo.getEntidad();
        if(entidadRutina.getEsDeCarga()) {
            this.ejercicio.setRepeticiones(Integer.parseInt(repeticiones.getText().toString() == "" ? "0" : repeticiones.getText().toString()));

        }else{
            this.ejercicio.setTiempoActivo(Integer.parseInt(tiempoActivo.getText().toString() == "" ? "0" : tiempoActivo.getText().toString()));
            this.ejercicio.setTiempoDescanso(Integer.parseInt(tiempoDescanso.getText().toString() == "" ? "0" : tiempoDescanso.getText().toString()));
        }
        this.ejercicio.setEsDeCarga(entidadRutina.getEsDeCarga());
        this.ejercicio.setDiaDeLaSemana(diasDeLaSemana.getSelectedItem().toString());
        this.ejercicio.setNombre(nombre.getText().toString());
        this.ejercicio.setSeries(Integer.parseInt(series.getText().toString() == "" ? "0" : series.getText().toString()));
        this.ejercicios.add(this.ejercicio);
        limpiarCampos();
    }

    private void completarEjercicio()
    {
        if(this.ejercicio.getEsDeCarga()) {
            this.ejercicio.setRepeticiones(Integer.parseInt(repeticiones.getText().toString() == "" ? "0" : repeticiones.getText().toString()));

        }else{
            this.ejercicio.setTiempoActivo(Integer.parseInt(tiempoActivo.getText().toString() == "" ? "0" : tiempoActivo.getText().toString()));
            this.ejercicio.setTiempoDescanso(Integer.parseInt(tiempoDescanso.getText().toString() == "" ? "0" : tiempoDescanso.getText().toString()));
        }
        this.ejercicio.setEsDeCarga(this.ejercicio.getEsDeCarga());
        this.ejercicio.setDiaDeLaSemana(diasDeLaSemana.getSelectedItem().toString());
        this.ejercicio.setNombre(nombre.getText().toString());
        this.ejercicio.setSeries(Integer.parseInt(series.getText().toString() == "" ? "0" : series.getText().toString()));
    }

    private int getPosicionDiaDeLaSemana(String dia)
    {
        switch(dia)
        {
            case "Lunes":
                return 1;
            case "Martes":
                return 2;
            case "Miercoles":
                return 3;
            case "Jueves":
                return 4;
            case "Viernes":
                return 5;
            case "Sabado":
                return 6;
            default:
                return 0;
        }
    }

    private void actualizarEjercicio()
    {
        completarEjercicio();
        new ServicioEjercicio().actualizar(this.ejercicio);
        iniciarFlujoPrincipal();
    }
}
