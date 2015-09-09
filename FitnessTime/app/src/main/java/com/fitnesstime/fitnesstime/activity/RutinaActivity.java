package com.fitnesstime.fitnesstime.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.adapter.EjercicioAdapter;
import com.fitnesstime.fitnesstime.dominio.Rutina;
import com.fitnesstime.fitnesstime.servicios.RutinaService;

public class RutinaActivity extends ActionBarActivity {

    private ListView list;
    public SharedPreferences sharedpreferences;
    private EjercicioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutina);
        this.sharedpreferences = this.getSharedPreferences("com.fitnesstime.fitnesstime", Context.MODE_PRIVATE);

        Rutina rutina = new RutinaService().GetRutina();

        adapter = new EjercicioAdapter(RutinaActivity.this, rutina.getEjercicios());
        list =(ListView)findViewById(R.id.listEjercicios);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            }
        });

        TextView lblRutina = (TextView)findViewById(R.id.lblRutina);
        lblRutina.setText("Objetivo: " + rutina.getObjetivo());
        TextView lblInicio = (TextView)findViewById(R.id.lblInicio);
        lblInicio.setText("Dia inicio: " + rutina.getInicio().toLocaleString());
        TextView lblFin = (TextView)findViewById(R.id.lblFin);
        lblFin.setText("Dia fin: " + rutina.getFin().toLocaleString());
    }

}
