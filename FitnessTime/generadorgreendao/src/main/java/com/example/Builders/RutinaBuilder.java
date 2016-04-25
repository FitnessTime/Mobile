package com.example.Builders;

import com.example.Builder;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;

/**
 * Created by jskalic on 22/04/2016.
 */
public class RutinaBuilder extends Builder {
    public static Entity build() {

        Entity ejercicio = schema.addEntity("Ejercicio");
        ejercicio.addIdProperty();
        ejercicio.implementsInterface("DomainEntity");
        ejercicio.addStringProperty("nombre").notNull();
        ejercicio.addStringProperty("diaDeLaSemana");
        ejercicio.addIntProperty("series");
        ejercicio.addIntProperty("repeticiones");
        ejercicio.addIntProperty("tiempoActivo");
        ejercicio.addIntProperty("tiempoDescanso");
        ejercicio.addBooleanProperty("esDeCarga").notNull();
        ejercicio.setHasKeepSections(true);
        Property ejercicioId = ejercicio.addLongProperty("rutinaId").notNull().getProperty();

        Entity rutina = schema.addEntity("Rutina");
        rutina.addIdProperty();
        rutina.implementsInterface("DomainEntity");
        rutina.addStringProperty("idUsuario").notNull();
        rutina.addStringProperty("descripcion");
        rutina.addStringProperty("aclaracion");
        rutina.addStringProperty("fechaInicio");
        rutina.addStringProperty("fechaFin");
        rutina.addBooleanProperty("esDeCarga").notNull();
        rutina.setHasKeepSections(true);
        rutina.addToMany(ejercicio,ejercicioId);
        return rutina;
    }
}
