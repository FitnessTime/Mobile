package com.fitnesstime.fitnesstime.Servicios;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Assemblers.RutinaAssembler;
import com.fitnesstime.fitnesstime.DAO.DomainEntityService;
import com.fitnesstime.fitnesstime.DAO.RutinaDao;
import com.fitnesstime.fitnesstime.DTOs.RutinaDTO;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Util.HelperLeerMensajeResponse;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by jskalic on 22/04/2016.
 */
public class
        ServicioRutina extends DomainEntityService<Rutina, RutinaDao> {

    @Override
    protected RutinaDao getDAO() {
        return this.daoSession.getRutinaDao();
    }

    public List<Rutina> getAll()
    {
        String idUsuario = FitnessTimeApplication.getIdUsuario();
        QueryBuilder<Rutina> queryBuilder = this.getDAO().queryBuilder();
        queryBuilder.where(queryBuilder.and(RutinaDao.Properties.IdUsuario.eq(idUsuario), RutinaDao.Properties.Eliminada.eq(false)));
        return queryBuilder.list();
    }

    public void actualizar(RutinaDTO rutinaDTO)
    {
        Rutina rutina = RutinaAssembler.fromDTO(rutinaDTO);
        this.getDAO().update(rutina);
    }

    public void actualizar(Rutina rutina)
    {
        this.getDAO().update(rutina);
    }

    public void guardar(Rutina rutina)
    {
        rutina.setIdUsuario(FitnessTimeApplication.getIdUsuario());
        this.getDAO().insert(rutina);
    }

    public void eliminar(Rutina rutina)
    {
        rutina.setEliminada(true);
        this.getDAO().update(rutina);
    }

    public ResponseHelper guardarAPI(String rutina)
    {
        try {
            SecurityToken st = FitnessTimeApplication.getSession();
            URL url = new URL("http://api-fitnesstime.herokuapp.com/rutinas?authToken=" + st.getAuthToken() + "&rutina=" + rutina);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestMethod("POST");
            int code = urlConnection.getResponseCode();
            InputStream stream = code!=200?urlConnection.getErrorStream():urlConnection.getInputStream();
            String response = HelperLeerMensajeResponse.leerMensaje(stream);
            return new ResponseHelper(code,response);
        }catch(Exception e)
        {
            return new ResponseHelper(404,"Error " + e.getMessage());
        }
    }

    public ResponseHelper editarAPI(String rutina)
    {
        try {
            SecurityToken st = FitnessTimeApplication.getSession();
            URL url = new URL("http://api-fitnesstime.herokuapp.com/rutinas?authToken=" + st.getAuthToken() + "&rutina=" + rutina);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestMethod("PUT");
            int code = urlConnection.getResponseCode();
            InputStream stream = code!=200?urlConnection.getErrorStream():urlConnection.getInputStream();
            String response = HelperLeerMensajeResponse.leerMensaje(stream);
            return new ResponseHelper(code,response);
        }catch(Exception e)
        {
            return new ResponseHelper(404,"Error " + e.getMessage());
        }
    }
}
