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
import android.widget.ImageView;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.Activities.ActivityPrincipal;
import com.fitnesstime.fitnesstime.Activities.ActivityPrincipalRutina;
import com.fitnesstime.fitnesstime.Activities.ActivityVerRutinas;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Flujos.FlujoRutinas;
import com.fitnesstime.fitnesstime.Modelo.EstadisticaPasos;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.ServicioRutina;
import com.fitnesstime.fitnesstime.Tasks.EliminarRutinaTask;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by julian on 31/10/16.
 */
public class EstadisticasPasosAdapter extends
        RecyclerView.Adapter<EstadisticasPasosAdapter.ViewHolder> {

    private List<EstadisticaPasos> estadisticaPasos;
    public Activity activity;
    private Context context;
    protected int posicionActual= 0;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private ArrayList<String> labels = new ArrayList<String>();

    public EstadisticasPasosAdapter(List<EstadisticaPasos> estadisticaPasos,  Activity activity, Context context) {
        this.estadisticaPasos = estadisticaPasos;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public EstadisticasPasosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_estadistica_pasos, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        this.posicionActual = position;

        final EstadisticaPasos estadisticaPasos = this.estadisticaPasos.get(position);
        SimpleDateFormat formateador = new SimpleDateFormat("EEEEEEEEE',' dd MMM", new Locale("es", "ES"));
        viewHolder.dia.setText(formateador.format(estadisticaPasos.getDia()));
        viewHolder.pasos.setText(String.valueOf(estadisticaPasos.getPasos()) + " pasos");
        SetearDatosEnPieChart(estadisticaPasos.getPasos(), viewHolder.pieChart);
    }

    @Override
    public int getItemCount() {
        return this.estadisticaPasos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener,
            MenuItem.OnMenuItemClickListener{

        public TextView dia;
        public TextView pasos;
        private PieChart pieChart;

        public ViewHolder(View itemView) {
            super(itemView);
            dia = (TextView) itemView.findViewById(R.id.dia_estadistica_pasos);
            pasos = (TextView) itemView.findViewById(R.id.pasos_estadistica_pasos);
            pieChart = (PieChart) itemView.findViewById(R.id.chart_estadistica_pasos);
            pieChart.animateY(4000);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            return false;
        }
    }

    private void SetearDatosEnPieChart(float pasosDiarios, PieChart pieChart)
    {
        pieChart.setDescription("");
        pieChart.setDrawSliceText(false);
        int PASOS_TOTALES = 1200;
        pieChart.invalidate();
        ArrayList<Entry> entries = new ArrayList<>();
        if(pasosDiarios >= PASOS_TOTALES)
        {
            labels.clear();
            labels.add(String.valueOf(PASOS_TOTALES));
            entries.add(new Entry(PASOS_TOTALES, 0));
        }
        else
        {
            labels.clear();
            labels.add(String.valueOf(PASOS_TOTALES - pasosDiarios));
            labels.add(String.valueOf(pasosDiarios));
            entries.add(new Entry(PASOS_TOTALES - pasosDiarios, 1));
            entries.add(new Entry(pasosDiarios, 0));
        }

        // initialize Piedata<br />

        if (pieChart.getData() != null &&
                pieChart.getData().getDataSetCount() > 0) {
            for(int i=0; i < pieChart.getData().getDataSets().size(); i++)
            {
                PieDataSet set = (PieDataSet)pieChart.getData().getDataSetByIndex(i);
                set.setYVals(entries);
                if( pasosDiarios >= PASOS_TOTALES)
                {
                    int[] colores = new int[1];
                    colores[0] = Color.parseColor("#35b800");
                    set.setColors(colores);
                }
            }


            pieChart.getData().notifyDataChanged();
            pieChart.notifyDataSetChanged();
        } else {
            // set data
            int[] colores;
            if( pasosDiarios >= PASOS_TOTALES)
            {
                colores = new int[1];
                colores[0] = Color.parseColor("#35b800");
            }
            else {
                colores = new int[2];
                colores[0] = Color.parseColor("#fb0000");
                colores[1] = Color.parseColor("#f99f45");
            }
            PieDataSet dataset = new PieDataSet(entries, "");
            dataset.setDrawValues(false);
            //dataset.setColors(ColorTemplate.COLORFUL_COLORS);
            dataset.setColors(colores);
            PieData data = new PieData(labels, dataset);
            pieChart.setData(data);
        }
    }
}
