package com.fitnesstime.fitnesstime.Adapters;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.R;

import java.util.List;

/**
 * Created by julian on 22/02/16.
 */
public class HerramientaAdapter extends
        RecyclerView.Adapter<HerramientaAdapter.ViewHolder> {

    private List<ItemHerramienta> herramientas;

    public HerramientaAdapter(List<ItemHerramienta> herramientas) {
        this.herramientas = herramientas;
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
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        ItemHerramienta herramienta = herramientas.get(position);

        TextView textView = viewHolder.nameTextView;
        textView.setText(herramienta.getNombre());

    }

    @Override
    public int getItemCount() {
        return herramientas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public FloatingActionButton messageButton;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.nombre_herramienta);
            messageButton = (FloatingActionButton) itemView.findViewById(R.id.boton_herramienta);
        }
    }


}
