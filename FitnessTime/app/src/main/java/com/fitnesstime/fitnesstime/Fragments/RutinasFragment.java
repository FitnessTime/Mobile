package com.fitnesstime.fitnesstime.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fitnesstime.fitnesstime.Activities.ActivityPrincipal;
import com.fitnesstime.fitnesstime.Activities.ActivityPrincipalRutina;
import com.fitnesstime.fitnesstime.Adapters.RutinasAdapter;
import com.fitnesstime.fitnesstime.DAO.RutinaDAO;
import com.fitnesstime.fitnesstime.Flujos.FlujoRutinas;
import com.fitnesstime.fitnesstime.Modelo.SecurityToken;
import com.fitnesstime.fitnesstime.Modelo.Rutina;
import com.fitnesstime.fitnesstime.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

public class RutinasFragment extends Fragment {

    private View rootView;
    private RutinasAdapter adapter;
    private SwipeRefreshLayout swipeActualizacion;
    private RecyclerView rvRutinas;
    private List<Rutina> rutinas;
    private List<Rutina> rutinasA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_rutinas, container, false);

        rvRutinas = (RecyclerView) rootView.findViewById(R.id.recycler_rutinas);

        rutinas = new RutinaDAO().getRutinas();
        adapter = new RutinasAdapter(rutinas, getActivity(), getContext());
        rvRutinas.setAdapter(adapter);
        rvRutinas.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.boton_agregar_rutina);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityPrincipal) getActivity()).setFlujo(new FlujoRutinas());
                ((ActivityPrincipal) getActivity()).finish();
                startActivity(new Intent(((ActivityPrincipal) getActivity()), ActivityPrincipalRutina.class));
            }
        });
        registerForContextMenu(rvRutinas);
        iniciarSwipe();
        iniciarAccionDeActualizacion();
        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Context Menu");
        menu.add(0, v.getId(), 0, "Action 1");
        menu.add(0, v.getId(), 0, "Action 2");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle()=="Action 1"){function1(item.getItemId());}
        else if(item.getTitle()=="Action 2"){function2(item.getItemId());}
        else {return false;}
        return true;
    }

    public void function1(int id){
    }
    public void function2(int id){
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
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        new RutinaDAO().borrar(rutinas.get(viewHolder.getAdapterPosition()));
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
