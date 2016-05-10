package com.fitnesstime.fitnesstime.DTOs;

import com.fitnesstime.fitnesstime.Dominio.Ejercicio;
import com.fitnesstime.fitnesstime.Dominio.Rutina;

import java.util.Date;
import java.util.List;

/**
 * Created by julian on 03/05/16.
 */
public class RutinaDTO {

    private Long idWeb;
    private Long idMobile;
    private String inicio;
    private String fin;
    private String descripcion;
    private String aclaracion;
    private Integer versionMobile;
    private Integer versionWeb;
    private boolean estaSincronizado;
    private boolean esDeCarga;
    private boolean eliminada;
    private String idUsuario;
    private List<Ejercicio> ejercicios;

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

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAclaracion() {
        return aclaracion;
    }

    public void setAclaracion(String aclaracion) {
        this.aclaracion = aclaracion;
    }

    public Integer getVersionMobile() {
        return versionMobile;
    }

    public void setVersionMobile(Integer versionMobile) {
        this.versionMobile = versionMobile;
    }

    public Integer getVersionWeb() {
        return versionWeb;
    }

    public void setVersionWeb(Integer versionWeb) {
        this.versionWeb = versionWeb;
    }

    public boolean isEstaSincronizado() {
        return estaSincronizado;
    }

    public void setEstaSincronizado(boolean estaSincronizado) {
        this.estaSincronizado = estaSincronizado;
    }

    public boolean isRutinaDeCarga() {
        return esDeCarga;
    }

    public void setRutinaDeCarga(boolean rutinaDeCarga) {
        this.esDeCarga = rutinaDeCarga;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String usuario) {
        this.idUsuario = usuario;
    }

    public List<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public boolean isEliminada() {
        return eliminada;
    }

    public void setEliminada(boolean eliminada) {
        this.eliminada = eliminada;
    }
}
