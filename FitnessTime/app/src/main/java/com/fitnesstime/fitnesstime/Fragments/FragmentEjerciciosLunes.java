package com.fitnesstime.fitnesstime.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fitnesstime.fitnesstime.Activities.ActivityEjercicio;
import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Activities.ActivityPrincipal;
import com.fitnesstime.fitnesstime.Activities.ActivityVerRutinas;
import com.fitnesstime.fitnesstime.Adapters.EjerciciosAdapter;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Dominio.Ejercicio;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Eventos.EventoEliminarEjercicio;
import com.fitnesstime.fitnesstime.Eventos.EventoEliminarRutina;
import com.fitnesstime.fitnesstime.Flujos.FlujoRutinas;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.ServicioEjercicio;
import com.fitnesstime.fitnesstime.Servicios.ServicioMarca;
import com.fitnesstime.fitnesstime.Util.HelperSnackbar;
import com.fitnesstime.fitnesstime.Util.HelperToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julian on 21/04/16.
 */
public class FragmentEjerciciosLunes extends Fragment {

    private View rootView;
    private EjerciciosAdapter adapter;
    private RecyclerView rvEjercicios;
    private List<Ejercicio> ejercicios = new ArrayList<>();
    private String tituloTab;
    String dia = "";
    private FloatingActionButton fabButton;
    Rutina rutina;

    public FragmentEjerciciosLunes(){
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_ver_rutinas, container, false);
        if(!FitnessTimeApplication.getEventBus().isRegistered(this))
            FitnessTimeApplication.getEventBus().register(this);
        rutina = (Rutina) ((ActivityVerRutinas) getActivity()).getFlujo().getEntidad();
        ejercicios.clear();
        ejercicios = new ServicioEjercicio().getEjerciciosDe(rutina.getId(), dia);
        rvEjercicios = (RecyclerView) rootView.findViewById(R.id.recycler_ejercicio);
        adapter = new EjerciciosAdapter(ejercicios,(ActivityFlujo) getActivity(), getContext());
        adapter.notifyDataSetChanged();
        rvEjercicios.setAdapter(adapter);
        fabButton = (FloatingActionButton) rootView.findViewById(R.id.boton_agregar_ejercicio_en_dia);
        rvEjercicios.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FlujoRutinas flujo = new FlujoRutinas();
                final Ejercicio ejercicio = new Ejercicio();
                ejercicio.setRutinaId(rutina.getId());
                ejercicio.setDiaDeLaSemana(dia);
                ejercicio.setEsDeCarga(rutina.getEsDeCarga());
                flujo.setEjercicio(ejercicio);
                flujo.setModoIndividual(true);
                flujo.setNuevoEjercicio(true);
                flujo.setEntidad(rutina);
                ((ActivityVerRutinas) getActivity()).setFlujo(flujo);
                ((ActivityVerRutinas) getActivity()).finish();
                ((ActivityVerRutinas) getActivity()).startActivity(new Intent(((ActivityVerRutinas) getActivity()), ActivityEjercicio.class));
            }
        });
        return rootView;
    }

    public void onEvent(EventoEliminarEjercicio evento)
    {
        FitnessTimeApplication.desactivarProgressDialog();
        FitnessTimeApplication.setEjecutandoTarea(false);
        actualizarListaEjercicios();
        HelperSnackbar.generarSnackbar(rootView, evento.getMensaje());
    }

    private void actualizarListaEjercicios()
    {
        ejercicios = new ServicioEjercicio().getEjerciciosDe(rutina.getId(), dia);
        adapter = new EjerciciosAdapter(ejercicios,(ActivityFlujo) getActivity(), getContext());
        rvEjercicios.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
}