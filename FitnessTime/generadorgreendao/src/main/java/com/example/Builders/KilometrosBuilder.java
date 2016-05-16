package com.example.Builders;

import com.example.Builder;

import de.greenrobot.daogenerator.Entity;

/**
 * Created by julian on 15/05/16.
 */
public class KilometrosBuilder extends Builder {
    public static Entity build() {

        Entity kilometros = schema.addEntity("Kilometros");
        kilometros.addIdProperty();
        kilometros.implementsInterface("DomainEntity");
        kilometros.addStringProperty("idUsuario").notNull();
        kilometros.addIntProperty("kms").notNull();
        kilometros.addStringProperty("fecha").notNull();
        return kilometros;
    }
}
