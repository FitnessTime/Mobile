package com.fitnesstime.fitnesstime.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by julian on 06/03/16.
 */
public class RutinasAdapter extends
        RecyclerView.Adapter<RutinasAdapter.ViewHolder> {

    private List<ItemRutina> rutinas;
    private Activity activity;
    private Context context;

    public RutinasAdapter(List<ItemRutina> rutinas,  Activity activity, Context context) {
        this.rutinas = rutinas;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public RutinasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_rutina, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        final ItemRutina rutina = rutinas.get(position);

        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");

        viewHolder.descripcion.setText(rutina.getDescripcion().toString());
        viewHolder.rangoFecha.setText(dt.format(rutina.getInicioRutina()) + " - " + dt.format(rutina.getFinRutina()));
    }

    @Override
    public int getItemCount() {
        return rutinas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView descripcion;
        public TextView rangoFecha;
        public CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView)itemView.findViewById(R.id.card);
            descripcion = (TextView) itemView.findViewById(R.id.descripcion_rutina);
            rangoFecha = (TextView) itemView.findViewById(R.id.rango_fecha_rutina);
        }
    }

}
