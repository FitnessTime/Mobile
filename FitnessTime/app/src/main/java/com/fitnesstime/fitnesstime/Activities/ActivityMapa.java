package com.fitnesstime.fitnesstime.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Eventos.EventoCambioPosicionGPS;
import com.fitnesstime.fitnesstime.Eventos.EventoTemporizador;
import com.fitnesstime.fitnesstime.Flujos.FlujoPrincipal;
import com.fitnesstime.fitnesstime.ModelosFlujo.Mapa;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.ServicioMapa;
import com.fitnesstime.fitnesstime.Servicios.ServicioTemporizador;
import com.fitnesstime.fitnesstime.Util.HelperToast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class ActivityMapa extends ActivityFlujo {

    private MarkerOptions markerOrigen;
    private MarkerOptions markerProgreso;
    PolylineOptions lineaProgreso;
    private GoogleMap mapa;
    private Button botonComenzarGPS;
    private Button botonDetenerGPS;
    private LatLng ubicacion;
    private boolean comenzo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mapa sigueme");

        iniciarMapa();
        iniciarMarkers();
        iniciarBotones();
        iniciarLineaProgreso();
        FitnessTimeApplication.getEventBus().register(this);
        iniciarServicePower();
    }

    public void onEvent(EventoCambioPosicionGPS evento)
    {
        ubicacion = new LatLng(evento.getPosicion().getLatitude(), evento.getPosicion().getLongitude());
        markerProgreso.position(ubicacion);
        mapa.clear();
        lineaProgreso.add(ubicacion);
        if(!comenzo)
        {
            markerOrigen.position(ubicacion);
            comenzo = true;
        }
        mapa.addMarker(markerOrigen);
        mapa.addPolyline(lineaProgreso);
        mapa.addMarker(markerProgreso);

        CameraUpdate center= CameraUpdateFactory.newLatLng(ubicacion);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(18);
        mapa.moveCamera(center);
        mapa.animateCamera(zoom);
        ((Mapa)flujo.getEntidad()).setLinea(lineaProgreso);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                guardarDatos();
                crearDialogoDeConfirmacion();
                return true;
            case R.id.share_item:
                sacarCapturaDePantalla();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        if(esElPrimero())
        {
            guardarDatos();
            crearDialogoDeConfirmacion();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mapa, menu);
        return true;
    }

    private void sacarCapturaDePantalla()
    {
        GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {
            Bitmap bitmap;

            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                bitmap = snapshot;
                try {
                    Calendar tiempoActual = Calendar.getInstance();
                    FileOutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/mapa_recorrido_"+tiempoActual +".png");
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                    HelperToast.generarToast(getApplicationContext(),"Imagen recorrido descargada.");
                    Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/mapa_recorrido_"+tiempoActual +".png"));
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out my app.");
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    shareIntent.setType("image/*");
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(shareIntent, "share via"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        mapa.snapshot(callback);
    }


    // Crea el dialogo de confirmacion.
    private void crearDialogoDeConfirmacion()
    {
        new AlertDialog.Builder(this)
                .setMessage("¿Desea salir de la función sigueme?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        iniciarFlujoPrincipal();
                    }})
                .setNegativeButton("Cancelar", null).show();
    }

    private void iniciarFlujoPrincipal()
    {
        FlujoPrincipal flujo = new FlujoPrincipal();
        flujo.setPosicionFragment(Constantes.FRAGMENT_HERRAMIENTA);
        setFlujo(flujo);
        finish();
        startActivity(new Intent(ActivityMapa.this, ActivityPrincipal.class));
    }

    private void iniciarMarkers()
    {
        markerOrigen = new MarkerOptions()
                .title("Inicio").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        markerProgreso = new MarkerOptions()
                .title("Actual").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    }

    // Se utiliza para mantener el servicio corriendo en background al apagar la pantalla del celular.
    private void iniciarServicePower()
    {
        PowerManager pm = (PowerManager)getApplicationContext().getSystemService(getApplicationContext().POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "fitnesstimelock");
        wl.acquire();
    }

    private void iniciarBotones()
    {
        botonComenzarGPS = (Button) findViewById(R.id.boton_comenzar_gps);
        botonComenzarGPS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intentService = new Intent(getBaseContext(), ServicioMapa.class);
                startService(intentService);
                botonComenzarGPS.setVisibility(View.INVISIBLE);
                botonDetenerGPS.setVisibility(View.VISIBLE);
            }
        });
        botonDetenerGPS = (Button) findViewById(R.id.boton_detener_gps);
        botonDetenerGPS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stopService(new Intent(getBaseContext(), ServicioMapa.class));
                mapa.clear();
                botonComenzarGPS.setVisibility(View.VISIBLE);
                botonDetenerGPS.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void iniciarLineaProgreso()
    {
        PolylineOptions linea = ((Mapa)flujo.getEntidad()).getLinea();
        lineaProgreso = linea != null ?
                linea : (new PolylineOptions()
                .width(10)
                .color(Color.BLUE));
    }

    private void iniciarMapa()
    {
        mapa = ((MapFragment) getFragmentManager().
                findFragmentById(R.id.map)).getMap();
    }

}
