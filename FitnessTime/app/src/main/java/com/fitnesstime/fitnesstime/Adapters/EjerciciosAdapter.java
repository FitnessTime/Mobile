package com.fitnesstime.fitnesstime.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.Activities.ActivityEjercicio;
import com.fitnesstime.fitnesstime.Activities.ActivityPrincipal;
import com.fitnesstime.fitnesstime.Dominio.Ejercicio;
import com.fitnesstime.fitnesstime.Dominio.Marca;
import com.fitnesstime.fitnesstime.Flujos.FlujoRutinas;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.ServicioMarca;
import com.fitnesstime.fitnesstime.Util.HelperToast;

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
    private View viewAgregarMarca;

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
        viewAgregarMarca = inflater.inflate(R.layout.agregar_marca,parent,false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        this.posicionActual = position;

        final Ejercicio ejercicio = ejercicios.get(position);
        final Marca marca = new ServicioMarca().ultimaMarcaDe(ejercicio.getId());
        viewHolder.series.setText("Series: " + ejercicio.getSeries());
        viewHolder.dia.setText("Día: " + ejercicio.getDiaDeLaSemana());
        if(ejercicio.getEsDeCarga()) {
            viewHolder.nombre.setText("Ejercicio carga: " + ejercicio.getNombre());
            viewHolder.repeticiones.setText("Repeticiones: " + ejercicio.getRepeticiones());
            viewHolder.repeticiones.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.nombre.setText("Ejercicio aerobico: " + ejercicio.getNombre());
            viewHolder.repeticiones.setVisibility(View.INVISIBLE);
        }
        viewHolder.ultimaMarca.setText(marca==null?"No tiene marcas":"Última marca registrada: " + marca.getCarga() + "kg");
    }

    @Override
    public int getItemCount() {
        return ejercicios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener,
            MenuItem.OnMenuItemClickListener{

        public TextView nombre;
        public TextView series;
        public TextView repeticiones;
        public TextView ultimaMarca;
        public TextView dia;
        public CardView card;
        public ImageView agregarMArca;

        public ViewHolder(final View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.nombre_ejercicio);
            series = (TextView) itemView.findViewById(R.id.series_ejercicio);
            repeticiones = (TextView) itemView.findViewById(R.id.repeticiones_ejercicio);
            ultimaMarca = (TextView) itemView.findViewById(R.id.ultima_marca_registrada);
            dia = (TextView) itemView.findViewById(R.id.dia_de_la_semana);
            agregarMArca = (ImageView)itemView.findViewById(R.id.icono_agregar_marca);
            agregarMArca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setTitle("Nueva marca")
                    .setView(viewAgregarMarca)
                            .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    final EditText peso = (EditText)viewAgregarMarca.findViewById(R.id.peso_marca);
                                    Ejercicio ejercicio = ejercicios.get(getAdapterPosition());
                                    new ServicioMarca().agregarMarca(Integer.parseInt(peso.getText().toString()),ejercicio.getId());
                                    HelperToast.generarToast(activity,"Marca agregada con éxito");
                                    notifyDataSetChanged();

                                }
                            })
                            .setNegativeButton("Cancelar", null).show();
                    dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#F57C00"));
                    dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#F57C00"));
                }
            });
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
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem myActionItem = menu.add("Editar");
            MenuItem myActionItem1 = menu.add("Eliminar");
            myActionItem.setOnMenuItemClickListener(this);
            myActionItem1.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(item.getTitle() == "Editar")
            {
                FlujoRutinas flujo = new FlujoRutinas();
                final Ejercicio ejercicio = ejercicios.get(getAdapterPosition());
                flujo.setEjercicio(ejercicio);
                flujo.setModoIndividual(true);
                ((ActivityPrincipal) activity).setFlujo(flujo);
                ((ActivityPrincipal) activity).finish();
                ((ActivityPrincipal) activity).startActivity(new Intent(((ActivityPrincipal) activity), ActivityEjercicio.class));
            }
            return true;
        }

        @Override
        public void onClick(View v) {

        }
    }

}
