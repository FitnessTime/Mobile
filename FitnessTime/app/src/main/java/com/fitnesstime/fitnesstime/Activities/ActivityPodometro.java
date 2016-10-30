package com.fitnesstime.fitnesstime.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Dominio.Paso;
import com.fitnesstime.fitnesstime.Eventos.EventoPodometro;
import com.fitnesstime.fitnesstime.Flujos.FlujoPrincipal;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.ServicioPaso;
import com.fitnesstime.fitnesstime.Util.HelperNotificacion;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;


import java.util.ArrayList;
import java.util.Date;

public class ActivityPodometro extends ActivityFlujo{

    private ArrayList<String> labels = new ArrayList<String>();
    private PieChart pieChart;
    private int PASOS_TOTALES = 0;
    private TextView pasosDiarios;

    public void onEvent(EventoPodometro evento)
    {
        pasosDiarios = (TextView)findViewById(R.id.pasos_diarios);
        pasosDiarios.setText(String.valueOf(evento.getCantidadPasos()));
        SetearDatosEnPieChart(evento.getCantidadPasos());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podometro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Podometro");
        FitnessTimeApplication.getEventBus().register(this);

        PASOS_TOTALES = FitnessTimeApplication.getPasosMinimosUsuario();

        Paso paso = new ServicioPaso().getByFecha(new Date());

        IniciarPieChart();
        SetearDatosEnPieChart(paso != null ? paso.getPasosDados() : 0);
        pasosDiarios = (TextView)findViewById(R.id.pasos_diarios);
        pasosDiarios.setText(String.valueOf(paso != null ? paso.getPasosDados() : 0));

    }

    private void IniciarPieChart()
    {
        pieChart = (PieChart) findViewById(R.id.chart1);
        pieChart.animateY(4000);
        pieChart.setDescription("Podometro diario");
    }

    private void SetearDatosEnPieChart(float pasosDiarios)
    {
        pieChart.invalidate();
        ArrayList<Entry> entries = new ArrayList<>();
        if(pasosDiarios >= PASOS_TOTALES)
        {
            labels.clear();
            labels.add("Objetivo cumplido");
            entries.add(new Entry(PASOS_TOTALES, 0));
        }
        else
        {
            labels.clear();
            labels.add("Pasos faltantes");
            labels.add("Pasos dados");
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
            //dataset.setColors(ColorTemplate.COLORFUL_COLORS);
            dataset.setColors(colores);
            PieData data = new PieData(labels, dataset);
            pieChart.setData(data);
        }
    }

    @Override
    public void guardarDatos() {

    }

    @Override
    public void cargarDatos() {

    }

    @Override
    public boolean tieneSiguiente() {
        return false;
    }

    @Override
    public boolean tieneAnterior() {
        return false;
    }

    @Override
    public boolean esElPrimero()
    {
        return true;
    }

    @Override
    public void onBackPressed()
    {
        if(esElPrimero())
        {
            crearDialogoDeConfirmacion();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //stopService(new Intent(getBaseContext(), ServicioPodometro.class));
    }

    // Crea el dialogo de confirmacion.
    private void crearDialogoDeConfirmacion()
    {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("¿Desea salir del podometro?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        iniciarFlujoPrincipal();
                    }})
                .setNegativeButton("Cancelar", null).show();

        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#F57C00"));
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#F57C00"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                guardarDatos();
                crearDialogoDeConfirmacion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void iniciarFlujoPrincipal()
    {
        FlujoPrincipal flujo = new FlujoPrincipal();
        FitnessTimeApplication.setPosicionActivityPrincipal(Constantes.FRAGMENT_HERRAMIENTA);
        setFlujo(flujo);
        finish();
        startActivity(new Intent(ActivityPodometro.this, ActivityPrincipal.class));
    }


}
