package com.fitnesstime.fitnesstime.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.Activities.ActivityPrincipal;
import com.fitnesstime.fitnesstime.Activities.ActivityPrincipalRutina;
import com.fitnesstime.fitnesstime.Activities.ActivityVerRutinas;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Flujos.FlujoRutinas;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.ServicioRutina;
import com.fitnesstime.fitnesstime.Tasks.EliminarRutinaTask;
import com.fitnesstime.fitnesstime.Util.HelperToast;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by julian on 06/03/16.
 */
public class RutinasAdapter extends
        RecyclerView.Adapter<RutinasAdapter.ViewHolder> {

    private List<Rutina> rutinas;
    public Activity activity;
    private Context context;
    protected int posicionActual= 0;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public RutinasAdapter(List<Rutina> rutinas,  Activity activity, Context context) {
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

        this.posicionActual = position;

        final Rutina rutina = rutinas.get(position);

        viewHolder.descripcion.setText(rutina.getDescripcion());
        viewHolder.rangoFecha.setText(rutina.getInicio() + " - " + rutina.getFin());
        if(rutina.getEstaSincronizado())
            viewHolder.sincronizado.setVisibility(View.INVISIBLE);
        else
            viewHolder.sincronizado.setVisibility(View.VISIBLE);

        if(rutina.getEsDeCarga())
            viewHolder.textoInicial.setText("C");

        else
            viewHolder.textoInicial.setText("A");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar ahora = Calendar.getInstance();
        boolean activa = false;
        try {
            activa = formatter.parse(rutina.getInicio()).before(ahora.getTime()) && formatter.parse(rutina.getFin()).after(ahora.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(activa)
        {
            viewHolder.estadoRutina.setText("(Activa)");
            viewHolder.estadoRutina.setTextColor(Color.parseColor("#619B5D"));
        } else
        {
            viewHolder.estadoRutina.setText("(Finalizada)");
            viewHolder.estadoRutina.setTextColor(Color.parseColor("#CA1515"));
        }
    }

    @Override
    public int getItemCount() {
        return rutinas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener,
            MenuItem.OnMenuItemClickListener{

        public TextView descripcion;
        public TextView estadoRutina;
        public TextView rangoFecha;
        public TextView textoInicial;
        public ImageView sincronizado;
        public CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card);
            card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FlujoRutinas flujo = new FlujoRutinas();
                    final Rutina rutina = rutinas.get(getAdapterPosition());
                    flujo.setEntidad(rutina);
                    ((ActivityPrincipal) activity).setFlujo(flujo);
                    ((ActivityPrincipal) activity).finish();
                    ((ActivityPrincipal) activity).startActivity(new Intent(((ActivityPrincipal) activity), ActivityVerRutinas.class));
                }
            });
            descripcion = (TextView) itemView.findViewById(R.id.descripcion_rutina);
            estadoRutina = (TextView) itemView.findViewById(R.id.estadoRutina);
            rangoFecha = (TextView) itemView.findViewById(R.id.rango_fecha_rutina);
            textoInicial = (TextView)itemView.findViewById(R.id.texto_inicial_carga_aerobico);
            sincronizado = (ImageView) itemView.findViewById(R.id.card_icon_rutina);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Menu rutina");
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
                final Rutina rutina = rutinas.get(getAdapterPosition());
                flujo.setEntidad(rutina);
                ((ActivityPrincipal) activity).setFlujo(flujo);
                ((ActivityPrincipal) activity).finish();
                ((ActivityPrincipal) activity).startActivity(new Intent(((ActivityPrincipal) activity), ActivityPrincipalRutina.class));
            }
            if(item.getTitle() == "Eliminar")
            {
                FitnessTimeApplication.setEjecutandoTarea(true);
                FitnessTimeApplication.activarProgressDialog((ActivityPrincipal) activity, "Eliminando rutina...");
                final Rutina rutina = rutinas.get(getAdapterPosition());
                new ServicioRutina().marcarComoNoSincronizada(rutina.getId());
                eliminarRutina(rutina);
            }
            return true;
        }

        private void eliminarRutina(Rutina rutina)
        {
            new ServicioRutina().eliminar(rutina);
            new EliminarRutinaTask((ActivityPrincipal) activity).execute(rutina);
        }

    }
}
