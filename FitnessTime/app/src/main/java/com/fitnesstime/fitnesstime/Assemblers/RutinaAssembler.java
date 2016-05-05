package com.fitnesstime.fitnesstime.Assemblers;

import com.fitnesstime.fitnesstime.DTOs.RutinaDTO;
import com.fitnesstime.fitnesstime.Dominio.Rutina;

/**
 * Created by julian on 03/05/16.
 */
public final class RutinaAssembler {

    public static Rutina fromDTO(RutinaDTO rutinaDTO)
    {
        Rutina rutina = new Rutina();
        rutina.setId(rutinaDTO.getIdMobile());
        rutina.setIdWeb(rutinaDTO.getIdWeb());
        rutina.setInicio(rutinaDTO.getInicio());
        rutina.setFin(rutinaDTO.getFin());
        rutina.setDescripcion(rutinaDTO.getDescripcion());
        rutina.setAclaracion(rutinaDTO.getAclaracion());
        rutina.setVersion(rutinaDTO.getVersionMobile());
        rutina.setVersionWeb(rutinaDTO.getVersionWeb());
        rutina.setEstaSincronizado(rutinaDTO.isEstaSincronizado());
        rutina.setEsDeCarga(rutinaDTO.isRutinaDeCarga());
        rutina.setIdUsuario(rutinaDTO.getIdUsuario());
        return rutina;
    }

    public static RutinaDTO toDTO(Rutina rutina)
    {
        RutinaDTO rutinaDTO = new RutinaDTO();
        rutinaDTO.setIdMobile(rutina.getId());
        rutinaDTO.setIdWeb(rutina.getIdWeb());
        rutinaDTO.setInicio(rutina.getInicio());
        rutinaDTO.setFin(rutina.getFin());
        rutinaDTO.setDescripcion(rutina.getDescripcion());
        rutinaDTO.setAclaracion(rutina.getAclaracion());
        rutinaDTO.setVersionMobile(rutina.getVersion());
        rutinaDTO.setVersionWeb(rutina.getVersionWeb());
        rutinaDTO.setEstaSincronizado(rutina.getEstaSincronizado());
        rutinaDTO.setRutinaDeCarga(rutina.getEsDeCarga());
        rutinaDTO.setIdUsuario(rutina.getIdUsuario());
        rutinaDTO.setEjercicioList(rutina.getEjercicioList());
        return rutinaDTO;
    }
}
