package com.fitnesstime.fitnesstime.Servicios;

import android.util.Log;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.DAO.DomainEntityService;
import com.fitnesstime.fitnesstime.DAO.RutinaDao;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Dominio.Ejercicio;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;


import java.net.HttpURLConnection;
import java.net.URL;
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
        String idUsuario = FitnessTimeApplication.getIdUsuario();
        QueryBuilder<Rutina> queryBuilder = this.getDAO().queryBuilder();
        queryBuilder.where(RutinaDao.Properties.IdUsuario.eq(idUsuario));
        return queryBuilder.list();
    }

    public void guardar(Rutina rutina)
    {
        rutina.setIdUsuario(FitnessTimeApplication.getIdUsuario());
        this.getDAO().insert(rutina);
    }

    public int guardarAPI(String rutina)
    {
        int code = 500;
        try {
            SecurityToken st = FitnessTimeApplication.getSession();
            URL url = new URL("http://api-fitnesstime.herokuapp.com/rutinas/crear?authToken=" + st.getAuthToken() + "&rutina=" + rutina);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            code = urlConnection.getResponseCode();
        }catch(Exception e)
        {
            Log.println(1, "", e.getMessage());
        }
        return code;
    }
}
