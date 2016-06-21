package com.fitnesstime.fitnesstime.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.Activities.ActivityEstadisticaMarcas;
import com.fitnesstime.fitnesstime.Activities.ActivityPrincipal;
import com.fitnesstime.fitnesstime.Adapters.EstadisticaAdapter;
import com.fitnesstime.fitnesstime.Adapters.ItemEstadistica;
import com.fitnesstime.fitnesstime.Adapters.ItemHerramienta;
import com.fitnesstime.fitnesstime.Flujos.FlujoTemporizador;
import com.fitnesstime.fitnesstime.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class EstadisticasFragment extends Fragment {

    private View rootView;

    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_estadisticas, container, false);


        RecyclerView rvHerramientas = (RecyclerView) rootView.findViewById(R.id.recycler_estadisticas);
        List<ItemEstadistica> estadisticas = new ArrayList<ItemEstadistica>();
        estadisticas.add(new ItemEstadistica("Estadistica marcas", "ic_estadisticas", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityPrincipal) getActivity()).setFlujo(new FlujoTemporizador());
                ((ActivityPrincipal) getActivity()).finish();
                ((ActivityPrincipal) getActivity()).startActivity(new Intent(((ActivityPrincipal) getActivity()), ActivityEstadisticaMarcas.class));
            }
        }));

        // Set adapter.
        EstadisticaAdapter adapter = new EstadisticaAdapter(estadisticas,(ActivityPrincipal) getActivity(), getContext());
        rvHerramientas.setAdapter(adapter);
        rvHerramientas.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        return rootView;
    }
}
