package com.fitnesstime.fitnesstime.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.fitnesstime.fitnesstime.Adapters.TabsFitnessTimeAdapter;
import com.fitnesstime.fitnesstime.Flujos.FlujoLoggin;
import com.fitnesstime.fitnesstime.Modelo.SecurityToken;
import com.fitnesstime.fitnesstime.R;

import java.util.ArrayList;


public class ActivityPrincipal extends ActivityFlujo implements ActionBar.TabListener{

    private ViewPager viewPager;
    private TabsFitnessTimeAdapter tabsFitnessTimeAdapter;
    private android.support.v7.app.ActionBar actionBar;
    private String[] tabs = {"Rutinas","Ejercicios","Herramientas","Estadisticas"};
    private String[] mPlanetTitles = {"temporizador"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] titulos;
    private DrawerLayout NavDrawerLayout;
    private ListView NavList;
    private TypedArray NavIcons;
    private int posicionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Fitness Time");
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        tabsFitnessTimeAdapter = new TabsFitnessTimeAdapter(getSupportFragmentManager());
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        viewPager.setAdapter(tabsFitnessTimeAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                posicionFragment = position;
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        // Adding Tabs
        for (String tabName : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tabName)
                    .setTabListener(this));
        }
        actionBar.setSelectedNavigationItem(flujo.getPosicionFragment());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                finish();
                cerrarSesion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cerrarSesion()
    {
        SecurityToken.deleteAll(SecurityToken.class);
        setFlujo(new FlujoLoggin());
        startActivity(new Intent(ActivityPrincipal.this, ActivityLoggin.class));
    }

    @Override
    public boolean esElPrimero()
    {
        return true;
    }

    @Override
    public void guardarDatos() {
        flujo.setPosicionFragment(posicionFragment);
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
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

}
