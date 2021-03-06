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
        if(rutinaDTO.getInicio().contains("-"))
        {
            String[] finicio = rutinaDTO.getInicio().split("-");
            rutina.setInicio(finicio[2] + "/" + finicio[1] + "/" + finicio[0]);
        }
        else
        {
            rutina.setInicio(rutinaDTO.getInicio());
        }

        if(rutinaDTO.getFin().contains("-"))
        {
            String[] ffin = rutinaDTO.getFin().split("-");
            rutina.setFin(ffin[2] + "/" + ffin[1] + "/" + ffin[0]);
        }
        else
        {
            rutina.setFin(rutinaDTO.getFin());
        }
        rutina.setInicioCambio(rutinaDTO.getInicioCambio());
        rutina.setFinCambio(rutinaDTO.getFinCambio());
        rutina.setDescripcionCambio(rutinaDTO.getDescripcionCambio());
        rutina.setAclaracionCambio(rutinaDTO.getAclaracionCambio());
        rutina.setDescripcion(rutinaDTO.getDescripcion());
        rutina.setAclaracion(rutinaDTO.getAclaracion());
        rutina.setVersion(rutinaDTO.getVersionMobile());
        rutina.setVersionWeb(rutinaDTO.getVersionWeb());
        rutina.setEstaSincronizado(rutinaDTO.isEstaSincronizado());
        rutina.setEsDeCarga(rutinaDTO.isRutinaDeCarga());
        rutina.setEliminada(rutinaDTO.isEliminada());
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
        rutinaDTO.setInicioCambio(rutina.getInicioCambio());
        rutinaDTO.setFinCambio(rutina.getFinCambio());
        rutinaDTO.setDescripcionCambio(rutina.getDescripcionCambio());
        rutinaDTO.setAclaracionCambio(rutina.getAclaracionCambio());
        rutinaDTO.setVersionMobile(rutina.getVersion());
        rutinaDTO.setVersionWeb(rutina.getVersionWeb());
        rutinaDTO.setEstaSincronizado(rutina.getEstaSincronizado());
        rutinaDTO.setRutinaDeCarga(rutina.getEsDeCarga());
        rutinaDTO.setEliminada(rutina.getEliminada());
        rutinaDTO.setIdUsuario(rutina.getIdUsuario());
        rutinaDTO.setEjercicios(EjercicioAssembler.toDTOs(rutina.getEjercicioList()));
        return rutinaDTO;
    }
}
