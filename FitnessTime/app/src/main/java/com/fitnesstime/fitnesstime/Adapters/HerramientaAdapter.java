package com.fitnesstime.fitnesstime.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Activities.ActivityPrincipal;
import com.fitnesstime.fitnesstime.Activities.ActivityTemporizador;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Flujos.FlujoTemporizador;
import com.fitnesstime.fitnesstime.R;

import java.util.List;

/**
 * Created by julian on 22/02/16.
 */
public class HerramientaAdapter extends
        RecyclerView.Adapter<HerramientaAdapter.ViewHolder> {

    private List<ItemHerramienta> herramientas;
    private ActivityFlujo activity;
    private Context context;

    public HerramientaAdapter(List<ItemHerramienta> herramientas,  ActivityFlujo activity, Context context) {
        this.herramientas = herramientas;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public HerramientaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_herramienta, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        final ItemHerramienta herramienta = herramientas.get(position);

        TextView textView = viewHolder.nameTextView;
        textView.setText(herramienta.getNombre().toString());
        ImageView image = viewHolder.image;
        int id = context.getResources().getIdentifier(herramienta.getIcono(), "mipmap", context.getPackageName());
        image.setImageResource(id);
        CardView card = viewHolder.card;
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityPrincipal) activity).setFlujo(new FlujoTemporizador());
                ((ActivityPrincipal) activity).finish();
                ((ActivityPrincipal) activity).startActivity(new Intent(((ActivityPrincipal) activity), ActivityTemporizador.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return herramientas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        private ImageView image;
        public CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView)itemView.findViewById(R.id.card);
            image = (ImageView)itemView.findViewById(R.id.card_icon);
            nameTextView = (TextView) itemView.findViewById(R.id.nombre_herramienta);
        }
    }

}
