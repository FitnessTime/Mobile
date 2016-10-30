package com.fitnesstime.fitnesstime.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.media.MediaMuxer;
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
import com.fitnesstime.fitnesstime.Eventos.EventoCambioDistancia;
import com.fitnesstime.fitnesstime.Eventos.EventoCambioPosicionGPS;
import com.fitnesstime.fitnesstime.Eventos.EventoTemporizador;
import com.fitnesstime.fitnesstime.Flujos.FlujoMapa;
import com.fitnesstime.fitnesstime.Flujos.FlujoPrincipal;
import com.fitnesstime.fitnesstime.ModelosFlujo.CuentaDistancia;
import com.fitnesstime.fitnesstime.ModelosFlujo.Mapa;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.ServicioDistanciaRecorrida;
import com.fitnesstime.fitnesstime.Servicios.ServicioMapa;
import com.fitnesstime.fitnesstime.Servicios.ServicioTemporizador;
import com.fitnesstime.fitnesstime.Util.HelperCalcularDistancia;
import com.fitnesstime.fitnesstime.Util.HelperToast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityMapa extends ActivityFlujo {

    private MarkerOptions markerOrigen;
    private MarkerOptions markerProgreso;
    PolylineOptions lineaProgreso;
    private GoogleMap mapa;
    private Button botonComenzarGPS;
    private Button botonDetenerGPS;
    private LatLng ubicacion;
    private boolean comenzo = false;
    private TextView distancia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mapa sigueme");

        distancia = (TextView) findViewById(R.id.text_distancia_recorrida);
        iniciarMapa();
        iniciarMarkers();
        iniciarBotones();
        iniciarLineaProgreso();
        FitnessTimeApplication.getEventBus().register(this);
        iniciarServicePower();
    }

    public void onEvent(EventoCambioDistancia evento)
    {
        Mapa mapa = (Mapa)flujo.getEntidad();
        if(mapa.getPosicion()==null)
        {
            mapa.setPosicion(evento.getPosicion());
        }
        Location locacionActual = evento.getPosicion();
        Location locacionAnterior = mapa.getPosicion();
        double distanciaRecorrida = mapa.getDistanciaAnterior() + HelperCalcularDistancia.CalculationByDistance(new LatLng(locacionAnterior.getLatitude(), locacionAnterior.getLongitude()), new LatLng(locacionActual.getLatitude(), locacionActual.getLongitude()));
        mapa.setDistanciaAnterior(distanciaRecorrida);
        mapa.setPosicion(evento.getPosicion());
        this.distancia.setText(String.valueOf(String.valueOf(distanciaRecorrida).toCharArray(), 0, 4) + " KM");
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

        Mapa mapa = ((Mapa)flujo.getEntidad());
        if(mapa != null)
        {
            mapa.setLinea(lineaProgreso);
        }
        else
        {
            setFlujo(new FlujoMapa());
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                guardarDatos();
                crearDialogoDeConfirmacion();
                return true;
            case R.id.share_item:
                List<LatLng> points = lineaProgreso.getPoints(); // route is instance of PolylineOptions

                LatLngBounds.Builder bc = new LatLngBounds.Builder();

                for (LatLng point : points) {
                    bc.include(point);
                }

                mapa.moveCamera(CameraUpdateFactory.newLatLngBounds(bc.build(), 50));
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
                    String tiempoActual = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                    String directorioDescarga = "/mapa_recorrido_" + tiempoActual.toString() + ".png";
                    FileOutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory() + directorioDescarga);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                    HelperToast.generarToast(getApplicationContext(),"Imagen recorrido descargada.");
                    Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + directorioDescarga));
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Recorrido mapa.");
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
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("¿Desea salir de la función sigueme?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        iniciarFlujoPrincipal();
                    }})
                .setNegativeButton("Cancelar", null).show();

        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#F57C00"));
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#F57C00"));

    }

    private void iniciarFlujoPrincipal()
    {
        FlujoPrincipal flujo = new FlujoPrincipal();
        FitnessTimeApplication.setPosicionActivityPrincipal(Constantes.FRAGMENT_HERRAMIENTA);
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
                Intent intentServiceMapa = new Intent(getBaseContext(), ServicioMapa.class);
                startService(intentServiceMapa);
                Intent intentServiceDstancia = new Intent(getBaseContext(), ServicioDistanciaRecorrida.class);
                startService(intentServiceDstancia);
                mapa.clear();
                botonComenzarGPS.setVisibility(View.INVISIBLE);
                botonDetenerGPS.setVisibility(View.VISIBLE);
            }
        });
        botonDetenerGPS = (Button) findViewById(R.id.boton_detener_gps);
        botonDetenerGPS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stopService(new Intent(getBaseContext(), ServicioMapa.class));
                stopService(new Intent(getBaseContext(), ServicioDistanciaRecorrida.class));
                //mapa.clear();
                botonComenzarGPS.setVisibility(View.VISIBLE);
                botonDetenerGPS.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void iniciarLineaProgreso()
    {
        Mapa mapa = ((Mapa)flujo.getEntidad());
        if(mapa==null)
            setFlujo(new FlujoMapa());
        PolylineOptions linea = mapa.getLinea();
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
