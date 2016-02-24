package com.fitnesstime.fitnesstime.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fitnesstime.fitnesstime.Fragments.EjerciciosFragment;
import com.fitnesstime.fitnesstime.Fragments.EstadisticasFragment;
import com.fitnesstime.fitnesstime.Fragments.HerramientasFragment;
import com.fitnesstime.fitnesstime.Fragments.RutinasFragment;

/**
 * Created by julian on 21/02/16.
 */
public class TabsFitnessTimeAdapter extends FragmentPagerAdapter {


    public TabsFitnessTimeAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int arg0) {
        switch (arg0) {
            case 0:
                Fragment rutinasFragment = new RutinasFragment();
                return rutinasFragment;
            case 1:
                Fragment ejerciciosFragment = new EjerciciosFragment();
                return ejerciciosFragment;
            case 2:
                Fragment herramientasFragment = new HerramientasFragment();
                return herramientasFragment;
            case 3:
                Fragment estadisticasFragment = new EstadisticasFragment();
                return estadisticasFragment;
            default:
                break;
        }
        return null;
    }
    @Override
    public int getCount() {
        return 4;
    }
}
