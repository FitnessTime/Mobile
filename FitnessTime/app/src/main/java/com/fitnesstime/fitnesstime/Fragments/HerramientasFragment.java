package com.fitnesstime.fitnesstime.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnesstime.fitnesstime.Activities.ActivityPrincipal;
import com.fitnesstime.fitnesstime.Adapters.HerramientaAdapter;
import com.fitnesstime.fitnesstime.Adapters.ItemHerramienta;
import com.fitnesstime.fitnesstime.R;

import java.util.ArrayList;
import java.util.List;

public class HerramientasFragment extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_herramientas, container, false);


        RecyclerView rvHerramientas = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        List<ItemHerramienta> herramientas = new ArrayList<ItemHerramienta>();
        herramientas.add(new ItemHerramienta("Temporizador", "ic_temporizador"));
        herramientas.add(new ItemHerramienta("Cuenta kilometros", "ic_action_star"));

        // Set adapter.
        HerramientaAdapter adapter = new HerramientaAdapter(herramientas,(ActivityPrincipal) getActivity(), getContext());
        rvHerramientas.setAdapter(adapter);
        rvHerramientas.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        return rootView;
    }

    private void refreshContent(){
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
