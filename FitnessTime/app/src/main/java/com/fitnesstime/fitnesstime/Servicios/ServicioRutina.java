package com.fitnesstime.fitnesstime.Servicios;

import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Assemblers.RutinaAssembler;
import com.fitnesstime.fitnesstime.Configuracion.Constantes;
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
        queryBuilder.where(queryBuilder.and(RutinaDao.Properties.IdUsuario.eq(idUsuario), RutinaDao.Properties.Eliminada.eq(false), RutinaDao.Properties.EstaSincronizado.eq(false)));
        List<Rutina> rutinas = queryBuilder.list();
        List<RutinaDTO> rutinasDTO = new ArrayList<>();
        for(Rutina rutina : rutinas)
        {
            rutinasDTO.add(RutinaAssembler.toDTO(rutina));
        }
        return rutinasDTO;
    }

    public Rutina getById(Integer idRutina)
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
            if(rutinaDTO.getIdMobile()==null)
            {
                this.guardar(RutinaAssembler.fromDTO(rutinaDTO));
            }
            else
            {
                this.actualizar(rutinaDTO);
            }
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
        this.getDAO().update(rutina);
    }

    public ResponseHelper guardarAPI(String rutina)
    {
        try {
            SecurityToken st = FitnessTimeApplication.getSession();
            URL url = new URL(Constantes.URL_API + "/rutinas?authToken=" + st.getAuthToken() + "&rutina=" + rutina);
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

    public ResponseHelper eliminarAPI(long idRutina)
    {
        try {
            SecurityToken st = FitnessTimeApplication.getSession();
            URL url = new URL(Constantes.URL_API + "/rutinas?authToken=" + st.getAuthToken() + "&id=" + idRutina);
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

    public ResponseHelper editarAPI(String rutina)
    {
        try {
            SecurityToken st = FitnessTimeApplication.getSession();
            URL url = new URL(Constantes.URL_API + "/rutinas?authToken=" + st.getAuthToken() + "&rutina=" + rutina);
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

    public ResponseHelper sincronizarAPI(String rutinas)
    {
        try {
            SecurityToken st = FitnessTimeApplication.getSession();
            URL url = new URL(Constantes.URL_API + "/sincronizarRutinas?authToken=" + st.getAuthToken() + "&rutinas=" + rutinas);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestMethod("GET");
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
