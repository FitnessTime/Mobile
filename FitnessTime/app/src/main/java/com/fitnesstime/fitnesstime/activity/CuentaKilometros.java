package com.fitnesstime.fitnesstime.activity;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.R;

public class CuentaKilometros extends Activity{

    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 30000;
    protected LocationManager locationManager;
    Location currentLocation;
    double distanciaEnMetros = 0;
    TextView distancia;
    boolean inicio = true;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_kilometros);
        distancia = (TextView)findViewById(R.id.distancia);

        inicializarGPS();
    }

    //Inicia el GPS
    private void inicializarGPS()
    {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0,
                new MyLocationListener()
        );
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            if(location == null)
                return;

            if(inicio)
            {
                currentLocation = location;
                inicio = false;
            }

            if(currentLocation.distanceTo(location) > 0.9) {
                distanciaEnMetros = distanciaEnMetros + currentLocation.distanceTo(location);
                currentLocation = location;
            }
            distancia.setText("La distancia es: " + String.valueOf(distanciaEnMetros));

        }

        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(CuentaKilometros.this, "Provider status changed",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String s) {
            Toast.makeText(CuentaKilometros.this,
                    "Provider disabled by the user. GPS turned off",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(CuentaKilometros.this,
                    "Provider enabled by the user. GPS turned on",
                    Toast.LENGTH_LONG).show();
        }

    }

}