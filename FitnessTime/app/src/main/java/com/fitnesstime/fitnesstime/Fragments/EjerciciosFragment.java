package com.fitnesstime.fitnesstime.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnesstime.fitnesstime.Activities.ActivityPrincipal;
import com.fitnesstime.fitnesstime.Activities.ActivityPrincipalRutina;
import com.fitnesstime.fitnesstime.Flujos.FlujoRutinas;
import com.fitnesstime.fitnesstime.R;


public class EjerciciosFragment extends Fragment {
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_ejercicios, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.boton_agregar_ejercicio);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                ((ActivityPrincipal)getActivity()).setFlujo(new FlujoRutinas());
                ((ActivityPrincipal)getActivity()).finish();
                startActivity(new Intent(((ActivityPrincipal)getActivity()), ActivityPrincipalRutina.class));
            }
        });

        return rootView;
    }
}
