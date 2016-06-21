package com.fitnesstime.fitnesstime.Servicios;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Assemblers.EjercicioAssembler;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.DAO.DomainEntityService;
import com.fitnesstime.fitnesstime.DAO.EjercicioDao;
import com.fitnesstime.fitnesstime.DTOs.EjercicioDTO;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Dominio.Ejercicio;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Util.HelperLeerMensajeResponse;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

    public void guardarEjercicioEnRutina(Rutina rutina, Ejercicio ejercicio)
    {
        ejercicio.setRutinaId(rutina.getId());
        this.getDAO().insert(ejercicio);
        rutina.getEjercicioList().add(ejercicio);
    }

    public void eliminar(Ejercicio ejercicio)
    {
        ejercicio.setEliminada(true);
        this.getDAO().update(ejercicio);
    }

    public List<Ejercicio> getEjerciciosDe(Long idRutina)
    {
        QueryBuilder<Ejercicio> queryBuilder = this.getDAO().queryBuilder();
        List ejercicios = queryBuilder.where(queryBuilder.and(EjercicioDao.Properties.RutinaId.eq(idRutina), EjercicioDao.Properties.Eliminada.eq(false))).list();
        return ejercicios;
    }

    public List<Ejercicio> getEjerciciosDe(Long idRutina, String dia)
    {
        QueryBuilder qb = this.getDAO().queryBuilder();
        List ejercicios = qb.where(qb.and(EjercicioDao.Properties.RutinaId.eq(idRutina), EjercicioDao.Properties.DiaDeLaSemana.eq(dia), EjercicioDao.Properties.Eliminada.eq(false))).list();
        return ejercicios;
    }

    public List<Ejercicio> getAll()
    {
        QueryBuilder<Ejercicio> queryBuilder = this.getDAO().queryBuilder();
        queryBuilder.where(EjercicioDao.Properties.Eliminada.eq(false));
        return queryBuilder.list();
    }

    public boolean tieneEjerciciosElDia(long idRutina, String dia)
    {
        QueryBuilder<Ejercicio> queryBuilder = this.getDAO().queryBuilder();
        queryBuilder.where(queryBuilder.and(EjercicioDao.Properties.DiaDeLaSemana.eq(dia), EjercicioDao.Properties.Eliminada.eq(false), EjercicioDao.Properties.RutinaId.eq(idRutina)));
        return queryBuilder.list().size() > 0;
    }

    public void actualizar(EjercicioDTO ejercicioDTO)
    {
        Ejercicio ejercicio = EjercicioAssembler.fromDTO(ejercicioDTO);
        this.getDAO().update(ejercicio);
    }

    public void actualizar(Ejercicio ejercicio)
    {
        this.getDAO().update(ejercicio);
    }

    public Ejercicio getById(long idEjercicio)
    {
        QueryBuilder<Ejercicio> queryBuilder = this.getDAO().queryBuilder();
        queryBuilder.where(queryBuilder.and(EjercicioDao.Properties.Id.eq(idEjercicio), EjercicioDao.Properties.Eliminada.eq(false)));
        return queryBuilder.list().get(0);
    }

    public void marcarComoNoSincronizada(Long idEjercicio)
    {
        Ejercicio ejercicio = this.getById(idEjercicio);
        ejercicio.setEstaSincronizado(false);
        this.actualizar(ejercicio);
    }

    public void sincronizarEjercicios(List<EjercicioDTO> ejercicios)
    {
        for(EjercicioDTO ejercicioDTO : ejercicios)
        {
            if(ejercicioDTO.getIdMobile()==null)
            {
                this.guardar(EjercicioAssembler.fromDTO(ejercicioDTO));
            }
            else
            {
                this.actualizar(EjercicioAssembler.fromDTO(ejercicioDTO));
            }
        }
    }

    public ResponseHelper guardarAPI(String ejercicio)
    {
        try {
            SecurityToken st = FitnessTimeApplication.getSession();
            URL url = new URL(Constantes.URL_API + "/ejercicios?authToken=" + st.getAuthToken() + "&ejercicio=" + ejercicio);
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

    public ResponseHelper editarAPI(String ejercicio)
    {
        try {
            SecurityToken st = FitnessTimeApplication.getSession();
            URL url = new URL(Constantes.URL_API + "/ejercicios?authToken=" + st.getAuthToken() + "&ejercicio=" + ejercicio);
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

    public ResponseHelper eliminarAPI(long idEjercicio, boolean esDeCarga)
    {
        try {
            SecurityToken st = FitnessTimeApplication.getSession();
            URL url = new URL(Constantes.URL_API + "/ejercicios?authToken=" + st.getAuthToken() + "&id=" + idEjercicio + "&esDeCarga=" + esDeCarga);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestMethod("DELETE");
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