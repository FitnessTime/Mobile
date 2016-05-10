package com.fitnesstime.fitnesstime.Servicios;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.DAO.DomainEntityService;
import com.fitnesstime.fitnesstime.DAO.EjercicioDao;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Dominio.Ejercicio;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by julian on 22/04/16.
 */
public class ServicioEjercicio extends DomainEntityService<Ejercicio, EjercicioDao> {

    @Override
    protected EjercicioDao getDAO() {
        return this.daoSession.getEjercicioDao();
    }

    public void guardar(Ejercicio ejercicio)
    {
        this.getDAO().insert(ejercicio);
    }

    public void guardarEjerciciosEnRutina(Rutina rutina, List<Ejercicio> ejercicios)
    {
        for(Ejercicio ejercicio : ejercicios)
        {
            ejercicio.setRutinaId(rutina.getId());
            this.getDAO().insert(ejercicio);
            rutina.getEjercicioList().add(ejercicio);
        }
    }

    public List<Ejercicio> getEjerciciosDe(Long idRutina)
    {
        List ejercicios= this.getDAO().queryBuilder()
                .where(EjercicioDao.Properties.RutinaId.eq(idRutina))
                .list();
        return ejercicios;
    }

    public List<Ejercicio> getEjerciciosDe(Long idRutina, String dia)
    {
        QueryBuilder qb = this.getDAO().queryBuilder();
        List ejercicios = qb.where(qb.and(EjercicioDao.Properties.RutinaId.eq(idRutina), EjercicioDao.Properties.DiaDeLaSemana.eq(dia))).list();
        return ejercicios;
    }

    public List<Ejercicio> getAll()
    {
        QueryBuilder<Ejercicio> queryBuilder = this.getDAO().queryBuilder();
        return queryBuilder.list();
    }

    public boolean tieneEjerciciosElDia(String dia)
    {
        QueryBuilder<Ejercicio> queryBuilder = this.getDAO().queryBuilder();
        queryBuilder.where(EjercicioDao.Properties.DiaDeLaSemana.eq(dia));
        return queryBuilder.list().size() > 0;
    }

}