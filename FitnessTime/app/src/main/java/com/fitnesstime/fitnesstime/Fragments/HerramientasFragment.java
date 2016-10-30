package com.fitnesstime.fitnesstime.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnesstime.fitnesstime.Activities.ActivityCuentaDistancia;
import com.fitnesstime.fitnesstime.Activities.ActivityMapa;
import com.fitnesstime.fitnesstime.Activities.ActivityPrincipal;
import com.fitnesstime.fitnesstime.Activities.ActivityTemporizador;
import com.fitnesstime.fitnesstime.Activities.ActivityPodometro;
import com.fitnesstime.fitnesstime.Adapters.HerramientaAdapter;
import com.fitnesstime.fitnesstime.Adapters.ItemHerramienta;
import com.fitnesstime.fitnesstime.Flujos.FlujoCuentaDistancia;
import com.fitnesstime.fitnesstime.Flujos.FlujoMapa;
import com.fitnesstime.fitnesstime.Flujos.FlujoTemporizador;
import com.fitnesstime.fitnesstime.R;

import java.util.ArrayList;
import java.util.List;

public class HerramientasFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_herramientas, container, false);


        RecyclerView rvHerramientas = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        List<ItemHerramienta> herramientas = new ArrayList<ItemHerramienta>();
        herramientas.add(new ItemHerramienta("Temporizador", "ic_temporizador", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityPrincipal) getActivity()).setFlujo(new FlujoTemporizador());
                ((ActivityPrincipal) getActivity()).finish();
                ((ActivityPrincipal) getActivity()).startActivity(new Intent(((ActivityPrincipal) getActivity()), ActivityTemporizador.class));
            }
        }));
        herramientas.add(new ItemHerramienta("Sigueme", "ic_sigueme", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityPrincipal) getActivity()).setFlujo(new FlujoMapa());
                ((ActivityPrincipal) getActivity()).finish();
                ((ActivityPrincipal) getActivity()).startActivity(new Intent(((ActivityPrincipal) getActivity()), ActivityMapa.class));
            }
        }));
        herramientas.add(new ItemHerramienta("Cuenta distancia", "ic_cuentakilometros", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityPrincipal) getActivity()).setFlujo(new FlujoCuentaDistancia());
                ((ActivityPrincipal) getActivity()).finish();
                ((ActivityPrincipal) getActivity()).startActivity(new Intent(((ActivityPrincipal) getActivity()), ActivityCuentaDistancia.class));
            }
        }));
        herramientas.add(new ItemHerramienta("Contador pasos", "ic_cuentakilometros", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityPrincipal) getActivity()).setFlujo(new FlujoCuentaDistancia());
                ((ActivityPrincipal) getActivity()).finish();
                ((ActivityPrincipal) getActivity()).startActivity(new Intent(((ActivityPrincipal) getActivity()), ActivityPodometro.class));
            }
        }));

        // Set adapter.
        HerramientaAdapter adapter = new HerramientaAdapter(herramientas,(ActivityPrincipal) getActivity(), getContext());
        rvHerramientas.setAdapter(adapter);
        rvHerramientas.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        return rootView;
    }
}
