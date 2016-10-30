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
            //rutina.getEjercicioList().add(ejercicio);
        }
    }

    public void guardarEjercicioEnRutina(Rutina rutina, Ejercicio ejercicio)
    {
        ejercicio.setRutinaId(rutina.getId());
        this.getDAO().insert(ejercicio);
        //rutina.getEjercicioList().add(ejercicio);
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

    public Ejercicio getByIdWeb(long idWeb)
    {
        String idUsuario = FitnessTimeApplication.getIdUsuario();
        QueryBuilder<Ejercicio> queryBuilder = this.getDAO().queryBuilder();
        queryBuilder.where(queryBuilder.and(EjercicioDao.Properties.IdWeb.eq(idWeb), EjercicioDao.Properties.Eliminada.eq(false)));
        return queryBuilder.unique();
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
            Ejercicio ejercicio = this.getByIdWeb(EjercicioAssembler.fromDTO(ejercicioDTO).getIdWeb());
            if(ejercicio == null && ejercicioDTO.getIdMobile()== null)
            {
                this.guardar(EjercicioAssembler.fromDTO(ejercicioDTO));
            }
            else
            {
                this.actualizar(ejercicioDTO);
            }
        }
    }

    public ResponseHelper guardarAPI(String ejercicio)
    {
        SecurityToken st = FitnessTimeApplication.getSession();
        String requestURL = "/ejercicios?authToken=" + st.getAuthToken() + "&ejercicio=" + ejercicio;
        return ServicioRequestHelper.PostRequest(requestURL);
    }

    public ResponseHelper editarAPI(String ejercicio)
    {
        SecurityToken st = FitnessTimeApplication.getSession();
        String requestURL = "/ejercicios?authToken=" + st.getAuthToken() + "&ejercicio=" + ejercicio;
        return ServicioRequestHelper.PutRequest(requestURL);
    }

    public ResponseHelper eliminarAPI(long idEjercicio, boolean esDeCarga)
    {
        SecurityToken st = FitnessTimeApplication.getSession();
        String requestURL = "/ejercicios?authToken=" + st.getAuthToken() + "&id=" + idEjercicio + "&esDeCarga=" + esDeCarga;
        return ServicioRequestHelper.DeleteRequest(requestURL);
    }

}