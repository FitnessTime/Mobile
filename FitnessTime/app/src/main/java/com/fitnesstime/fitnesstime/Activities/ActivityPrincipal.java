package com.fitnesstime.fitnesstime.Activities;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Adapters.TabsFitnessTimeAdapter;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Eventos.EventoActualizar;
import com.fitnesstime.fitnesstime.Eventos.EventoCerrarSesion;
import com.fitnesstime.fitnesstime.Eventos.EventoGuardarRutina;
import com.fitnesstime.fitnesstime.Eventos.EventoSincronizarRutinas;
import com.fitnesstime.fitnesstime.Flujos.FlujoCambiarContrasenia;
import com.fitnesstime.fitnesstime.Flujos.FlujoLoggin;
import com.fitnesstime.fitnesstime.Flujos.FlujoModificarUsuario;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.Flujos.FlujoRegistro;
import com.fitnesstime.fitnesstime.ModelosFlujo.Registro;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.Network;
import com.fitnesstime.fitnesstime.Servicios.ServicioPodometro;
import com.fitnesstime.fitnesstime.Servicios.ServicioSecurityToken;
import com.fitnesstime.fitnesstime.Tasks.CerrarSesionTask;
import com.fitnesstime.fitnesstime.Tasks.SincronizacionRutinasTask;
import com.fitnesstime.fitnesstime.Util.HelperSnackbar;
import com.fitnesstime.fitnesstime.Util.HelperToast;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import de.hdodenhof.circleimageview.CircleImageView;


public class ActivityPrincipal extends ActivityFlujo implements ActionBar.TabListener {

