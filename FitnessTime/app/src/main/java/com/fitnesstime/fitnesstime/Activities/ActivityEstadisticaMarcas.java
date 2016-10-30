package com.fitnesstime.fitnesstime.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Custom.MyMarkerView;
import com.fitnesstime.fitnesstime.Dominio.Marca;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Flujos.FlujoPrincipal;
import com.fitnesstime.fitnesstime.Modelo.EstadisticaMarca;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.ServicioMarca;
import com.fitnesstime.fitnesstime.Servicios.ServicioRutina;
import com.fitnesstime.fitnesstime.Util.HelperSnackbar;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ActivityEstadisticaMarcas extends ActivityFlujo implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener, Spinner.OnItemSelectedListener {

    private LineChart mChart;
    List<Rutina> rutinas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_estadistica_marcas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Estadistica marcas");

        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);

        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("No tiene marcas en ningun ejercicio.");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(true);
        mChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        //mChart.setBackgroundColor(Color.LTGRAY);

        // add data
        //setData(20, 30);

        mChart.animateX(2500);

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(tf);
        l.setTextSize(11f);
        l.setTextColor(Color.BLACK);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
//        l.setYOffset(11f);

        /*XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(tf);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setSpaceBetweenLabels(1);
*/
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setAxisMaxValue(500f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setGranularityEnabled(false);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setTypeface(tf);
        rightAxis.setAxisMaxValue(500);
        rightAxis.setAxisMinValue(0);
        rightAxis.setDrawGridLines(true);
        rightAxis.setDrawZeroLine(true);
        rightAxis.setGranularityEnabled(false);

        rutinas = new ServicioRutina().getAll();
        ArrayAdapter rutinasAdapter = new ArrayAdapter(this, R.layout.spinner_rutinas, rutinas);

        Spinner rutinasSpinner = (Spinner) findViewById(R.id.spinner_rutinas);
        rutinasSpinner.setOnItemSelectedListener(this);
        rutinasSpinner.setAdapter(rutinasAdapter);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.line, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                iniciarFlujoPrincipal();
                return true;
            case R.id.actionToggleValues: {
                List<ILineDataSet> sets = mChart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    set.setDrawValues(!set.isDrawValuesEnabled());
                }

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if (mChart.getData() != null) {
                    mChart.getData().setHighlightEnabled(!mChart.getData().isHighlightEnabled());
                    mChart.invalidate();
                }
                break;
            }
            case R.id.actionToggleFilled: {

                List<ILineDataSet> sets = mChart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    if (set.isDrawFilledEnabled())
                        set.setDrawFilled(false);
                    else
                        set.setDrawFilled(true);
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleCircles: {
                List<ILineDataSet> sets = mChart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    if (set.isDrawCirclesEnabled())
                        set.setDrawCircles(false);
                    else
                        set.setDrawCircles(true);
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleCubic: {
                List<ILineDataSet> sets = mChart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    set.setMode(set.getMode() == LineDataSet.Mode.CUBIC_BEZIER
                            ? LineDataSet.Mode.LINEAR
                            :  LineDataSet.Mode.CUBIC_BEZIER);
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleStepped: {
                List<ILineDataSet> sets = mChart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    set.setMode(set.getMode() == LineDataSet.Mode.STEPPED
                            ? LineDataSet.Mode.LINEAR
                            :  LineDataSet.Mode.STEPPED);
                }
                mChart.invalidate();
                break;
            }

            case R.id.actionTogglePinch: {
                if (mChart.isPinchZoomEnabled())
                    mChart.setPinchZoom(false);
                else
                    mChart.setPinchZoom(true);

                mChart.invalidate();
                break;
            }
            case R.id.actionSave: {
                if (mChart.saveToPath("title" + System.currentTimeMillis(), "")) {
                    HelperSnackbar.generarSnackbar(findViewById(android.R.id.content), "Estadistica guardada con éxito.");
                } else
                    HelperSnackbar.generarSnackbar(findViewById(android.R.id.content), "No se pudo guardar la estadistica.");

                mChart.saveToGallery("estadisticaMarca", 85);
                break;
            }
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        // redraw
        mChart.invalidate();
    }

    private void setData(List<EstadisticaMarca> estadisticas, float range) {

        mChart.clear();
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < cantidadMayorDeMarcas(estadisticas); i++) {
            xVals.add((i) + "");
        }

        Random rand = new Random();

        List<ILineDataSet> lineDataSets = new ArrayList<>();
        List<List<Entry>> yVals = new ArrayList<>();
        for(EstadisticaMarca estadistica : estadisticas)
        {
            ArrayList<Entry> yVals1 = new ArrayList<Entry>();
            for (int i = 0; i < estadistica.getMarcas().size(); i++) {
                Marca marca = estadistica.getMarcas().get(i);
                yVals1.add(new Entry(marca.getCarga(), i));
            }
            yVals.add(yVals1);
            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);
            int randomColor = Color.rgb(r,g,b);
            LineDataSet set1 = new LineDataSet(yVals1, estadistica.getEjercicio().getNombre());
            //set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(Color.RED);
            set1.setColor(randomColor);
            set1.setHighLightColor(randomColor);
            set1.setHighLightColor(Color.BLACK);
            set1.setDrawCircleHole(true);
            lineDataSets.add(set1);
        }

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            for(int i=0; i < mChart.getData().getDataSets().size(); i++)
            {
                LineDataSet set = (LineDataSet)mChart.getData().getDataSetByIndex(i);
                set.setYVals(yVals.get(i));
            }
            mChart.getData().setXVals(xVals);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a data object with the datasets
            LineData data = new LineData(xVals, lineDataSets);
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(9f);

            // set data
            mChart.setData(data);
        }
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        mChart.centerViewToAnimated(e.getXIndex(), e.getVal(), mChart.getData().getDataSetByIndex(dataSetIndex).getAxisDependency(), 500);
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

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
            iniciarFlujoPrincipal();
        }
    }

    private void iniciarFlujoPrincipal()
    {
        setGuardaDatos(false);
        FlujoPrincipal flujo = new FlujoPrincipal();
        FitnessTimeApplication.setPosicionActivityPrincipal(Constantes.FRAGMENT_ESTADISTICAS);
        setFlujo(flujo);
        finish();
        startActivity(new Intent(ActivityEstadisticaMarcas.this, ActivityPrincipal.class));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ServicioMarca servicioMarca = new ServicioMarca();
        Rutina rutina = rutinas.get(position);
        List<EstadisticaMarca> estadisticas = servicioMarca.getEstadisticaMarcas(rutina.getId());
        if (estadisticas.size() > 0)
        {
            setData(estadisticas, 20);
            mChart.invalidate();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private int cantidadMayorDeMarcas(List<EstadisticaMarca> estadisticas)
    {
        int max = 0;
        for(EstadisticaMarca estadistica : estadisticas)
        {
            int maxTmp = estadistica.getMarcas().size();
            if(maxTmp > max)
                max = maxTmp;
        }
        return max;
    }
}
