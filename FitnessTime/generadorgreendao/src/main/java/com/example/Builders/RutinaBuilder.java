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
        ejercicio.addLongProperty("idWeb");
        ejercicio.addIntProperty("versionWeb");
        ejercicio.addIntProperty("version");
        ejercicio.implementsInterface("DomainEntity");
        ejercicio.addStringProperty("nombre").notNull();
        ejercicio.addBooleanProperty("estaSincronizado").notNull();
        ejercicio.addStringProperty("diaDeLaSemana");
        ejercicio.addIntProperty("series");
        ejercicio.addIntProperty("repeticiones");
        ejercicio.addIntProperty("tiempoActivo");
        ejercicio.addIntProperty("tiempoDescanso");
        ejercicio.addBooleanProperty("nombreCambio");
        ejercicio.addBooleanProperty("diaDeLaSemanaCambio");
        ejercicio.addBooleanProperty("seriesCambio");
        ejercicio.addBooleanProperty("repeticionesCambio");
        ejercicio.addBooleanProperty("tiempoActivoCambio");
        ejercicio.addBooleanProperty("tiempoDescansoCambio");

        ejercicio.addBooleanProperty("eliminada").notNull();
        ejercicio.addBooleanProperty("esDeCarga").notNull();
        ejercicio.setHasKeepSections(true);
        Property ejercicioId = ejercicio.addLongProperty("rutinaId").notNull().getProperty();

        Entity marca = schema.addEntity("Marca");
        marca.implementsInterface("DomainEntity");
        marca.addIdProperty();
        marca.addLongProperty("idWeb");
        marca.addStringProperty("fecha");
        marca.addIntProperty("carga");
        marca.setHasKeepSections(true);
        Property marcaId = marca.addLongProperty("ejercicioId").notNull().getProperty();
        ejercicio.addToMany(marca, marcaId);

        Entity rutina = schema.addEntity("Rutina");
        rutina.addIdProperty();
        rutina.implementsInterface("DomainEntity");
        rutina.addStringProperty("idUsuario").notNull();
        rutina.addIntProperty("versionWeb");
        rutina.addIntProperty("version");
        rutina.addLongProperty("idWeb");
        rutina.addBooleanProperty("estaSincronizado").notNull();
        rutina.addStringProperty("descripcion");
        rutina.addStringProperty("aclaracion");
        rutina.addStringProperty("inicio");
        rutina.addStringProperty("fin");
        rutina.addBooleanProperty("esDeCarga").notNull();
        rutina.addBooleanProperty("eliminada").notNull();
        rutina.addBooleanProperty("inicioCambio");
        rutina.addBooleanProperty("finCambio");
        rutina.addBooleanProperty("aclaracionCambio");
        rutina.addBooleanProperty("descripcionCambio");
        rutina.setHasKeepSections(true);
        rutina.addToMany(ejercicio,ejercicioId);
        return rutina;
    }
}
