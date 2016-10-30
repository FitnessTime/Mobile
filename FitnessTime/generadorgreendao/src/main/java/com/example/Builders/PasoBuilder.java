package com.example.Builders;

import com.example.Builder;

import de.greenrobot.daogenerator.Entity;

/**
 * Created by julian on 27/09/16.
 */
public class PasoBuilder extends Builder {
    public static Entity build() {
        Entity paso = schema.addEntity("Paso");
        paso.addIdProperty();
        paso.implementsInterface("DomainEntity");
        paso.addStringProperty("idUsuario").notNull();
        paso.addFloatProperty("pasosDados");
        paso.addStringProperty("fecha");
        paso.setHasKeepSections(true);
        return paso;
    }
}
