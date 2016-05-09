package com.fitnesstime.fitnesstime.Assemblers;

import com.fitnesstime.fitnesstime.DTOs.EjercicioDTO;
import com.fitnesstime.fitnesstime.Dominio.Ejercicio;

/**
 * Created by julian on 08/05/16.
 */
public class EjercicioAssembler {

    public static Ejercicio fromDTO(EjercicioDTO ejercicioDTO)
    {
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setId(ejercicioDTO.getIdMobile());
        ejercicio.setIdWeb(ejercicioDTO.getIdWeb());
        ejercicio.setNombre(ejercicioDTO.getNombre());
        ejercicio.setSeries(ejercicioDTO.getSeries());
        ejercicio.setRepeticiones(ejercicioDTO.getRepeticiones());
        ejercicio.setTiempoActivo(ejercicioDTO.getTiempoActivo());
        ejercicio.setTiempoDescanso(ejercicioDTO.getTiempoDescanso());
        ejercicio.setVersion(ejercicioDTO.getVersionMobile());
        ejercicio.setVersionWeb(ejercicioDTO.getVersionWeb());
        ejercicio.setEstaSincronizado(ejercicioDTO.isEstaSincronizado());
        ejercicio.setEsDeCarga(ejercicioDTO.isEsDeCarga());
        ejercicio.setEliminada(ejercicioDTO.isEliminada());
        ejercicio.setRutinaId(ejercicioDTO.getIdRutina());
        return ejercicio;
    }

    public static EjercicioDTO toDTO(Ejercicio ejercicio)
    {
        EjercicioDTO ejercicioDTO = new EjercicioDTO();
        ejercicioDTO.setIdMobile(ejercicio.getId());
        ejercicioDTO.setIdWeb(ejercicio.getIdWeb());
        ejercicioDTO.setNombre(ejercicio.getNombre());
        ejercicioDTO.setSeries(ejercicio.getSeries());
        ejercicioDTO.setRepeticiones(ejercicio.getRepeticiones());
        ejercicioDTO.setTiempoActivo(ejercicio.getTiempoActivo());
        ejercicioDTO.setTiempoDescanso(ejercicio.getTiempoDescanso());
        ejercicioDTO.setVersionMobile(ejercicio.getVersion());
        ejercicioDTO.setVersionWeb(ejercicio.getVersionWeb());
        ejercicioDTO.setEstaSincronizado(ejercicio.getEstaSincronizado());
        ejercicioDTO.setEsDeCarga(ejercicio.getEsDeCarga());
        ejercicioDTO.setEliminada(ejercicio.getEliminada());
        ejercicioDTO.setIdRutina(ejercicio.getRutinaId());
        return ejercicioDTO;
    }
}