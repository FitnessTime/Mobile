package com.fitnesstime.fitnesstime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Eventos.EventoActualizarRutina;
import com.fitnesstime.fitnesstime.Flujos.FlujoPrincipal;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.ServicioRutina;
import com.fitnesstime.fitnesstime.Tasks.EditarRutinaTask;
import com.fitnesstime.fitnesstime.Util.HelperToast;

import java.util.Calendar;


public class ActivityRegistroFechaRutina extends ActivityFlujo {

    private Button siguiente;

    private DatePicker fechaInicio;
    private int yearInicio;
    private int monthInicio;
    private int dayInicio;

    private DatePicker fechaFin;
    private int yearFin;
    private int monthFin;
    private int dayFin;
    private Rutina entidadRutina;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_fecha_rutina);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Rutinas");
        if(FitnessTimeApplication.isEjecutandoTarea())
        {
            FitnessTimeApplication.activarProgressDialog(this, "Modificando rutina...");
        }
        FitnessTimeApplication.getEventBus().register(this);
        entidadRutina = (Rutina)flujo.getEntidad();
        iniciarBotones();
    }

    @Override
    public void guardarDatos() {
        Rutina entidadRegistro = (Rutina)flujo.getEntidad();
        entidadRegistro.setInicio(dayInicio + "/" + monthInicio + "/" + yearInicio);
        entidadRegistro.setFin(dayFin + "/" + monthFin + "/" + yearFin);
    }

    @Override
    public void cargarDatos() {
        Rutina entidadRegistro = (Rutina)flujo.getEntidad();
        setearDiasDeInicioYFin(entidadRegistro.getInicio(), entidadRegistro.getFin());
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                activityAnterior();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onEvent(EventoActualizarRutina evento)
    {
        FitnessTimeApplication.desactivarProgressDialog();
        FitnessTimeApplication.setEjecutandoTarea(false);
        HelperToast.generarToast(this, "Rutina modificada con éxito.");
        iniciarFlujoPrincipal();
    }

    private void modificarRutina()
    {
        try
        {
            entidadRutina.setEstaSincronizado(false);
            entidadRutina.setVersion(entidadRutina.getVersion() + 1);
            new ServicioRutina().actualizar(entidadRutina);
            new EditarRutinaTask(this).execute(entidadRutina);
            FitnessTimeApplication.setEjecutandoTarea(true);
            FitnessTimeApplication.activarProgressDialog(this, "Modificando rutina...");
        }
        catch(Exception e)
        {
            HelperToast.generarToast(this, "No se pudo modificar por el siguiente error: " + e.getMessage());
        }
    }

    private void iniciarFlujoPrincipal()
    {
        setGuardaDatos(false);
        FlujoPrincipal flujo = new FlujoPrincipal();
        FitnessTimeApplication.setPosicionActivityPrincipal(Constantes.FRAGMENT_RUTINA);
        setFlujo(flujo);
        finish();
        startActivity(new Intent(ActivityRegistroFechaRutina.this, ActivityPrincipal.class));
    }

    private boolean validar()
    {
        if(yearInicio > yearFin)
        {
            HelperToast.generarToast(this, "La fecha de fin debe ser mayor a la de inicio.");
            return false;
        }
        else if(yearFin == yearInicio && monthInicio > monthFin)
        {
            HelperToast.generarToast(this, "La fecha de fin debe ser mayor a la de inicio.");
            return false;
        } else if(yearFin == yearInicio && monthFin == monthInicio && dayInicio > dayFin)
        {
            HelperToast.generarToast(this, "La fecha de fin debe ser mayor a la de inicio.");
            return false;
        }
        return true;
    }

    // Inicia las acciones de los botones del activity.
    private void iniciarBotones()
    {
        siguiente = (Button)findViewById(R.id.boton_siguiente_fecha_rutina);
        final Rutina entidadRegistro = (Rutina)flujo.getEntidad();
        if(!entidadRegistro.nuevaRutina())
        {
            siguiente.setText("Finalizar");
        }
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()) {
                    if (entidadRegistro.nuevaRutina()) {
                        guardarDatos();
                        activitySigiente();
                    } else {
                        modificarRutina();
                    }
                }
            }
        });
    }

    private void setearDiasDeInicioYFin(String fechaInicio, String fechaFin)
    {
        if(fechaInicio == "" || fechaInicio == null)
        {
            final Calendar c = Calendar.getInstance();
            yearInicio = c.get(Calendar.YEAR);
            monthInicio = c.get(Calendar.MONTH);
            dayInicio = c.get(Calendar.DAY_OF_MONTH);
        }
        else{
            if(fechaInicio.contains("-"))
            {
                fechaInicio = fechaInicio.replace("-", "/");
                String[] splitFecha = fechaInicio.split("/");
                yearInicio = Integer.parseInt(splitFecha[0]);
                monthInicio = Integer.parseInt(splitFecha[1]);
                dayInicio = Integer.parseInt(splitFecha[2]);
            }
            else {
                String[] splitFecha = fechaInicio.split("/");
                yearInicio = Integer.parseInt(splitFecha[2]);
                monthInicio = Integer.parseInt(splitFecha[1]);
                dayInicio = Integer.parseInt(splitFecha[0]);
            }
        }

        if(fechaFin == "" || fechaFin == null)
        {
            final Calendar c = Calendar.getInstance();
            yearFin = c.get(Calendar.YEAR);
            monthFin = c.get(Calendar.MONTH);
            dayFin = c.get(Calendar.DAY_OF_MONTH);
        }
        else{
            if(fechaFin.contains("-"))
            {
                fechaFin = fechaFin.replace("-", "/");
                String[] splitFecha = fechaFin.split("/");
                yearFin = Integer.parseInt(splitFecha[0]);
                monthFin = Integer.parseInt(splitFecha[1]);
                dayFin = Integer.parseInt(splitFecha[2]);
            }
            else {
                String[] splitFecha = fechaFin.split("/");
                yearFin = Integer.parseInt(splitFecha[2]);
                monthFin = Integer.parseInt(splitFecha[1]);
                dayFin = Integer.parseInt(splitFecha[0]);
            }
        }
        iniciarDatePickers();
    }

    private void iniciarDatePickers()
    {
        fechaInicio = (DatePicker) findViewById(R.id.fecha_inicio_rutina);
        fechaInicio.init(yearInicio, monthInicio, dayInicio, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int año, int mes, int dia) {
                yearInicio = año;
                monthInicio = mes;
                dayInicio = dia;
            }
        });

        fechaFin = (DatePicker) findViewById(R.id.fecha_fin_rutina);
        fechaFin.init(yearFin, monthFin, dayFin, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int año, int mes, int dia) {
                yearFin = año;
                monthFin = mes;
                dayFin = dia;
            }
        });
    }
}
