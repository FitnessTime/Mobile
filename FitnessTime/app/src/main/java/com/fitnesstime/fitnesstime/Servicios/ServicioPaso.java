package com.fitnesstime.fitnesstime.Servicios;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.DAO.DomainEntityService;
import com.fitnesstime.fitnesstime.DAO.MarcaDao;
import com.fitnesstime.fitnesstime.DAO.PasoDao;
import com.fitnesstime.fitnesstime.Dominio.Paso;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.Modelo.EstadisticaPasos;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by julian on 27/09/16.
 */
public class ServicioPaso extends DomainEntityService<Paso, PasoDao> {

    private SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected PasoDao getDAO() {
        return this.daoSession.getPasoDao();
    }

    public void guardarPasos(Integer pasos)
    {
        Paso paso = this.getByFecha(new Date());
        if(paso == null)
        {
            paso = new Paso();
            paso.setFecha(formatDate.format(new Date()));
            paso.setPasosDados(pasos);
            paso.setIdUsuario(FitnessTimeApplication.getIdUsuario());
            this.getDAO().insert(paso);
        }
        else
        {
            paso.setPasosDados(pasos);
            this.getDAO().update(paso);
        }
    }

    public Paso getByFecha(Date fecha)
    {
        Paso paso = null;
        if(new ServicioSecurityToken().estaAutenticado())
        {
            String idUsuario = FitnessTimeApplication.getIdUsuario();
            QueryBuilder<Paso> queryBuilder = this.getDAO().queryBuilder();
            queryBuilder.where(queryBuilder.and(PasoDao.Properties.IdUsuario.eq(idUsuario), PasoDao.Properties.Fecha.eq(formatDate.format(fecha))));
            paso = queryBuilder.unique();
        }
        return paso;
    }

    public ArrayList<EstadisticaPasos> getEstadisticasPasos()
    {
        ArrayList<EstadisticaPasos> estadisticas = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for(int i=0; i > -7 ; i--)
        {
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_YEAR, i);
            Paso paso = this.getByFecha(calendar.getTime());
            EstadisticaPasos estadistica = new EstadisticaPasos();
            try {
                estadistica.setDia(paso != null ? formatDate.parse(paso.getFecha()) : calendar.getTime());
                estadistica.setPasos(paso != null ? paso.getPasosDados() : 0);
            } catch (ParseException e) {
                estadistica.setDia(calendar.getTime());
                estadistica.setPasos(0);
            }
            estadisticas.add(estadistica);
        }
        return estadisticas;
    }

    public ResponseHelper guardarAPI(Paso paso)
    {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String pasoDTO = gson.toJson(paso, new TypeToken<Paso>() {}.getType());
        pasoDTO = pasoDTO.replace(" ", "%20");
        SecurityToken st = FitnessTimeApplication.getSession();
        String requestURL = "/pasos?authToken=" + st.getAuthToken() + "&paso=" + pasoDTO;
        return ServicioRequestHelper.PostRequest(requestURL);
    }
}
