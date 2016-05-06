package com.fitnesstime.fitnesstime.Servicios;

import com.fitnesstime.fitnesstime.DAO.DomainEntityService;
import com.fitnesstime.fitnesstime.DAO.SecurityTokenDao;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by julian on 24/04/16.
 */
public class ServicioSecurityToken extends DomainEntityService<SecurityToken, SecurityTokenDao> {

    @Override
    protected SecurityTokenDao getDAO() {
        return this.daoSession.getSecurityTokenDao();
    }

    public List<SecurityToken> getAll()
    {
        QueryBuilder<SecurityToken> queryBuilder = this.getDAO().queryBuilder();
        return queryBuilder.list();
    }

    public void guardar(SecurityToken st)
    {
        st.setImagenPerfil("");
        this.getDAO().insert(st);
    }

    public SecurityToken get(String id)
    {
        return this.getDAO().queryBuilder().where(SecurityTokenDao.Properties.EmailUsuario.eq(id)).unique();
    }

    public void guardarFotoPerfil(String imagenPerfil)
    {
        SecurityToken st = new ServicioSecurityToken().getAll().get(0);
        st.setImagenPerfil(imagenPerfil);
        this.getDAO().update(st);
    }

    public void borrar(SecurityToken st)
    {
        this.getDAO().delete(st);
    }

    public boolean estaAutenticado()
    {
        return this.getAll().size() > 0 ;
    }
}
