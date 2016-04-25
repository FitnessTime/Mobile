package com.fitnesstime.fitnesstime.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnesstime.fitnesstime.Activities.ActivityVerRutinas;
import com.fitnesstime.fitnesstime.Adapters.EjerciciosAdapter;
import com.fitnesstime.fitnesstime.Dominio.Ejercicio;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.ServicioEjercicio;

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

    public FragmentEjerciciosLunes(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_ver_rutinas, container, false);
        Rutina rutina = (Rutina) ((ActivityVerRutinas) getActivity()).getFlujo().getEntidad();
        String dia = ((ActivityVerRutinas) getActivity()).getSupportActionBar().getSelectedTab().getText().toString();

        ejercicios.clear();
        ejercicios = new ServicioEjercicio().getEjerciciosDe(rutina.getId(), dia);
        rvEjercicios = (RecyclerView) rootView.findViewById(R.id.recycler_ejercicio);
        adapter = null;
        adapter = new EjerciciosAdapter(ejercicios, getActivity(), getContext());
        rvEjercicios.setAdapter(null);

        rvEjercicios.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        rvEjercicios.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        //registerForContextMenu(rvEjercicios);
        return rootView;
    }
}