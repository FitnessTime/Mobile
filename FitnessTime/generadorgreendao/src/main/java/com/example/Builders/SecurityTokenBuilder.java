package com.example.Builders;

import com.example.Builder;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;

/**
 * Created by jskalic on 22/04/2016.
 */
public class SecurityTokenBuilder extends Builder {
    public static Entity build() {

        Entity securityToken = schema.addEntity("SecurityToken");
        securityToken.addIdProperty();
        securityToken.implementsInterface("DomainEntity");
        securityToken.addStringProperty("nombreUsuario").notNull();
        securityToken.addStringProperty("emailUsuario").notNull();
        securityToken.addStringProperty("authToken").notNull();
        return securityToken;
    }
}
