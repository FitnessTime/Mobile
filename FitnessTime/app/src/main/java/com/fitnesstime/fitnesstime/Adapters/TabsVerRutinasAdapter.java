package com.fitnesstime.fitnesstime.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fitnesstime.fitnesstime.Fragments.FragmentEjerciciosLunes;

/**
 * Created by julian on 21/04/16.
 */
public class TabsVerRutinasAdapter extends FragmentPagerAdapter {


    public TabsVerRutinasAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0) {
        switch (arg0) {
            case 0:
                Fragment ejerciciosLunes = new FragmentEjerciciosLunes();
                return ejerciciosLunes;
            case 1:
                Fragment ejerciciosMartes = new FragmentEjerciciosLunes();
                return ejerciciosMartes;
            case 2:
                Fragment ejerciciosMiercoles = new FragmentEjerciciosLunes();
                return ejerciciosMiercoles;
            case 3:
                Fragment ejerciciosJueves = new FragmentEjerciciosLunes();
                return ejerciciosJueves;
            case 4:
                Fragment ejerciciosViernes = new FragmentEjerciciosLunes();
                return ejerciciosViernes;
            case 5:
                Fragment ejerciciosSabado = new FragmentEjerciciosLunes();
                return ejerciciosSabado;
            default:
                break;
        }
        return null;
    }
    @Override
    public int getCount() {
        return 6;
    }
}
