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
import com.fitnesstime.fitnesstime.Eventos.EventoActuaizarEjercicio;
import com.fitnesstime.fitnesstime.Eventos.EventoGuardarEjercicio;
import com.fitnesstime.fitnesstime.Eventos.EventoGuardarRutina;
import com.fitnesstime.fitnesstime.Flujos.FlujoPrincipal;
import com.fitnesstime.fitnesstime.Dominio.Ejercicio;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Flujos.FlujoRutinas;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.ServicioEjercicio;
import com.fitnesstime.fitnesstime.Servicios.ServicioRutina;
import com.fitnesstime.fitnesstime.Tasks.EditarEjercicioTask;
import com.fitnesstime.fitnesstime.Tasks.GuardarEjercicioTask;
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
    private String diaDeLaSemanaInicial;
    private String nombreInicial;
    private int seriesInicial;
    private int repeticionesInicial;
    private int tiempoActivoInicial;
    private int tiempoDescansoInicial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(FitnessTimeApplication.isEjecutandoTarea())
        {
            FitnessTimeApplication.desactivarProgressDialog();
            FitnessTimeApplication.activarProgressDialog(this, FitnessTimeApplication.getMensajeTareaEnEjecucion());
        }
        if(!FitnessTimeApplication.getEventBus().isRegistered(this))
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
                if(((FlujoRutinas) flujo).isModoIndividual()) {
                    if(((FlujoRutinas) flujo).isNuevoEjercicio())
                        this.guardarEjercicio();
                    else
                        actualizarEjercicio();
                }
                else
                    guardarRutina();
                return true;
            case android.R.id.home:
                if(((FlujoRutinas) flujo).isModoIndividual()) {
                    if (((FlujoRutinas) flujo).isNuevoEjercicio())
                    {
                        iniciarFlujoVerEjercicios();
                    }
                    else
                        iniciarFlujoPrincipal();
                }
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
        HelperToast.generarToast(this, evento.getMensaje());
        iniciarFlujoPrincipal();
    }

    public void onEvent(EventoActuaizarEjercicio evento)
    {
        FitnessTimeApplication.desactivarProgressDialog();
        FitnessTimeApplication.setEjecutandoTarea(false);
        HelperToast.generarToast(this, evento.getMensaje());
        iniciarFlujoVerEjercicios();
    }

    public void onEvent(EventoGuardarEjercicio evento)
    {
        FitnessTimeApplication.desactivarProgressDialog();
        FitnessTimeApplication.setEjecutandoTarea(false);
        HelperToast.generarToast(this, evento.getMensaje());
        iniciarFlujoVerEjercicios();
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
            setearDialog("Guardando rutina...");
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
            FitnessTimeApplication.setPosicionActivityPrincipal(Constantes.FRAGMENT_EJERCICIOS);
        else
            FitnessTimeApplication.setPosicionActivityPrincipal(Constantes.FRAGMENT_RUTINA);
        setFlujo(flujo);
        finish();
        startActivity(new Intent(ActivityEjercicio.this, ActivityPrincipal.class));
    }

    private void iniciarFlujoVerEjercicios()
    {
        FitnessTimeApplication.setPosicionFragmentVerEjercicios(getPosicionDiaDeLaSemana(this.ejercicio.getDiaDeLaSemana())-1);
        FlujoRutinas flujoNuevo = new FlujoRutinas();
        final Rutina rutina = (Rutina)flujo.getEntidad();
        flujoNuevo.setEntidad(rutina);
        setFlujo(flujoNuevo);
        finish();
        startActivity(new Intent(this, ActivityVerRutinas.class));
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
            getSupportActionBar().setTitle(((FlujoRutinas) flujo).isNuevoEjercicio()?"Nuevo ejercicio de carga":((FlujoRutinas) flujo).isModoIndividual()?((FlujoRutinas) flujo).getEjercicio().getNombre():"Rutinas: Ejercicio carga");
        else
            getSupportActionBar().setTitle(((FlujoRutinas) flujo).isNuevoEjercicio()?"Nuevo ejercicio de aerobico":((FlujoRutinas) flujo).isModoIndividual()?((FlujoRutinas) flujo).getEjercicio().getNombre():"Rutinas: Ejercicio aerobico");
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
        if(((FlujoRutinas) flujo).isNuevoEjercicio())
        {
            this.ejercicio = ((FlujoRutinas) flujo).getEjercicio();
            esDeCarga = ejercicio.getEsDeCarga();

            if(esDeCarga)
            {
                tiempoActivo.setVisibility(View.INVISIBLE);
                tiempoDescanso.setVisibility(View.INVISIBLE);
                repeticiones.setVisibility(View.VISIBLE);
            }
            else
            {
                tiempoActivo.setVisibility(View.VISIBLE);
                tiempoDescanso.setVisibility(View.VISIBLE);
                repeticiones.setVisibility(View.INVISIBLE);
            }
            diasDeLaSemana.setEnabled(false);
            diasDeLaSemana.setSelection(getPosicionDiaDeLaSemana(ejercicio.getDiaDeLaSemana()));
        }
        else
        {
            if(((FlujoRutinas) flujo).isModoIndividual())
            {
                Ejercicio ejercicio = ((FlujoRutinas) flujo).getEjercicio();
                esDeCarga = ejercicio.getEsDeCarga();
                this.ejercicio = ejercicio;
                diaDeLaSemanaInicial = ejercicio.getDiaDeLaSemana();
                nombreInicial = ejercicio.getNombre();
                seriesInicial = ejercicio.getSeries();
                if(this.ejercicio.getId() != null)
                {
                    nombre.setText(this.ejercicio.getNombre().toString());
                    series.setText(this.ejercicio.getSeries().toString());
                }

                if(esDeCarga)
                {
                    repeticionesInicial = ejercicio.getRepeticiones();
                    repeticiones.setText(this.ejercicio.getRepeticiones().toString());
                }
                else
                {
                    tiempoActivoInicial = ejercicio.getTiempoActivo();
                    tiempoDescansoInicial = ejercicio.getTiempoDescanso();
                    tiempoActivo.setText(this.ejercicio.getTiempoActivo().toString());
                    tiempoDescanso.setText(this.ejercicio.getTiempoDescanso().toString());
                }
                diasDeLaSemana.setSelection(getPosicionDiaDeLaSemana(ejercicio.getDiaDeLaSemana()));
            }
            else
            {
                Rutina entidadRutina = (Rutina)flujo.getEntidad();
                esDeCarga = entidadRutina.getEsDeCarga();
                this.ejercicio = new Ejercicio();
                this.ejercicio.setNombreCambio(false);
                this.ejercicio.setDiaDeLaSemanaCambio(false);
                this.ejercicio.setSeriesCambio(false);
                this.ejercicio.setRepeticionesCambio(false);
                this.ejercicio.setTiempoActivoCambio(false);
                this.ejercicio.setTiempoDescansoCambio(false);
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
        this.ejercicio.setEstaSincronizado(false);
        if(!((FlujoRutinas) flujo).isNuevoEjercicio())
        {
            this.ejercicios.add(this.ejercicio);
            limpiarCampos();
        }
        else
        {
            this.guardarEjercicio();
        }
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
        if(dia == null)
            return 0;
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

    private void guardarEjercicio()
    {
        completarEjercicio();
        Rutina rutina = ((FlujoRutinas) flujo).getEntidad();
        new ServicioRutina().marcarComoNoSincronizada(rutina.getId());
        new ServicioEjercicio().guardarEjercicioEnRutina(rutina, this.ejercicio);
        new GuardarEjercicioTask(this).execute(this.ejercicio);
        setearDialog("Guardando ejercicio...");
    }

    private void actualizarEjercicio()
    {
        completarEjercicio();
        this.ejercicio.setEstaSincronizado(false);
        if(!this.ejercicio.getNombre().equals(nombreInicial))
            this.ejercicio.setNombreCambio(true);
        if(!this.ejercicio.getDiaDeLaSemana().equals(diaDeLaSemanaInicial))
            this.ejercicio.setDiaDeLaSemanaCambio(true);
        if(!this.ejercicio.getSeries().equals(seriesInicial))
            this.ejercicio.setSeriesCambio(true);
        if(this.ejercicio.getEsDeCarga() && (!this.ejercicio.getRepeticiones().equals(repeticionesInicial)))
        {
            this.ejercicio.setRepeticionesCambio(true);
        }
        if(!this.ejercicio.getEsDeCarga() && (!this.ejercicio.getTiempoActivo().equals(tiempoActivoInicial)))
        {
            this.ejercicio.setTiempoActivoCambio(true);
        }
        if(!this.ejercicio.getEsDeCarga() && (!this.ejercicio.getTiempoDescanso().equals(tiempoDescansoInicial)))
        {
            this.ejercicio.setTiempoDescansoCambio(true);
        }
        new ServicioRutina().marcarComoNoSincronizada(this.ejercicio.getRutinaId());
        new ServicioEjercicio().actualizar(this.ejercicio);
        new EditarEjercicioTask(this).execute(this.ejercicio);
        setearDialog("Modificando ejercicio...");
    }

    private void setearDialog(String mensaje)
    {
        FitnessTimeApplication.setEjecutandoTarea(true);
        FitnessTimeApplication.activarProgressDialog(this, mensaje);
    }
}
