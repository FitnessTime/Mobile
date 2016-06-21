package com.fitnesstime.fitnesstime.DTOs;

/**
 * Created by julian on 19/06/16.
 */
public class MarcaDTO {

    private Long idWeb;
    private Long idMobile;
    private String fecha;
    private Integer carga;
    private Long idEjercicio;

    public Long getIdWeb() {
        return idWeb;
    }

    public void setIdWeb(Long idWeb) {
        this.idWeb = idWeb;
    }

    public Long getIdMobile() {
        return idMobile;
    }

    public void setIdMobile(Long idMobile) {
        this.idMobile = idMobile;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getCarga() {
        return carga;
    }

    public void setCarga(Integer carga) {
        this.carga = carga;
    }

    public Long getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(Long idEjercicio) {
        this.idEjercicio = idEjercicio;
    }
}
