package com.fitnesstime.fitnesstime.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.SwipeListViewTouchListener;
import com.fitnesstime.fitnesstime.adapter.EjercicioAdapter;
import com.fitnesstime.fitnesstime.dominio.Ejercicio;
import com.fitnesstime.fitnesstime.servicios.EjercicioService;
import com.fitnesstime.fitnesstime.servicios.GenericService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class EjercicioActivity extends Activity {

    ListView list;
    ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
    private Gson gson = new Gson();
    private Type typeArray = new TypeToken<ArrayList<Ejercicio>>(){}.getType();
    EjercicioAdapter adapter;
    public SharedPreferences sharedpreferences;
    private int posicionEjercicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio);
        init();
        this.sharedpreferences = this.getSharedPreferences("com.fitnesstime.fitnesstime", Context.MODE_PRIVATE);
        final String ejerciciosJson = sharedpreferences.getString("Ejercicios", null);
        if(ejerciciosJson == null)
        {
            this.ejercicios = new ArrayList<Ejercicio>();
        }else {
            this.ejercicios = gson.fromJson(ejerciciosJson, typeArray);
        }

        loadAdapter(this.ejercicios);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                cargarModificacion(position);
                posicionEjercicio = position;
            }
        });

        SwipeListViewTouchListener touchListener =new SwipeListViewTouchListener(list,new SwipeListViewTouchListener.OnSwipeCallback() {
            @Override
            public void onSwipeLeft(ListView listView, int [] reverseSortedPositions) {
                //alertMessage(reverseSortedPositions[0]);
            }

            @Override
            public void onSwipeRight(ListView listView, int [] reverseSortedPositions) {
                //alertMessage(reverseSortedPositions[0]);
            }
        },true, false);

        //Escuchadores del listView
        list.setOnTouchListener(touchListener);
        list.setOnScrollListener(touchListener.makeScrollListener());

    }
   

    public void init()
    {
        btn_Crear();
        btn_Modificar();
        /*Button botonModificar = (Button)findViewById(R.id.buttonModificar);
        botonModificar.setVisibility(View.INVISIBLE);*/
//        SharedPreferences sharedpreferences = this.getSharedPreferences("com.fitnesstime.fitnesstime", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        String ejerciciosJson = sharedpreferences.getString("Ejercicios", null);
//
//        if(ejerciciosJson == null)
//        {
//            this.ejercicios  = new ArrayList<Ejercicio>();
//            editor.putString("Ejercicios",gson.toJson(this.ejercicios));
//            editor.commit();
//        }

    }

    public void set_Swipe()
    {

    }

    public void cargarModificacion(int position)
    {
        String json = sharedpreferences.getString("Ejercicios", null);
        ArrayList<Ejercicio> ejercicios = gson.fromJson(json,this.typeArray);
        Ejercicio ejercicioSelected = ejercicios.get(position);

        TextView nombre = (TextView)findViewById(R.id.inputNombre);
        nombre.setText(ejercicioSelected.getNombre());
        TextView peso = (TextView)findViewById(R.id.inputPeso);
        peso.setText(String.valueOf(ejercicioSelected.getPeso()));
        TextView series = (TextView)findViewById(R.id.inputSeries);
        series.setText(String.valueOf(ejercicioSelected.getSeries()));
        TextView repeticiones = (TextView)findViewById(R.id.inputRepeticiones);
        repeticiones.setText(String.valueOf(ejercicioSelected.getRepeticiones()));
    }

    public void btn_Crear()
    {
        Button btn_crear = (Button)findViewById(R.id.buttonCrear);
        btn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEjercicio();
            }
        });

    }

    public void btn_Modificar()
    {
        Button btn_modificar = (Button)findViewById(R.id.buttonModificar);
        btn_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarEjercicio();
            }
        });
    }

    public void loadAdapter(ArrayList<Ejercicio> ejs)
    {
        adapter = new EjercicioAdapter(this,ejs);
        list =(ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public EjercicioService getEjercicioService()
    {
        return new EjercicioService(this);
    }

    public void saveEjercicio(){
//        SharedPreferences sharedpreferences = this.getSharedPreferences("com.fitnesstime.fitnesstime", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//
//        String ejerciciosJson = sharedpreferences.getString("Ejercicios", null);
//        ArrayList<Ejercicio> ejercicios = gson.fromJson(ejerciciosJson,typeArray);

        EditText nombre = (EditText)findViewById(R.id.inputNombre);
        EditText peso = (EditText)findViewById(R.id.inputPeso);
        EditText series = (EditText)findViewById(R.id.inputSeries);
        EditText repeticiones = (EditText)findViewById(R.id.inputRepeticiones);
        Ejercicio ejercicio = new Ejercicio(nombre.getText().toString(),Integer.parseInt(peso.getText().toString()),Integer.parseInt(series.getText().toString()),Integer.parseInt(repeticiones.getText().toString()));


//        editor.putString("Ejercicios",gson.toJson(ejercicios));
//        editor.commit();
        loadAdapter(getEjercicioService().saveEjercicio(this, ejercicio));
    }

    public void modificarEjercicio()
    {
        String json = sharedpreferences.getString("Ejercicios", null);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        ArrayList<Ejercicio> ejercicios = gson.fromJson(json,this.typeArray);
        int count = 0;

        for(Ejercicio e : ejercicios)
        {
            if(count == posicionEjercicio)
            {
                EditText nombre = (EditText)findViewById(R.id.inputNombre);
                EditText peso = (EditText)findViewById(R.id.inputPeso);
                EditText series = (EditText)findViewById(R.id.inputSeries);
                EditText repeticiones = (EditText)findViewById(R.id.inputRepeticiones);

                e.setNombre(nombre.getText().toString());
                e.setPeso(Integer.parseInt(peso.getText().toString()));
                e.setSeries(Integer.parseInt(series.getText().toString()));
                e.setRepeticiones(Integer.parseInt(repeticiones.getText().toString()));
            }
            count++;
        }
        editor.putString("Ejercicios",gson.toJson(ejercicios));
        editor.commit();
        loadAdapter(ejercicios);
    }
}
