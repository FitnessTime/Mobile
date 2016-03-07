package com.fitnesstime.fitnesstime.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fitnesstime.fitnesstime.Activities.ActivityPrincipal;
import com.fitnesstime.fitnesstime.Activities.ActivityPrincipalRutina;
import com.fitnesstime.fitnesstime.Adapters.HerramientaAdapter;
import com.fitnesstime.fitnesstime.Adapters.ItemHerramienta;
import com.fitnesstime.fitnesstime.Adapters.ItemRutina;
import com.fitnesstime.fitnesstime.Adapters.RutinasAdapter;
import com.fitnesstime.fitnesstime.Decorators.DividerItemDecoration;
import com.fitnesstime.fitnesstime.Flujos.FlujoRutinas;
import com.fitnesstime.fitnesstime.R;
import com.fitnesstime.fitnesstime.Util.SwipeListViewTouchListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RutinasFragment extends Fragment {

    private View rootView;
    private RutinasAdapter adapter;
    private SwipeRefreshLayout swipeActualizacion;
    private RecyclerView rvRutinas;
    private List<ItemRutina> rutinas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_rutinas, container, false);

        rvRutinas = (RecyclerView) rootView.findViewById(R.id.recycler_rutinas);
        rutinas = new ArrayList<ItemRutina>();
        rutinas.add(new ItemRutina(new Date(), new Date(), "Rutina inicial"));
        rutinas.add(new ItemRutina(new Date(), new Date(), "Rutina media"));
        rutinas.add(new ItemRutina(new Date(), new Date(), "Rutina avanzada"));
        rutinas.add(new ItemRutina(new Date(), new Date(), "Rutina 1"));
        rutinas.add(new ItemRutina(new Date(), new Date(), "Rutina 2"));

        adapter = new RutinasAdapter(rutinas, getActivity(), getContext());
        rvRutinas.setAdapter(adapter);
        rvRutinas.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        rvRutinas.addItemDecoration(new RecyclerView.ItemDecoration() {

            private int textSize = 35;
            private int groupSpacing = 100;
            private int itemsInGroup = rutinas.size();

            private Paint paint = new Paint();
            {
                paint.setTextSize(textSize);
            }

            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                    View view = parent.getChildAt(0);
                        c.drawText("Rutinas de carga", view.getLeft(),
                                view.getTop() - groupSpacing / 2 + textSize / 3, paint);
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) % itemsInGroup == 0) {
                    outRect.set(0, groupSpacing, 0, 0);
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.boton_agregar_rutina);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityPrincipal) getActivity()).setFlujo(new FlujoRutinas());
                ((ActivityPrincipal) getActivity()).finish();
                startActivity(new Intent(((ActivityPrincipal) getActivity()), ActivityPrincipalRutina.class));
            }
        });


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
        new AlertDialog.Builder(getActivity())
                .setTitle("Rutinas")
                .setMessage("Desea eliminar la rutina?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        rutinas.remove(viewHolder.getAdapterPosition());
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        adapter.notifyDataSetChanged();
                    }
                }).show();
    }
}
