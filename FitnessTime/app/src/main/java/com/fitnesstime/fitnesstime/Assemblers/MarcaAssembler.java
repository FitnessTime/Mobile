package com.fitnesstime.fitnesstime.Assemblers;

import com.fitnesstime.fitnesstime.DTOs.MarcaDTO;
import com.fitnesstime.fitnesstime.Dominio.Marca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by julian on 19/06/16.
 */
public class MarcaAssembler {

    public static Marca fromDTO(MarcaDTO marcaDTO)
    {
        Marca marca = new Marca();
        marca.setId(marcaDTO.getIdMobile());
        marca.setIdWeb(marcaDTO.getIdWeb());
        marca.setCarga(marcaDTO.getCarga());

        if(marcaDTO.getFecha().contains("-"))
        {
            String[] finicio = marcaDTO.getFecha().split("-");
            marca.setFecha(finicio[2] + "/" + finicio[1] + "/" + finicio[0]);
        }
        else
        {
            marca.setFecha(marcaDTO.getFecha());
        }

        marca.setEjercicioId(marcaDTO.getIdEjercicio());
        return marca;
    }

    public static MarcaDTO toDTO(Marca marca) {

        MarcaDTO marcaDTO = new MarcaDTO();
        marcaDTO.setIdWeb(marca.getIdWeb());
        marcaDTO.setIdMobile(marca.getId());
        marcaDTO.setCarga(marca.getCarga());
        marcaDTO.setFecha(marca.getFecha());
        marcaDTO.setIdEjercicio(marca.getEjercicioId());
        return marcaDTO;
    }

    public static List<MarcaDTO> toDTOs(List<Marca> marcas)
    {
        List<MarcaDTO> marcasDTOs = new ArrayList<>();
        if(marcas == null)
            return marcasDTOs;
        for(Marca marca : marcas)
        {
            marcasDTOs.add(toDTO(marca));
        }
        return marcasDTOs;
    }
}
