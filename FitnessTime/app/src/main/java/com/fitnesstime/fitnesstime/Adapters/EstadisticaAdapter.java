package com.fitnesstime.fitnesstime.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.R;

import java.util.List;

/**
 * Created by julian on 19/06/16.
 */
public class EstadisticaAdapter extends
        RecyclerView.Adapter<EstadisticaAdapter.ViewHolder> {

    private List<ItemEstadistica> estadisticas;
    private ActivityFlujo activity;
    private Context context;

    public EstadisticaAdapter(List<ItemEstadistica> estadisticas,  ActivityFlujo activity, Context context) {
        this.estadisticas = estadisticas;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public EstadisticaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_estadistica, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        final ItemEstadistica estadistica = estadisticas.get(position);


        TextView textView = viewHolder.nombre;
        textView.setText(estadistica.getNombre().toString());
        ImageView image = viewHolder.imagen;

        int id = context.getResources().getIdentifier(estadistica.getIcono(), "mipmap", context.getPackageName());
        image.setImageResource(id);
        CardView card = viewHolder.card;
        card.setOnClickListener(estadistica.getAccion());
        //card.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return estadisticas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nombre;
        private ImageView imagen;
        public CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView)itemView.findViewById(R.id.card_estadistica);
            imagen = (ImageView)itemView.findViewById(R.id.card_icon_estadistica);
            nombre = (TextView) itemView.findViewById(R.id.nombre_estadistica);

        }
    }

}
