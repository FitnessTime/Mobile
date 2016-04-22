package com.fitnesstime.fitnesstime.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnesstime.fitnesstime.Adapters.EjerciciosAdapter;
import com.fitnesstime.fitnesstime.DAO.RutinaDAO;
import com.fitnesstime.fitnesstime.Modelo.Ejercicio;
import com.fitnesstime.fitnesstime.R;

import java.util.List;

/**
 * Created by julian on 21/04/16.
 */
public class FragmentEjerciciosLunes extends Fragment {

    private View rootView;
    private EjerciciosAdapter adapter;
    private RecyclerView rvEjercicios;
    private List<Ejercicio> ejercicios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_ver_rutinas, container, false);

        rvEjercicios = (RecyclerView) rootView.findViewById(R.id.recycler_ejercicio);

        ejercicios = new RutinaDAO().getEjerciciosDeRutinas();
        adapter = new EjerciciosAdapter(ejercicios, getActivity(), getContext());
        rvEjercicios.setAdapter(adapter);
        rvEjercicios.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        //registerForContextMenu(rvEjercicios);
        return rootView;
    }
}