package com.fitnesstime.fitnesstime.DAO;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Modelo.Rutina;
import com.fitnesstime.fitnesstime.Modelo.SecurityToken;

import java.util.List;

/**
 * Created by julian on 12/04/16.
 */
public class SecurityTokenDAO extends GenericDAO<SecurityToken> {

    public SecurityToken getSecurityToken()
    {
        return db.where(SecurityToken.class).findAll().first();
    }

    public boolean estaAutenticado()
    {
        return (db.where(SecurityToken.class).findAll().size() == 1);
    }
}
