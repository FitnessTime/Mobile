package com.fitnesstime.fitnesstime.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.Adapters.TabsFitnessTimeAdapter;
import com.fitnesstime.fitnesstime.DAO.SecurityTokenDAO;
import com.fitnesstime.fitnesstime.Flujos.FlujoCambiarContrasenia;
import com.fitnesstime.fitnesstime.Flujos.FlujoLoggin;
import com.fitnesstime.fitnesstime.Flujos.FlujoModificarUsuario;
import com.fitnesstime.fitnesstime.Modelo.SecurityToken;
import com.fitnesstime.fitnesstime.R;

import java.util.Iterator;


public class ActivityPrincipal extends ActivityFlujo implements ActionBar.TabListener{

    private ViewPager viewPager;
    private TabsFitnessTimeAdapter tabsFitnessTimeAdapter;
    private android.support.v7.app.ActionBar actionBar;
    private String[] tabs = {"Rutinas","Ejercicios","Herramientas","Estadisticas"};
    private String[] mPlanetTitles = {"temporizador"};
    private ListView mDrawerList;
    private String[] titulos;
    private DrawerLayout drawerLayout;
    private ListView NavList;
    private TypedArray NavIcons;
    private int posicionFragment;
    boolean drawerAbierto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        actionBar = getSupportActionBar();
        actionBar.setTitle("Fitness Time");
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView n = (NavigationView) findViewById(R.id.nav_view);
        n.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.nav_ayuda:
                        crearDialogoDeAyuda();
                        menuItem.setCheckable(true);
                    case R.id.nav_cambiar_contrasenia:
                        setFlujo(new FlujoCambiarContrasenia());
                        finish();
                        startActivity(new Intent(ActivityPrincipal.this, ActivityCambiarContrasenia.class));
                    case R.id.nav_modificar_usuario:
                        setFlujo(new FlujoModificarUsuario());
                        finish();
                        startActivity(new Intent(ActivityPrincipal.this, ActivityCambiarContrasenia.class));
                    default:
                        return true;
                }
            }
        });
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                SecurityToken securityTokenSession = new SecurityTokenDAO().getSecurityToken();
                TextView email = (TextView) findViewById(R.id.email);
                TextView usuario = (TextView) findViewById(R.id.usuario);
                email.setText(securityTokenSession.getEmailUsuario());
                usuario.setText(securityTokenSession.getNombreUsuario());
                drawerAbierto = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerAbierto = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_principal, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                finish();
                cerrarSesion();
                return true;
            case android.R.id.home:
                if(!drawerAbierto) {
                    drawerLayout.openDrawer(GravityCompat.START);
                    drawerAbierto = true;
                }
                else {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    drawerAbierto = false;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cerrarSesion()
    {
        //SecurityToken.deleteAll(SecurityToken.class);
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


    private void crearDialogoDeAyuda() {
        new AlertDialog.Builder(this)
                .setMessage("Fitness time, una aplicación para ayudarte con tus rutinas de gimnacio.")
                .setPositiveButton("Ok", null).show();
    }
}
