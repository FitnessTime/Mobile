package com.fitnesstime.fitnesstime.Servicios;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.DAO.DomainEntityService;
import com.fitnesstime.fitnesstime.DAO.MarcaDao;
import com.fitnesstime.fitnesstime.Dominio.Ejercicio;
import com.fitnesstime.fitnesstime.Dominio.Marca;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.Modelo.EjercicioMarca;
import com.fitnesstime.fitnesstime.Modelo.EstadisticaMarca;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Util.HelperLeerMensajeResponse;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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

    public void agregarMarca(Marca marca)
    {

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

    public List<Marca> getMarcasDeEjercicio(long idEjercicio)
    {
        int anio = new Date().getYear();
        QueryBuilder<Marca> queryBuilder = this.getDAO().queryBuilder();
        List<Marca> marcas = queryBuilder.where(MarcaDao.Properties.EjercicioId.eq(idEjercicio)).list();
        return marcas;
    }

    public List<EstadisticaMarca> getEstadisticaMarcas(long idRutina)
    {
        ServicioEjercicio servicioEjercicio = new ServicioEjercicio();
        List<Ejercicio> ejercicios =  servicioEjercicio.getEjerciciosDe(idRutina);
        List<EstadisticaMarca> estadisticas = new ArrayList<>();

        for(Ejercicio ejercicio : ejercicios)
        {
            EstadisticaMarca estadistica = new EstadisticaMarca();
            estadistica.setEjercicio(ejercicio);
            estadistica.setMarcas(getMarcasDeEjercicio(ejercicio.getId()));
            estadisticas.add(estadistica);
        }
        return estadisticas;
    }

    public ResponseHelper guardarAPI(String marca)
    {
        SecurityToken st = FitnessTimeApplication.getSession();
        String requestURL = "/marcas?authToken=" + st.getAuthToken() + "&marca=" + marca;
        return ServicioRequestHelper.PostRequest(requestURL);
    }
}
