package com.fitnesstime.fitnesstime.Servicios;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Assemblers.RutinaAssembler;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
import com.fitnesstime.fitnesstime.DAO.DomainEntityService;
import com.fitnesstime.fitnesstime.DAO.RutinaDao;
import com.fitnesstime.fitnesstime.DTOs.RutinaDTO;
import com.fitnesstime.fitnesstime.Dominio.Ejercicio;
import com.fitnesstime.fitnesstime.Dominio.Rutina;
import com.fitnesstime.fitnesstime.Dominio.SecurityToken;
import com.fitnesstime.fitnesstime.Modelo.ResponseHelper;
import com.fitnesstime.fitnesstime.Util.HelperLeerMensajeResponse;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
        queryBuilder.where(queryBuilder.and(RutinaDao.Properties.IdUsuario.eq(idUsuario), RutinaDao.Properties.Eliminada.eq(false)));
        return queryBuilder.list();
    }

    public List<RutinaDTO> getNoSincronizadas()
    {
        String idUsuario = FitnessTimeApplication.getIdUsuario();
        QueryBuilder<Rutina> queryBuilder = this.getDAO().queryBuilder();
        queryBuilder.where(RutinaDao.Properties.IdUsuario.eq(idUsuario));
        List<Rutina> rutinas = queryBuilder.list();
        List<RutinaDTO> rutinasDTO = new ArrayList<>();
        for(Rutina rutina : rutinas)
        {
            rutinasDTO.add(RutinaAssembler.toDTO(rutina));
        }
        return rutinasDTO;
    }

    public void marcarComoNoSincronizada(Long idRutina)
    {
        Rutina rutina = this.getById(idRutina);
        rutina.setEstaSincronizado(false);
        this.actualizar(rutina);
    }

    public void marcarComoSincronizada(Long idRutina)
    {
        Rutina rutina = this.getById(idRutina);
        rutina.setEstaSincronizado(true);
        this.actualizar(rutina);
    }

    public Rutina getByIdWeb(long idWeb)
    {
        String idUsuario = FitnessTimeApplication.getIdUsuario();
        QueryBuilder<Rutina> queryBuilder = this.getDAO().queryBuilder();
        queryBuilder.where(queryBuilder.and(RutinaDao.Properties.IdUsuario.eq(idUsuario), RutinaDao.Properties.Eliminada.eq(false), RutinaDao.Properties.IdWeb.eq(idWeb)));
        return queryBuilder.unique();
    }

    public Rutina getById(long idRutina)
    {
        String idUsuario = FitnessTimeApplication.getIdUsuario();
        QueryBuilder<Rutina> queryBuilder = this.getDAO().queryBuilder();
        queryBuilder.where(queryBuilder.and(RutinaDao.Properties.IdUsuario.eq(idUsuario), RutinaDao.Properties.Eliminada.eq(false), RutinaDao.Properties.Id.eq(idRutina)));
        return queryBuilder.list().get(0);
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

    public void sincronizarRutinas(List<RutinaDTO> rutinas)
    {
        for(RutinaDTO rutinaDTO : rutinas)
        {
            Rutina rutina = this.getByIdWeb(rutinaDTO.getIdWeb());
            if(rutina == null && rutinaDTO.getIdMobile() == null)
            {
                this.guardar(RutinaAssembler.fromDTO(rutinaDTO));
            }
            else
            {
                this.actualizar(rutinaDTO);
            }
            new ServicioEjercicio().sincronizarEjercicios(rutinaDTO.getEjercicios());
        }
    }

    public void guardar(Rutina rutina)
    {
        rutina.setIdUsuario(FitnessTimeApplication.getIdUsuario());
        this.getDAO().insert(rutina);
    }

    public void eliminar(Rutina rutina)
    {
        rutina.setEliminada(true);
        ServicioEjercicio servicioEjercicio = new ServicioEjercicio();
        for(Ejercicio ejercicio : rutina.getEjercicioList())
        {
            servicioEjercicio.eliminar(ejercicio);
        }
        this.getDAO().update(rutina);
    }

    public ResponseHelper guardarAPI(String rutina)
    {
        SecurityToken st = FitnessTimeApplication.getSession();
        String requestURL = "/rutinas?authToken=" + st.getAuthToken() + "&rutina=" + rutina;
        return ServicioRequestHelper.PostRequest(requestURL);
    }

    public ResponseHelper eliminarAPI(long idRutina)
    {
        SecurityToken st = FitnessTimeApplication.getSession();
        String requestURL = "/rutinas?authToken=" + st.getAuthToken() + "&id=" + idRutina;
        return ServicioRequestHelper.DeleteRequest(requestURL);
    }

    public ResponseHelper editarAPI(String rutina)
    {
        SecurityToken st = FitnessTimeApplication.getSession();
        String requestURL = "/rutinas?authToken=" + st.getAuthToken() + "&rutina=" + rutina;
        return ServicioRequestHelper.PutRequest(requestURL);
    }

    public ResponseHelper sincronizarAPI(String rutinas)
    {
        SecurityToken st = FitnessTimeApplication.getSession();
        String requestURL = "/sincronizarRutinas?authToken=" + st.getAuthToken() + "&rutinas=" + rutinas;
        return ServicioRequestHelper.GetRequest(requestURL);
    }
}
