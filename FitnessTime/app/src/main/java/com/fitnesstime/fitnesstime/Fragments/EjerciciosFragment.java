package com.fitnesstime.fitnesstime.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Adapters.EjerciciosAdapter;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Dominio.Ejercicio;
import com.fitnesstime.fitnesstime.Eventos.EventoEliminarEjercicio;
import com.fitnesstime.fitnesstime.Eventos.EventoEliminarRutina;
import com.fitnesstime.fitnesstime.Eventos.EventoSincronizarRutinas;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.ServicioEjercicio;
import com.fitnesstime.fitnesstime.Util.HelperSnackbar;
import com.fitnesstime.fitnesstime.Util.HelperToast;

import java.util.List;

public class EjerciciosFragment extends Fragment {

    private View rootView;
    private EjerciciosAdapter adapter;
    private RecyclerView rvEjercicios;
    private List<Ejercicio> ejercicios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_ejercicios, container, false);
        if(!FitnessTimeApplication.getEventBus().isRegistered(this))
            FitnessTimeApplication.getEventBus().register(this);
        rvEjercicios = (RecyclerView) rootView.findViewById(R.id.recycler_ejercicio);

        ejercicios = new ServicioEjercicio().getAll();
        adapter = new EjerciciosAdapter(ejercicios,(ActivityFlujo) getActivity(), getContext());
        rvEjercicios.setAdapter(adapter);
        rvEjercicios.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        return rootView;
    }

    public void onEvent(EventoSincronizarRutinas evento)
    {
        FitnessTimeApplication.desactivarProgressDialog();
        FitnessTimeApplication.setEjecutandoTarea(false);
        actualizarListaEjercicios();
    }

    public void onEvent(EventoEliminarRutina evento)
    {
        FitnessTimeApplication.desactivarProgressDialog();
        FitnessTimeApplication.setEjecutandoTarea(false);
        actualizarListaEjercicios();
        HelperSnackbar.generarSnackbar(rootView, evento.getMensaje());
    }

    public void onEvent(EventoEliminarEjercicio evento)
    {
        FitnessTimeApplication.desactivarProgressDialog();
        FitnessTimeApplication.setEjecutandoTarea(false);
        actualizarListaEjercicios();
        HelperSnackbar.generarSnackbar(rootView, evento.getMensaje());
    }

    // Actualiza la lista de ejercicios segun evento.
    private void actualizarListaEjercicios()
    {
        ejercicios = new ServicioEjercicio().getAll();
        adapter = new EjerciciosAdapter(ejercicios,(ActivityFlujo) getActivity(), getContext());
        rvEjercicios.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
