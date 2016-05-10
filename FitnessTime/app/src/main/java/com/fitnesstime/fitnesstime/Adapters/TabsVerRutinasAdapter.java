package com.fitnesstime.fitnesstime.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Fragments.FragmentEjerciciosLunes;

/**
 * Created by julian on 21/04/16.
 */
public class TabsVerRutinasAdapter extends FragmentPagerAdapter {

    private ActivityFlujo activity;

    public TabsVerRutinasAdapter(FragmentManager fm, ActivityFlujo activity) {
        super(fm);
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int arg0) {

        switch (arg0) {
            case 0:
                FragmentEjerciciosLunes ejerciciosLunes = new FragmentEjerciciosLunes();
                ejerciciosLunes.setDia("Lunes");
                return ejerciciosLunes;
            case 1:
                FragmentEjerciciosLunes ejerciciosMartes = new FragmentEjerciciosLunes();
                ejerciciosMartes.setDia("Martes");
                return ejerciciosMartes;
            case 2:
                FragmentEjerciciosLunes ejerciciosMiercoles = new FragmentEjerciciosLunes();
                ejerciciosMiercoles.setDia("Miercoles");
                return ejerciciosMiercoles;
            case 3:
                FragmentEjerciciosLunes ejerciciosJueves = new FragmentEjerciciosLunes();
                ejerciciosJueves.setDia("Jueves");
                return ejerciciosJueves;
            case 4:
                FragmentEjerciciosLunes ejerciciosViernes = new FragmentEjerciciosLunes();
                ejerciciosViernes.setDia("Viernes");
                return ejerciciosViernes;
            case 5:
                FragmentEjerciciosLunes ejerciciosSabado = new FragmentEjerciciosLunes();
                ejerciciosSabado.setDia("Sabado");
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
