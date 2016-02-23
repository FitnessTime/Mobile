package com.fitnesstime.fitnesstime.Adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitnesstime.fitnesstime.Activities.ActivityLoggin;
import com.fitnesstime.fitnesstime.Activities.ActivityPrincipal;
import com.fitnesstime.fitnesstime.Flujos.FlujoLoggin;
import com.fitnesstime.fitnesstime.Modelo.SecurityToken;
import com.fitnesstime.fitnesstime.R;

public class NavigationAdapter extends BaseAdapter {
    private Activity activity;
    ArrayList<ItemObject> arrayitms;

    public NavigationAdapter(Activity activity,ArrayList<ItemObject>  listarry) {
        super();
        this.activity = activity;
        this.arrayitms=listarry;
    }
    //Retorna objeto Item_objct del array list
    @Override
    public Object getItem(int position) {
        return arrayitms.get(position);
    }
    public int getCount() {
        // TODO Auto-generated method stub
        return arrayitms.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    //Declaramos clase estatica la cual representa a la fila
    public static class Fila
    {
        TextView titulo_itm;
        ImageView icono;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Fila view;
        LayoutInflater inflator = activity.getLayoutInflater();
        if(convertView==null)
        {
            view = new Fila();
            //Creo objeto item y lo obtengo del array
            ItemObject itm=arrayitms.get(position);
            convertView = inflator.inflate(R.layout.itm, null);
            //Titulo
            view.titulo_itm = (TextView) convertView.findViewById(R.id.title_item);
            //Seteo en el campo titulo el nombre correspondiente obtenido del objeto
            view.titulo_itm.setText(itm.getTitulo());
            //Icono
            view.titulo_itm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SecurityToken.deleteAll(SecurityToken.class);
                    activity.startActivity(new Intent(activity, ActivityLoggin.class));
                }
            });
            view.icono = (ImageView) convertView.findViewById(R.id.icon);
            //Seteo el icono
            view.icono.setImageResource(itm.getIcono());

            view.icono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SecurityToken.deleteAll(SecurityToken.class);
                    activity.startActivity(new Intent(activity, ActivityLoggin.class));
                }
            });
            convertView.setTag(view);
        }
        else
        {
            view = (Fila) convertView.getTag();
        }
        return convertView;
    }
}