    private ViewPager viewPager;
    private TabsFitnessTimeAdapter tabsFitnessTimeAdapter;
    private android.support.v7.app.ActionBar actionBar;
    private String[] tabs = {"Rutinas", "Ejercicios", "Herramientas", "Estadisticas"};
    private String[] mPlanetTitles = {"temporizador"};
    private ListView mDrawerList;
    private String[] titulos;
    private DrawerLayout drawerLayout;
    private ListView NavList;
    private TypedArray NavIcons;
    private int posicionFragment;
    boolean drawerAbierto = false;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Fitness Time");
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(!FitnessTimeApplication.getEventBus().isRegistered(this))
            FitnessTimeApplication.getEventBus().register(this);
        if(FitnessTimeApplication.isEjecutandoTarea())
        {
            FitnessTimeApplication.desactivarProgressDialog();
            FitnessTimeApplication.activarProgressDialog(this, FitnessTimeApplication.getMensajeTareaEnEjecucion());
        }
        iniciarTabs();
        iniciarDrawerLayout();
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
            case R.id.action_sincronizar:
                sincronizar();
                return true;
            case android.R.id.home:
                if (!drawerAbierto) {
                    drawerLayout.openDrawer(GravityCompat.START);
                    drawerAbierto = true;
                } else {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    drawerAbierto = false;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cerrarSesion() {
        FitnessTimeApplication.setEjecutandoTarea(true);
        FitnessTimeApplication.activarProgressDialog(this, "Cerrando sesión...");
        new CerrarSesionTask(this).execute();
    }

    @Override
    public boolean esElPrimero() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                imageView = (ImageView) findViewById(R.id.imgView);
                imageView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));
                new ServicioSecurityToken().guardarFotoPerfil(imgDecodableString);
            } else {
                HelperToast.generarToast(this, "No se seleccionó foto de perfil.");
            }
        } catch (Exception e) {
            HelperToast.generarToast(this, "Error, intente nuevamente.");
        }
    }

    public void onEvent(EventoCerrarSesion evento)
    {
        FitnessTimeApplication.desactivarProgressDialog();
        FitnessTimeApplication.setEjecutandoTarea(false);
        if(evento.getCode()==200)
        {
            stopService(new Intent(getBaseContext(), ServicioPodometro.class));
            new ServicioSecurityToken().borrar(FitnessTimeApplication.getSession());
            setFlujo(new FlujoLoggin());
            startActivity(new Intent(ActivityPrincipal.this, ActivityLoggin.class));
        }
        HelperToast.generarToast(this, evento.getMensaje());
    }

    private void iniciarTabs()
    {
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
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Adding Tabs
        for (String tabName : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tabName)
                    .setTabListener(this));
        }
        actionBar.setSelectedNavigationItem(FitnessTimeApplication.getPosicionActivityPrincipal());
    }

    private void iniciarDrawerLayout()
    {
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
                        return true;
                    case R.id.nav_cambiar_contrasenia:
                        setFlujo(new FlujoCambiarContrasenia());
                        finish();
                        startActivity(new Intent(ActivityPrincipal.this, ActivityCambiarContrasenia.class));
                        menuItem.setCheckable(true);
                        return true;
                    case R.id.nav_modificar_usuario:
                        SecurityToken st = FitnessTimeApplication.getSession();
                        Registro registro = new Registro(st.getNombreUsuario(), st.getEmailUsuario(), st.getFechaNacimientoUsuario(), Integer.parseInt(st.getPesoUsuario()), Integer.parseInt(st.getCantidadMinimaPasosUsuario()));
                        FlujoRegistro fregistro = new FlujoRegistro();
                        fregistro.setEntidad(registro);
                        fregistro.setModoEdicion(true);
                        setFlujo(fregistro);
                        finish();
                        startActivity(new Intent(ActivityPrincipal.this, ActivityRegistroDatosPersonales.class));
                        menuItem.setCheckable(true);
                        return true;
                    case R.id.nav_log_out:
                        crearDialogoDeConfirmacionParaCerrarSesion();
                        menuItem.setCheckable(true);
                        return true;
                    case R.id.nav_subir_foto:
                        loadImagefromGallery();
                        menuItem.setCheckable(true);
                        return true;
                    default:
                        return true;
                }
            }
        });

        final ActionBarDrawerToggle toolbarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                null,
                R.string.drawer_open,
                R.string.drawer_close
        ) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                drawerAbierto = false;
            }

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
                SecurityToken st = new ServicioSecurityToken().getAll().get(0);
                if(!st.getImagenPerfil().isEmpty())
                {
                    imageView = (ImageView) findViewById(R.id.imgView);
                    imageView.setImageBitmap(BitmapFactory
                            .decodeFile(st.getImagenPerfil()));
                }
                SecurityToken securityTokenSession = FitnessTimeApplication.getSession();
                TextView email = (TextView) findViewById(R.id.email);
                TextView usuario = (TextView) findViewById(R.id.usuario);
                email.setText(securityTokenSession.getEmailUsuario());
                usuario.setText(securityTokenSession.getNombreUsuario());
                drawerAbierto = true;
            }
        };
        drawerLayout.setDrawerListener(toolbarDrawerToggle);
        toolbarDrawerToggle.syncState();

        ValueAnimator anim = ValueAnimator.ofFloat(1, 0);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                toolbarDrawerToggle.onDrawerSlide(drawerLayout, slideOffset);
            }
        });
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(500);
        anim.start();
    }

    private void crearDialogoDeAyuda() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("Fitness time, una aplicación para ayudarte con tus rutinas de gimnacio. Para mas información ingrese en www.fitnesstime.herokuapp.com")
                .setPositiveButton("Ok", null).show();

        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#F57C00"));
    }

    // Crea el dialogo de confirmacion.
    private void crearDialogoDeConfirmacionParaCerrarSesion()
    {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("¿Dese cerrar sesión?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        cerrarSesion();
                    }
                })
                .setNegativeButton("Cancelar", null).show();

        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#F57C00"));
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#F57C00"));
    }

    public void loadImagefromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    private void sincronizar()
    {
        if(Network.isOnline(this)) {
            FitnessTimeApplication.setEjecutandoTarea(true);
            FitnessTimeApplication.activarProgressDialog(this, "Sincronizando...");
            new SincronizacionRutinasTask(this).execute();
        }
        else
        {
            HelperSnackbar.generarSnackbar(viewPager, "No tiene conexión a internet");
        }
    }
}
