package com.fitnesstime.fitnesstime.Servicios;

import com.fitnesstime.fitnesstime.DAO.DomainEntityService;
import com.fitnesstime.fitnesstime.DAO.MarcaDao;
import com.fitnesstime.fitnesstime.Dominio.Marca;

import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by julian on 25/05/16.
 */
public class ServicioMarca extends DomainEntityService<Marca, MarcaDao> {

    @Override
    protected MarcaDao getDAO() {
        return this.daoSession.getMarcaDao();
    }

    public void agregarMarca(Integer peso, Long idEjercicio)
    {
        Marca marca = new Marca();
        marca.setCarga(peso);
        marca.setFecha(new Date().toString());
        marca.setEjercicioId(idEjercicio);
        this.getDAO().insert(marca);
    }

    public Marca ultimaMarcaDe(Long idEjercicio)
    {
        QueryBuilder<Marca> queryBuilder = this.getDAO().queryBuilder();
        queryBuilder.where(MarcaDao.Properties.EjercicioId.eq(idEjercicio));
        queryBuilder.orderDesc(MarcaDao.Properties.Id);
        List<Marca> marcas = queryBuilder.list();
        return marcas.size()==0?null:marcas.get(0);
    }
}
