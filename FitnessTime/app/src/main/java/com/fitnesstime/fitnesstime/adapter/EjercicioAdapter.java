package com.fitnesstime.fitnesstime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.dominio.Ejercicio;

import java.util.ArrayList;

/**
 * Created by julian on 22/08/15.
 */
public class EjercicioAdapter extends ArrayAdapter<Ejercicio>{

    private Context mContext;
    public ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();

    public EjercicioAdapter(Context c,ArrayList<Ejercicio> ejercicios) {
        super(c, 0, ejercicios);
        mContext = c;
        this.ejercicios = ejercicios;
    }

    public void updateData(ArrayList<Ejercicio> ejercicios)
    {
        this.ejercicios.clear();
        this.ejercicios.addAll(ejercicios);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            Ejercicio e = this.ejercicios.get(position);

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.single_ejercicio, null);
            TextView nombre = (TextView) grid.findViewById(R.id.nombre);
            nombre.setTextSize(12);
            nombre.setText(this.ejercicios.get(position).getNombre() + "   ");
            TextView peso = (TextView) grid.findViewById(R.id.peso);
            peso.setTextSize(12);
            peso.setText(" Peso: " + String.valueOf(e.getPeso()));
            TextView series = (TextView) grid.findViewById(R.id.series);
            series.setTextSize(12);
            series.setText(" Series: " + String.valueOf(e.getSeries()));
            TextView repeticiones = (TextView) grid.findViewById(R.id.repeticiones);
            repeticiones.setTextSize(12);
            repeticiones.setText(" Repeticiones: " + String.valueOf(e.getRepeticiones()));
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}