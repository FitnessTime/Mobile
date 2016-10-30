package com.fitnesstime.fitnesstime.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Eventos.EventoCambioDistancia;
import com.fitnesstime.fitnesstime.Flujos.FlujoPrincipal;
import com.fitnesstime.fitnesstime.ModelosFlujo.CuentaDistancia;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.ServicioDistanciaRecorrida;
import com.fitnesstime.fitnesstime.Servicios.ServicioMapa;
import com.fitnesstime.fitnesstime.Util.HelperCalcularDistancia;
import com.google.android.gms.maps.model.LatLng;

public class ActivityCuentaDistancia extends ActivityFlujo {

    TextView distancia;
    Button comenzarContadorRecorrido;
    Button detenerContadorRecorrido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cuenta_distancia);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cuenta kilometros");

        FitnessTimeApplication.getEventBus().register(this);

        distancia = (TextView) findViewById(R.id.text_distancia_recorrida);
        iniciarBotones();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cuenta_distancia, menu);
        return true;
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

    @Override
    public void onBackPressed()
    {
        if(esElPrimero())
        {
            crearDialogoDeConfirmacion();
        }
    }

    public void onEvent(EventoCambioDistancia evento)
    {
        CuentaDistancia entidadCuentaDistancia = (CuentaDistancia)flujo.getEntidad();
        if(entidadCuentaDistancia.getPosicion()==null)
        {
            entidadCuentaDistancia.setPosicion(evento.getPosicion());
        }
        Location locacionActual = evento.getPosicion();
        Location locacionAnterior = entidadCuentaDistancia.getPosicion();
        double distanciaRecorrida = entidadCuentaDistancia.getDistanciaAnterior() + HelperCalcularDistancia.CalculationByDistance(new LatLng(locacionAnterior.getLatitude(), locacionAnterior.getLongitude()), new LatLng(locacionActual.getLatitude(), locacionActual.getLongitude()));
        entidadCuentaDistancia.setDistanciaAnterior(distanciaRecorrida);
        entidadCuentaDistancia.setPosicion(evento.getPosicion());
        this.distancia.setText(String.valueOf(String.valueOf(distanciaRecorrida).toCharArray(),0,4) + " KM");
    }

    private void iniciarFlujoPrincipal()
    {
        FlujoPrincipal flujo = new FlujoPrincipal();
        FitnessTimeApplication.setPosicionActivityPrincipal(Constantes.FRAGMENT_HERRAMIENTA);
        setFlujo(flujo);
        stopService(new Intent(getBaseContext(), ServicioDistanciaRecorrida.class));
        finish();
        startActivity(new Intent(ActivityCuentaDistancia.this, ActivityPrincipal.class));
    }

    // Crea el dialogo de confirmacion.
    private void crearDialogoDeConfirmacion()
    {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("¿Desea salir del cuenta kilomentros?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        iniciarFlujoPrincipal();
                    }})
                .setNegativeButton("Cancelar", null).show();

        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#F57C00"));
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#F57C00"));
    }

    private void iniciarBotones()
    {
        comenzarContadorRecorrido = (Button) findViewById(R.id.boton_comenzar_recorrido);
        comenzarContadorRecorrido.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intentService = new Intent(getBaseContext(), ServicioDistanciaRecorrida.class);
                startService(intentService);
                comenzarContadorRecorrido.setVisibility(View.INVISIBLE);
                detenerContadorRecorrido.setVisibility(View.VISIBLE);
            }
        });
        detenerContadorRecorrido = (Button) findViewById(R.id.boton_detener_recorrido);
        detenerContadorRecorrido.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stopService(new Intent(getBaseContext(), ServicioDistanciaRecorrida.class));
                comenzarContadorRecorrido.setVisibility(View.VISIBLE);
                detenerContadorRecorrido.setVisibility(View.INVISIBLE);
            }
        });
    }
}
