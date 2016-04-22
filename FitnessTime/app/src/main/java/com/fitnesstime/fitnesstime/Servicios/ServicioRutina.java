package com.fitnesstime.fitnesstime.Servicios;

import com.fitnesstime.fitnesstime.DAO.DomainEntityService;
import com.fitnesstime.fitnesstime.DAO.RutinaDao;
import com.fitnesstime.fitnesstime.Dominio.Rutina;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by jskalic on 22/04/2016.
 */
public class ServicioRutina extends DomainEntityService<Rutina, RutinaDao> {

    @Override
    protected RutinaDao getDAO() {
        return this.daoSession.getRutinaDao();
    }

    public List<Rutina> getAll()
    {
        QueryBuilder<Rutina> queryBuilder = this.getDAO().queryBuilder();
        return queryBuilder.list();
    }

    public void guardar(Rutina rutina)
    {
        rutina.setIdUsuario("1");
        this.getDAO().insert(rutina);
    }
}
