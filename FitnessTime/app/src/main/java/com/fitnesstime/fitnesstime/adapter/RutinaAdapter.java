package com.fitnesstime.fitnesstime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.dominio.Ejercicio;

import java.util.ArrayList;

/**
 * Created by julian on 24/08/15.
 */
public class RutinaAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();

    public RutinaAdapter(Context c, ArrayList<Ejercicio> ejericicos) {
        mContext = c;
        this.ejercicios = ejericicos;
    }

    @Override
    public int getCount() {
        return this.ejercicios.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
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
            nombre.setText(this.ejercicios.get(position).getNombre());
//            TextView peso = (TextView) grid.findViewById(R.id.peso);
//            peso.setText(String.valueOf(e.getPeso()));
//            TextView series = (TextView) grid.findViewById(R.id.series);
//            series.setText(String.valueOf(e.getSeries()));
//            TextView repeticiones = (TextView) grid.findViewById(R.id.repeticiones);
//            repeticiones.setText(String.valueOf(e.getRepeticiones()));
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
