package com.fitnesstime.fitnesstime.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.Modelo.Ejercicio;
import com.fitnesstime.fitnesstime.R;

import java.util.List;

/**
 * Created by julian on 18/04/16.
 */
public class EjerciciosAdapter extends
        RecyclerView.Adapter<EjerciciosAdapter.ViewHolder> {

    private List<Ejercicio> ejercicios;
    public Activity activity;
    private Context context;
    protected int posicionActual= 0;

    public EjerciciosAdapter(List<Ejercicio> ejercicios,  Activity activity, Context context) {
        this.ejercicios = ejercicios;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public EjerciciosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_ejercicio, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        this.posicionActual = position;

        final Ejercicio ejercicio = ejercicios.get(position);

        viewHolder.nombre.setText(ejercicio.getNombre());
        viewHolder.dia.setText(ejercicio.getDiaDeLaSemana());
        if(ejercicio.isEsDeCarga())
            viewHolder.textoInicial.setText("C");
        else
            viewHolder.textoInicial.setText("A");
    }

    @Override
    public int getItemCount() {
        return ejercicios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nombre;
        public TextView dia;
        public TextView textoInicial;
        public CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.nombre_ejercicio);
            dia = (TextView) itemView.findViewById(R.id.dia_de_la_semana);
            textoInicial = (TextView)itemView.findViewById(R.id.texto_inicial_ejercicio);
            card = (CardView)itemView.findViewById(R.id.card);
            card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }

}
