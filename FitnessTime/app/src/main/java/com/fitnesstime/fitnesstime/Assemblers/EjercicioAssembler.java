package com.fitnesstime.fitnesstime.Assemblers;

import com.fitnesstime.fitnesstime.DTOs.EjercicioDTO;
import com.fitnesstime.fitnesstime.Dominio.Ejercicio;

import java.util.ArrayList;
import java.util.List;

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
        ejercicio.setNombreCambio(ejercicioDTO.isNombreCambio());
        ejercicio.setDiaDeLaSemanaCambio(ejercicioDTO.isDiaDeLaSemanaCambio());
        ejercicio.setSeriesCambio(ejercicioDTO.isSeriesCambio());
        ejercicio.setRepeticionesCambio(ejercicioDTO.isRepeticionesCambio());
        ejercicio.setTiempoActivoCambio(ejercicioDTO.isTiempoActivoCambio());
        ejercicio.setTiempoDescansoCambio(ejercicioDTO.isTiempoDescansoCambio());
        ejercicio.setRutinaId(ejercicioDTO.getIdRutina());
        return ejercicio;
    }

    public static EjercicioDTO toDTO(Ejercicio ejercicio) {
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
        ejercicioDTO.setNombreCambio(ejercicio.getNombreCambio());
        ejercicioDTO.setDiaDeLaSemanaCambio(ejercicio.getDiaDeLaSemanaCambio());
        ejercicioDTO.setSeriesCambio(ejercicio.getSeriesCambio());
        ejercicioDTO.setRepeticionesCambio(ejercicio.getRepeticionesCambio());
        ejercicioDTO.setTiempoActivoCambio(ejercicio.getTiempoActivoCambio());
        ejercicioDTO.setTiempoDescansoCambio(ejercicio.getTiempoDescansoCambio());
        ejercicioDTO.setIdRutina(ejercicio.getRutinaId());
        return ejercicioDTO;
    }

    public static List<EjercicioDTO> toDTOs(List<Ejercicio> ejercicios)
    {
        List<EjercicioDTO> ejerciciosDTOs = new ArrayList<>();
        if(ejercicios == null)
            return ejerciciosDTOs;
        for(Ejercicio ejercicio : ejercicios)
        {
            ejerciciosDTOs.add(toDTO(ejercicio));
        }
        return ejerciciosDTOs;
    }
}