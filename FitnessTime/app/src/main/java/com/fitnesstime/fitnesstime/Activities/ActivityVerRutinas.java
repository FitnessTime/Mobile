package com.fitnesstime.fitnesstime.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.fitnesstime.fitnesstime.Adapters.TabsVerRutinasAdapter;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Flujos.FlujoPrincipal;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.ServicioEjercicio;


public class ActivityVerRutinas extends ActivityFlujo implements ActionBar.TabListener{

    private Rutina rutina;
    private ViewPager viewPager;
    private TabsVerRutinasAdapter tabsFitnessTimeAdapter;
    private android.support.v7.app.ActionBar actionBar;
    private String[] tabs = {"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
    private int posicionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_rutinas);

        rutina = (Rutina) flujo.getEntidad();
        actionBar = getSupportActionBar();
        actionBar.setTitle("Rutina: " + rutina.getDescripcion());
        actionBar.setSubtitle(rutina.getInicio().toString() + " - " + rutina.getFin().toString());
        actionBar.setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewPager_ver_rutinas);
        tabsFitnessTimeAdapter = new TabsVerRutinasAdapter(getSupportFragmentManager(), this);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        viewPager.setAdapter(tabsFitnessTimeAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                posicionFragment = position;
                actionBar.setSelectedNavigationItem(position);
                viewPager.getAdapter().notifyDataSetChanged();
                //if(position==1)
                //    animateFab(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Adding Tabs
        for (String tabName : tabs) {
            ActionBar.Tab tab =  actionBar.newTab().setText(tabName);
            if(new ServicioEjercicio().tieneEjerciciosElDia(rutina.getId(),tabName))
                tab.setIcon(R.mipmap.ic_notification);
            tab.setTabListener(this);
            actionBar.addTab(tab);
        }
        actionBar.setSelectedNavigationItem(FitnessTimeApplication.getPosicionFragmentVerEjercicios());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_ver_rutinas, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                iniciarFlujoPrincipal();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        iniciarFlujoPrincipal();
    }

    @Override
    public boolean esElPrimero()
    {
        return true;
    }

    @Override
    public void guardarDatos() {
        FitnessTimeApplication.setPosicionActivityPrincipal(posicionFragment);
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

    private void iniciarFlujoPrincipal()
    {
        FitnessTimeApplication.setPosicionFragmentVerEjercicios(0);
        setGuardaDatos(false);
        FlujoPrincipal flujo = new FlujoPrincipal();
        FitnessTimeApplication.setPosicionActivityPrincipal(Constantes.FRAGMENT_RUTINA);
        setFlujo(flujo);
        finish();
        startActivity(new Intent(ActivityVerRutinas.this, ActivityPrincipal.class));
    }

/*
    protected void animateFab(final int position) {
        fab = (FloatingActionButton)findViewById(R.id.boton_agregar_rutina);
        fab.clearAnimation();
        // Scale down animation
        ScaleAnimation shrink =  new ScaleAnimation(1f, 0.2f, 1f, 0.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        shrink.setDuration(150);     // animation duration in milliseconds
        shrink.setInterpolator(new DecelerateInterpolator());
        shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Change FAB color and icon

                // Scale up animation
                ScaleAnimation expand = new ScaleAnimation(0.2f, 1f, 0.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                expand.setDuration(300);     // animation duration in milliseconds
                expand.setInterpolator(new AccelerateInterpolator());
                fab.startAnimation(expand);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fab.startAnimation(shrink);
    }
    */
}
