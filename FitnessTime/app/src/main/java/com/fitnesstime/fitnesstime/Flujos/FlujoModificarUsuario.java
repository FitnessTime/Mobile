package com.fitnesstime.fitnesstime.Flujos;

import com.fitnesstime.fitnesstime.Activities.ActivityModificarUsuario;
import com.fitnesstime.fitnesstime.Modelo.Usuario;

import java.util.ArrayList;

/**
 * Created by julian on 29/03/16.
 */
public class FlujoModificarUsuario extends Flujo<Usuario> {

    public FlujoModificarUsuario() {
        super();
        activitys = new ArrayList<>();

        activitys.add(ActivityModificarUsuario.class);
    }

    @Override
    public Usuario getEntidad() {
        return entidad;
    }

    @Override
    public Usuario crearEntidad() {
        return new Usuario();
    }
}

