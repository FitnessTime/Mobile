package com.fitnesstime.fitnesstime.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Activities.ActivityPrincipal;
import com.fitnesstime.fitnesstime.Activities.ActivityPrincipalRutina;
import com.fitnesstime.fitnesstime.Adapters.RutinasAdapter;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Eventos.EventoActualizar;
import com.fitnesstime.fitnesstime.Flujos.FlujoRutinas;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Servicios.ServicioRutina;
import com.fitnesstime.fitnesstime.Util.HelperToast;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.List;


public class RutinasFragment extends Fragment {

    private View rootView;
    private RutinasAdapter adapter;
    private SwipeRefreshLayout swipeActualizacion;
    private RecyclerView rvRutinas;
    private List<Rutina> rutinas;
    private FloatingActionsMenu fabMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_rutinas, container, false);

        rvRutinas = (RecyclerView) rootView.findViewById(R.id.recycler_rutinas);

        rutinas = new ServicioRutina().getAll();
        adapter = new RutinasAdapter(rutinas, getActivity(), getContext());
        rvRutinas.setAdapter(adapter);
        rvRutinas.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        fabMenu = (FloatingActionsMenu) rootView.findViewById(R.id.fab_menu);
        final FrameLayout frameLayout = (FrameLayout) rootView.findViewById(R.id.frame_layout);

        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {

                rootView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        fabMenu.collapse();
                        return true;
                    }
                });
            }

            @Override
            public void onMenuCollapsed() {
                rootView.setOnTouchListener(null);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_agregar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityPrincipal) getActivity()).setFlujo(new FlujoRutinas());
                ((ActivityPrincipal) getActivity()).finish();
                startActivity(new Intent(((ActivityPrincipal) getActivity()), ActivityPrincipalRutina.class));
            }
        });
        animateFab(0);
        iniciarSwipe();
        iniciarAccionDeActualizacion();
        return rootView;
    }

    // Accion de actualizar al deslizar el dedo hacia abajo por la pantalla
    private void iniciarAccionDeActualizacion()
    {
        swipeActualizacion = (SwipeRefreshLayout) rootView.findViewById(R.id.actualizacion_rutinas);
        swipeActualizacion.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getActivity().recreate();
                adapter.notifyDataSetChanged();
                swipeActualizacion.setRefreshing(false);
            }
        });
    }

    // Accion al deslizar el dedo hacia la derecha sobre una card rutina.
    private void iniciarSwipe()
    {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(
                            final RecyclerView recyclerView,
                            final RecyclerView.ViewHolder viewHolder,
                            final RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(
                            final RecyclerView.ViewHolder viewHolder,
                            final int swipeDir) {
                        crearDialogoDeConfirmacion(viewHolder);
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvRutinas);
    }

    // Crea el dialogo de confirmacion.
    private void crearDialogoDeConfirmacion(final RecyclerView.ViewHolder viewHolder)
    {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setMessage("¿Desea eliminar la rutina?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        new ServicioRutina().eliminar(rutinas.get(viewHolder.getAdapterPosition()));
                        HelperToast.generarToast(getContext(),"Rutina eliminada.");
                        getActivity().recreate();
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        adapter.notifyDataSetChanged();
                    }
                }).show();

        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#F57C00"));
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#F57C00"));
    }

    protected void animateFab(final int position) {

        fabMenu.clearAnimation();
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
                fabMenu.startAnimation(expand);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fabMenu.startAnimation(shrink);
    }
}
