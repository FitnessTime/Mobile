package com.fitnesstime.fitnesstime.Util;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

/**
 * Created by julian on 20/03/16.
 */
public final class HelperCalcularDistancia {

    private static Double rad(Double x){
        return x*Math.PI/180;
    }

    public static double CalculationByDistance(LatLng StartP, LatLng EndP) {
        Double latitudeOne =  StartP.latitude;
        Double longitudeOne = StartP.longitude;
        Double latitudeTwo = EndP.latitude;
        Double longitudeTwo = EndP.longitude;
        Double radioTerrestre = 6378.137;
        Double dLat  = rad( latitudeTwo - latitudeOne );
        Double dLong = rad( longitudeTwo - longitudeOne );

        Double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(rad(latitudeOne)) * Math.cos(rad(latitudeTwo)) * Math.sin(dLong/2) * Math.sin(dLong/2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return radioTerrestre * c * 1000;
    }
}
